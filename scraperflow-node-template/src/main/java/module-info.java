import scraper.api.Node;
import scraper.nodes.custom.dev.MyNode;

open module scraper.nodes.custom.dev {
    requires scraper.core;

    exports scraper.nodes.custom.dev;

    // ALL nodes have to be provided
    provides Node with MyNode;
}
