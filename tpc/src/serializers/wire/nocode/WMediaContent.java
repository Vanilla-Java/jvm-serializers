package serializers.wire.nocode;

import net.openhft.chronicle.wire.AbstractMarshallable;

import java.util.List;

public class WMediaContent extends AbstractMarshallable {
    WMedia media;
    List<WImage> images;

    public WMediaContent(WMedia media, List<WImage> images) {
        this.media = media;
        this.images = images;
    }
}
