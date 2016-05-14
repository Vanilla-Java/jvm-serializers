package serializers.wire.bytemarshallable;

import data.media.Image.Size;
import net.openhft.chronicle.bytes.BytesIn;
import net.openhft.chronicle.bytes.BytesMarshallable;
import net.openhft.chronicle.bytes.BytesOut;

public class WImage implements BytesMarshallable {
    String uri;
    String title;
    int width;
    int height;
    Size size;

    public WImage() {
    }

    public WImage(String uri, String title, int width, int height, Size size) {
        this.uri = uri;
        this.title = title;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    @Override
    public void readMarshallable(BytesIn bytes) {
        uri = bytes.read8bit();
        title = bytes.read8bit();
        width = bytes.readInt();
        height = bytes.readInt();
        size = Size.valueOf(bytes.read8bit());

    }

    @Override
    public void writeMarshallable(BytesOut bytes) {
        bytes.writeUtf8(uri);
        bytes.writeUtf8(title);
        bytes.writeInt(width);
        bytes.writeInt(height);
        bytes.writeUtf8(size.name());
    }
}
