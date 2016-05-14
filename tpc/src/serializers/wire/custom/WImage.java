package serializers.wire.custom;

import data.media.Image.Size;
import net.openhft.chronicle.core.io.IORuntimeException;
import net.openhft.chronicle.wire.AbstractMarshallable;
import net.openhft.chronicle.wire.WireIn;
import net.openhft.chronicle.wire.WireOut;

public class WImage extends AbstractMarshallable {
    String uri;
    String title;
    int width;
    int height;
    Size size;

    public WImage(String uri, String title, int width, int height, Size size) {
        this.uri = uri;
        this.title = title;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    @Override
    public void readMarshallable(WireIn wire) throws IORuntimeException {
        uri = wire.read(() -> "uri").text();
        title = wire.read(() -> "title").text();
        width = wire.read(() -> "width").int32();
        height = wire.read(() -> "height").int32();
        size = wire.read(() -> "size").object(Size.class);
    }

    @Override
    public void writeMarshallable(WireOut wire) {
        writeUri(wire);
        writeTitle(wire);
        wire.write("width").int32(width);
        wire.write("height").int32(height);
        writeSize(wire);
    }

    public void writeSize(WireOut wire) {
        wire.write("size").object(Size.class, size);
    }

    public void writeTitle(WireOut wire) {
        wire.write("title").text(title);
    }

    public void writeUri(WireOut wire) {
        wire.write("uri").text(uri);
    }
}
