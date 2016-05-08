package serializers;

import serializers.cks.CksText;
import serializers.wire.ChronicleWire;

/**
 * Full test of various codecs, using a single <code>MediaItem</code>
 * as test data.
 */
public class BenchmarkRunner2 extends MediaItemBenchmark {
    public static void main(String[] args) {
        new BenchmarkRunner2().runBenchmark(args);
    }

    protected void addTests(TestGroups groups) {
        // JSON
//        JacksonJsonManual.register(groups);
        ChronicleWire.register(groups);
//        JacksonJsonDatabind.register(groups);
        CksText.register(groups);
    }
}
