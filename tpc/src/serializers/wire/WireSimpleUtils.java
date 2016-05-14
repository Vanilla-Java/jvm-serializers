package serializers.wire;

import data.media.Media;
import data.media.MediaContent;
import data.media.MediaTransformer;
import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.NativeBytesStore;
import net.openhft.chronicle.core.pool.ClassAliasPool;
import net.openhft.chronicle.wire.BinaryWire;
import net.openhft.chronicle.wire.Wire;
import net.openhft.chronicle.wire.Wires;
import serializers.Serializer;

import java.util.ArrayList;

public class WireSimpleUtils {

    public static final class WireTransformer extends MediaTransformer<MediaContent> {
        @Override
        public MediaContent[] resultArray(int size) {
            return sourceArray(size);
        }

        @Override
        public MediaContent forward(MediaContent a) {
            return a;
        }

        @Override
        public MediaContent reverse(MediaContent a) {
            return a;
        }

        @Override
        public MediaContent shallowReverse(MediaContent a) {
            return a;
        }
    }

    public static final class WireSerializer extends Serializer<MediaContent> {
        static {
            ClassAliasPool.CLASS_ALIASES.addAlias(Media.class, "M");
        }

        Wire wire = new BinaryWire(NativeBytesStore.nativeStoreWithFixedCapacity(1024).bytesForWrite());
        MediaContent content = new MediaContent(null, new ArrayList<>(10000));
        byte[] byteArray = {};

        @Override
        public MediaContent deserialize(byte[] array) throws Exception {
            try {
                wire.clear();
                wire.bytes().write(array);
                Wires.readMarshallable(content, wire, true);
                return content;
            } catch (Throwable t) {
                t.printStackTrace();
                throw t;
            }
        }

        @Override
        public byte[] serialize(MediaContent content) throws Exception {
            Bytes<?> bytes = wire.bytes();
            bytes.clear();
            Wires.writeMarshallable(content, wire);
            if (bytes.readRemaining() != byteArray.length)
                byteArray = new byte[(int) bytes.readRemaining()];
            bytes.read(byteArray);
            return byteArray;
        }

        @Override
        public String getName() {
            return "chronicle-wire";
        }
    }
}
