package serializers.wire.bytemarshallable;

import net.openhft.chronicle.bytes.BytesIn;
import net.openhft.chronicle.bytes.BytesMarshallable;
import net.openhft.chronicle.bytes.BytesOut;

import java.util.ArrayList;
import java.util.List;

public class WMediaContent implements BytesMarshallable {
    WMedia media;
    List<WImage> images;

    public WMediaContent(WMedia media, List<WImage> images) {
        this.media = media;
        this.images = images;
    }

    @Override
    public void readMarshallable(BytesIn bytes) {
        if (media == null)
            media = new WMedia();
        media.readMarshallable(bytes);
        if (images == null)
            images = new ArrayList<>();
        int len = (int) bytes.readStopBit();
        while (images.size() < len)
            images.add(new WImage());
        for (int i = 0; i < len; i++) {
            images.get(i).readMarshallable(bytes);
        }

    }

    @Override
    public void writeMarshallable(BytesOut bytes) {
        media.writeMarshallable(bytes);
        bytes.writeStopBit(images.size());
        for (WImage image : images) {
            image.writeMarshallable(bytes);
        }
    }
}
