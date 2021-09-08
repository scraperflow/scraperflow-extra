import scraper.api.Node;
import scraper.nodes.dev.io.ReadFile;

open module scraper.nodes.dev {
    requires scraper.api;

    requires org.jsoup;

    // FIXME why is this needed so that reflections can find all nodes?
    provides Node with ReadFile;
}
