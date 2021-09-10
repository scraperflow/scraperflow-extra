import scraper.api.Node;
import scraper.nodes.dev.functional.FilterEmptyList;
import scraper.nodes.dev.functional.StringJoin;
import scraper.nodes.dev.html.HtmlCssQuery;
import scraper.nodes.dev.io.*;

open module scraper.nodes.dev {
    requires scraper.api;

    requires org.jsoup;

    provides Node with
            FilterEmptyList, StringJoin
            , HtmlCssQuery
            , ListFiles, MapFolder, ParentOfFile, PathGlobFile, PersistentDuplicateCheck, ReadChunkAndFilter,
                ReadChunkFile, ReadFileAsStream, ReadFile
            ;
}
