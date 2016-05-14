package serializers.wire.bytemarshallable;

import data.media.Media.Player;
import net.openhft.chronicle.bytes.BytesIn;
import net.openhft.chronicle.bytes.BytesMarshallable;
import net.openhft.chronicle.bytes.BytesOut;

import java.util.ArrayList;
import java.util.List;

public class WMedia implements BytesMarshallable {
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

    public WMedia() {
    }

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

    @Override
    public void readMarshallable(BytesIn bytes) {
        uri = bytes.read8bit();
        title = bytes.read8bit();
        width = (int) bytes.readStopBit();
        height = (int) bytes.readStopBit();
        format = bytes.read8bit();
        duration = bytes.readStopBit();
        size = bytes.readStopBit();
        bitrate = (int) bytes.readStopBit();
        int len = (int) bytes.readStopBit();
        if (persons == null)
            persons = new ArrayList<>();
        else
            persons.clear();
        for (int i = 0; i < len; i++)
            persons.add(bytes.read8bit());
        player = Player.valueOf(bytes.readUtf8());
        copyright = bytes.read8bit();
    }

    @Override
    public void writeMarshallable(BytesOut bytes) {
        bytes.write8bit(uri);
        bytes.write8bit(title);
        bytes.writeStopBit(width);
        bytes.writeStopBit(height);
        bytes.write8bit(format);
        bytes.writeStopBit(duration);
        bytes.writeStopBit(size);
        bytes.writeStopBit(bitrate);
        bytes.writeStopBit(persons.size());
        for (String s : persons) bytes.writeUtf8(s);
        bytes.write8bit(player.name());
        bytes.write8bit(copyright);
    }
}


