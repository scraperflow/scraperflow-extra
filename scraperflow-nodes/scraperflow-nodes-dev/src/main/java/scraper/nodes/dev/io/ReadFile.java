package scraper.nodes.dev.io;

import scraper.annotations.*;
import scraper.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reads a file and joins every line with a separator.
 * Outputs a String.
 * Throws an exception if the file does not exist.
 */
@NodePlugin("0.4.0")
@Io
public final class ReadFile implements FunctionalNode {

    /** Input file path */
    @FlowKey(mandatory = true)
    private final T<String> inputFile = new T<>(){};

    /** Character encoding of the file */
    @FlowKey(defaultValue = "\"UTF-8\"")
    private String charset;

    /** Join lines with this string. Can be empty. */
    @FlowKey(defaultValue = "\"\\n\"")
    private String join;

    /** Where the output line will be put */
    @FlowKey(mandatory = true)
    private final L<String> output = new L<>(){};

    public void modify(@NotNull final FunctionalNodeContainer n, @NotNull final FlowMap o) throws NodeException {
        String file = o.eval(inputFile);

        if(!new File(file).exists()) throw new NodeException(n.getAddress() + ": File does not exist: " + file);

        try (Stream<String> stream = Files.lines(Paths.get(file), Charset.forName(charset))) {

            o.output(output,stream.collect(Collectors.joining(join)));
        } catch (IOException e) {
            throw new NodeException(e, "File IO error");
        }
    }
}
