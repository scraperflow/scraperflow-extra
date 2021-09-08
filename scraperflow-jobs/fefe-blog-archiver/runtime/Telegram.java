import com.fasterxml.jackson.databind.ObjectMapper;
import scraper.annotations.*;
import scraper.api.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static scraper.api.NodeLogLevel.ERROR;

/**
 * Message something to a Telegram account.
 * Failures to send will not result in exceptions, they are just logged.
 * <p>
 *     Note that the Telegram API has a limit on how big a message is allowed to be.
 * </p>
 */
@NodePlugin("1.0.2")
@Io
public final class Telegram implements FunctionalNode {

    /** Message as a String */
    @FlowKey(defaultValue = "\"{message}\"")
    private final T<String> message = new T<>(){};

    /** Bot token */
    @FlowKey(defaultValue = "\"{bot-token}\"") @Argument
    private String botToken;

    /** Message recipients. User or group chat id */
    @FlowKey(mandatory = true)
    private final T<List<String>> recipients = new T<>(){};

    /** Telegram bot api link */
    @FlowKey(defaultValue = "\"https://api.telegram.org/bot\"") @Argument
    private String api;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

    private void trySend(FunctionalNodeContainer n, String msg, String id) throws IOException, InterruptedException {
        /// Create Http POST method and set correct headers
        String url = api + botToken + "/sendmessage";
        String jsonString = mapper.writeValueAsString(new SendMessage("/sendmessage", id, msg));

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url))
                .header("User-Agent", "telegram-bot")
                .header("Content-type", "application/json")
                .header("charset", "UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse<Void> Response = client.send(request, HttpResponse.BodyHandlers.discarding());
        if(Response.statusCode() < 200 || Response.statusCode() > 300) {
            n.log(ERROR, "Could not send telegram message: {0}", Response.statusCode());
        }
    }

    @Override
    public void modify(@NotNull FunctionalNodeContainer n, @NotNull FlowMap o) {
        for (String id : o.eval(recipients)) {
            try {
                trySend(n, o.eval(message), id);
            } catch (IOException | InterruptedException e) {
                n.log(ERROR, "Could not send message to {0}: {1}", id, e);
            }
        }
    }


    @SuppressWarnings("unused") // used for serialization
    static class SendMessage implements Serializable {
        String chat_id;
        public String getChat_id() { return chat_id; }
        public void setChat_id(String chat_id) { this.chat_id = chat_id; }

        String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        String method;
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }

        SendMessage(String s, String valueOf, String msg) {
            method = s;
            text = msg;
            chat_id = valueOf;
        }
    }
}
