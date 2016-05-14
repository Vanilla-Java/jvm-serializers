package serializers;

import serializers.cks.CksText;
import serializers.wire.ChronicleWire;

/**
 * Full test of various codecs, using a single <code>MediaItem</code>
 * as test data.
 */
public class BenchmarkRunner2 extends MediaItemBenchmark {
    // -Xmx256m  -iterations=2000 -warmup-time=5000 -testRunMillis=10000 data/media.1.cks
    public static void main(String[] args) {
        new BenchmarkRunner2().runBenchmark(args);
    }

    protected void addTests(TestGroups groups) {
        // JSON
//        JacksonJsonManual.register(groups);
//        Wobly.register(groups);
        ChronicleWire.register(groups, 1);
//        ChronicleWire.register(groups,2);
//        ChronicleWire.register(groups,3);
//        JacksonJsonDatabind.register(groups);
        CksText.register(groups);
    }
}
