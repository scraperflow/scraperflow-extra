package scraper.nodes.custom.dev;

import scraper.annotations.*;
import scraper.api.*;


/**
 * Custom functional node. Identity.
 */
@NodePlugin("0.1.0")
public final class MyNode <X> implements FunctionalNode {

    @FlowKey(mandatory = true) T<X> myInput = new T<>(){};
    @FlowKey(mandatory = true) L<X> myOutput = new L<>(){};

    @Override
    public void modify(@NotNull FunctionalNodeContainer n, @NotNull FlowMap o) {
        X input = o.eval(myInput);
        o.output(myOutput, input);
    }
}
