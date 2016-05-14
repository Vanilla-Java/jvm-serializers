package serializers.wire.custom;

import data.media.Image;
import data.media.Media;
import data.media.MediaContent;
import data.media.MediaTransformer;
import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.pool.ClassAliasPool;
import net.openhft.chronicle.wire.BinaryWire;
import net.openhft.chronicle.wire.Wire;
import serializers.Serializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomWireSimpleUtils {
    public static WMedia forwardMedia(Media a) {
        return new WMedia(a.uri, a.title, a.width, a.height, a.format, a.duration, a.size, a.bitrate, a.persons, a.player, a.copyright);
    }

    public static Media reverseMedia(WMedia a) {
        return new Media(a.uri, a.title, a.width, a.height, a.format, a.duration, a.size, a.bitrate, true, a.persons, a.player, a.copyright);
    }

    public static WImage forwardImage(Image a) {
        return new WImage(a.uri, a.title, a.width, a.height, a.size);
    }

    public static Image reverseImage(WImage a) {
        return new Image(a.uri, a.title, a.width, a.height, a.size);
    }

    public static List<WImage> forwardImages(List<Image> a) {
        ArrayList<WImage> images = new ArrayList<WImage>(a.size());
        for (Image image : a) {
            images.add(forwardImage(image));
        }
        return images;
    }

    public static List<Image> reverseImages(List<WImage> a) {
        ArrayList<Image> images = new ArrayList<Image>(a.size());
        for (WImage image : a) {
            images.add(reverseImage(image));
        }
        return images;
    }

    public static final class WireTransformer extends MediaTransformer<WMediaContent> {
        @Override
        public WMediaContent[] resultArray(int size) {
            return new WMediaContent[size];
        }

        @Override
        public WMediaContent forward(MediaContent a) {
            return new WMediaContent(forwardMedia(a.media), forwardImages(a.images));
        }

        @Override
        public MediaContent reverse(WMediaContent a) {
            return new MediaContent(reverseMedia(a.media), reverseImages(a.images));
        }

        @Override
        public MediaContent shallowReverse(WMediaContent a) {
            return new MediaContent(reverseMedia(a.media), Collections.<Image>emptyList());
        }
    }

    public static final class WireSerializer extends Serializer<WMediaContent> {
        static {
            ClassAliasPool.CLASS_ALIASES.addAlias(Media.class, "M");
        }

        private final int count;
        Wire wire = new BinaryWire(Bytes.allocateDirect(1024).unchecked(true));
        byte[] byteArray = {};
        WMediaContent content = new WMediaContent(null, null);

        public WireSerializer(int count) {
            this.count = count;
        }

        @Override
        public WMediaContent deserialize(byte[] array) throws Exception {
            try {
                final Bytes<?> b = wire.bytes();
                b.clear();
                b.write(array);
                content.readMarshallable(wire);
                return content;
            } catch (Throwable t) {
                t.printStackTrace();
                throw t;
            }
        }

        @Override
        public byte[] serialize(WMediaContent content) throws Exception {
            final Bytes<?> b = wire.bytes();
            b.clear();
            content.writeMarshallable(wire);
            if (b.readRemaining() != byteArray.length)
                byteArray = new byte[(int) b.readRemaining()];
            b.read(byteArray);
            return byteArray;
        }

        @Override
        public String getName() {
            return "chronicle-wire-custom-" + count;
        }
    }
}
