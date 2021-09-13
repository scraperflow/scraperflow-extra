import scraper.annotations.*;
import scraper.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.lang.Integer.valueOf;
import static java.nio.file.Files.lines;

@NodePlugin("1.0.0")
public class IpBanDb implements FunctionalNode {
    @FlowKey T<String> user = new T<>(){};
    @FlowKey T<String> ip = new T<>(){};
    @FlowKey T<String> hash = new T<>(){};

    @FlowKey(defaultValue = "\"db/\"") @Argument @EnsureFile String bandb;
    @FlowKey(defaultValue = "\"hash.db\"") @Argument @EnsureFile String hashdb;
    @FlowKey(defaultValue = "3")  Integer attemps;

    @FlowKey(mandatory = true) @Flow(label = "ban") Address ban;

    Collection<String> hashed = new HashSet<>();
    Map<String, Integer> counts = new HashMap<>();


    @Override
    public void init(NodeContainer<? extends Node> n, ScrapeInstance instance) throws ValidationException {
        try {
            n.log(NodeLogLevel.INFO, "Reading hashed at {0}", bandb);
            lines(Path.of(hashdb)).forEach(l -> hashed.add(l));

            n.log(NodeLogLevel.INFO, "Reading bandb at {0}", bandb);
            for (String c : Objects.requireNonNull(Path.of(bandb).toFile().list())) {
                lines(Path.of(bandb, c)).forEach(l -> counts.put(c, valueOf(l)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(e, "Failed validation of ban.db and hash.db");
        }
    }

    @Override
    public synchronized void modify(FunctionalNodeContainer n, FlowMap o) {
        String hash = o.eval(this.hash);
        if(hashed.contains(hash)) return;

        String user = o.eval(this.user);
        String ip = o.eval(this.ip);

        n.log(NodeLogLevel.INFO, "New attempt: {0} as {1} ({2} times)" , ip, user, counts.getOrDefault(ip, 0));

        hashed.add(hash);


        try {
            Files.writeString(Path.of(hashdb),hash+"\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(counts.getOrDefault(ip,0) > attemps) {
            n.log(NodeLogLevel.WARN, "{0} was already BANNED" , ip);
            return;
        }

        counts.compute(ip, (h, old) -> (old==null? 1 : old + 1));
        try {
            Files.writeString(Path.of(bandb, ip),String.valueOf(counts.get(ip)));
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(counts.get(ip) > attemps) {
            n.log(NodeLogLevel.INFO, "{0} is BANNED ({1} attempts)" , ip, counts.get(ip));
            n.forward(o, ban);
        }
    }
}
