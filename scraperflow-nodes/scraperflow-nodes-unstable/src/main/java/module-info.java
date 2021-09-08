import scraper.api.Node;
import scraper.nodes.unstable.api.telegram.Telegram;

open module scraper.nodes.unstable {
    requires scraper.core;
    requires scraper.api;

    requires java.net.http;
    requires java.desktop;

    exports scraper.nodes.unstable.api.telegram;

    // FIXME why is this needed so that reflections can find all nodes?
    provides Node with Telegram;
}
