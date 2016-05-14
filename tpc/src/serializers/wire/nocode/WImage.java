package serializers.wire.nocode;

import data.media.Image.Size;
import net.openhft.chronicle.wire.AbstractMarshallable;

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
}
