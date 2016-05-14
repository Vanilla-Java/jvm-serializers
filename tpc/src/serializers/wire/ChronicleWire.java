package serializers.wire;

import serializers.*;
import serializers.wire.custom.CustomWireSimpleUtils;

/**
 * Created by peter on 08/05/16.
 */
public class ChronicleWire {
    public static void register(TestGroups groups, int count) {
/*        groups.media.add(new WireSimpleUtils.WireTransformer(), new WireSimpleUtils.WireSerializer(),
                new SerFeatures(
                        SerFormat.BINARY,
                        SerGraph.FLAT_TREE,
                        SerClass.ZERO_KNOWLEDGE,
                        ""
                )
        );

        groups.media.add(new MarshallableWireSimpleUtils.WireTransformer(), new MarshallableWireSimpleUtils.WireSerializer(),
                new SerFeatures(
                        SerFormat.BINARY,
                        SerGraph.FLAT_TREE,
                        SerClass.CLASSES_KNOWN,
                        ""
                )
        );*/

/*        groups.media.add(new BytesMSimpleUtils.WireTransformer(), new BytesMSimpleUtils.WireSerializer(count),
                new SerFeatures(
                        SerFormat.BINARY,
                        SerGraph.FLAT_TREE,
                        SerClass.MANUAL_OPT,
                        ""
                )
        );*/
        groups.media.add(new CustomWireSimpleUtils.WireTransformer(), new CustomWireSimpleUtils.WireSerializer(count),
                new SerFeatures(
                        SerFormat.BINARY,
                        SerGraph.FLAT_TREE,
                        SerClass.MANUAL_OPT,
                        ""
                )
        );
    }
}
