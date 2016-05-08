package serializers.wire;

import serializers.*;

/**
 * Created by peter on 08/05/16.
 */
public class ChronicleWire {
    public static void register(TestGroups groups) {
        groups.media.add(new WireSimpleUtils.WireTransformer(), new WireSimpleUtils.WireSerializer(),
                new SerFeatures(
                        SerFormat.BINARY,
                        SerGraph.FLAT_TREE,
                        SerClass.MANUAL_OPT,
                        ""
                )
        );
    }
}
