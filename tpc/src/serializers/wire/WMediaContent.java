package serializers.wire;

import net.openhft.chronicle.wire.AbstractMarshallable;

import java.util.List;

public class WMediaContent extends AbstractMarshallable {
    List<WImage> images;
    WMedia media;


    public WMediaContent(List<WImage> images, WMedia media) {
        this.images = images;
        this.media = media;
    }
}
