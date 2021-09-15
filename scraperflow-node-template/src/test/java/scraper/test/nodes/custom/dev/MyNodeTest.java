package scraper.test.nodes.custom.dev;

import org.junit.jupiter.api.Test;
import scraper.nodes.custom.dev.MyNode;
import scraper.test.FunctionalTest;
import scraper.test.WorkflowTest;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class MyNodeTest {

    /**
     * A simple functional input/output test of one node
     */
    @Test
    public void putTest() {
        FunctionalTest.runWith(MyNode.class, List.of(
                Map.of("myInput", "{X}", "myOutput", "result"), // Node Configuration
                Map.of("X", "Hello"), // flow map input
                Map.of("result","Hello") // expected flow map output
        ));
    }

    /**
     * A full specification test
     */
    @Test
    public void goToTest() {
        URL file = ClassLoader.getSystemResource("scraper/test/nodes/custom/dev/runtime.yml");
        WorkflowTest.runWith(file);
    }
}

