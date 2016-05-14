package serializers.wire.custom;

import net.openhft.chronicle.core.io.IORuntimeException;
import net.openhft.chronicle.wire.AbstractMarshallable;
import net.openhft.chronicle.wire.WireIn;
import net.openhft.chronicle.wire.WireOut;

import java.util.ArrayList;
import java.util.List;

public class WMediaContent extends AbstractMarshallable {
    WMedia media;
    List<WImage> images;
    transient int imageCount = 0;

    public WMediaContent(WMedia media, List<WImage> images) {
        this.media = media;
        this.images = images;
    }

    @Override
    public void readMarshallable(WireIn wire) throws IORuntimeException {
        if (media == null)
            media = new WMedia();
        if (images == null)
            images = new ArrayList<>();
        wire.read(() -> "media").marshallable(media);
        imageCount = 0;
        wire.read(() -> "images").list(this, WMediaContent::getNextWImage);
    }

    WImage getNextWImage() {
        while (images.size() <= imageCount)
            images.add(new WImage(null, null, 0, 0, null));
        return images.get(imageCount++);
    }

    @Override
    public void writeMarshallable(WireOut wire) {
        wire.write("media").marshallable(media);
        wire.write("images").list(images);
    }
}
