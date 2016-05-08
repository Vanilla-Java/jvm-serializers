package serializers.wire;

import data.media.Media.Player;
import net.openhft.chronicle.wire.AbstractMarshallable;

import java.util.List;

public class WMedia extends AbstractMarshallable {
    String uri;
    String title;
    int width;
    int height;
    String format;
    long duration;
    long size;
    int bitrate;
    List<String> persons;
    Player player;
    String copyright;

    public WMedia(String uri, String title, int width, int height, String format, long duration, long size, int bitrate,
                  List<String> persons, Player player, String copyright) {
        this.uri = uri;
        this.title = title;
        this.width = width;
        this.height = height;
        this.format = format;
        this.duration = duration;
        this.size = size;
        this.bitrate = bitrate;
        this.persons = persons;
        this.player = player;
        this.copyright = copyright;
    }
}


