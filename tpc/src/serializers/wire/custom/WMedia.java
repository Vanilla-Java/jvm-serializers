package serializers.wire.custom;

import data.media.Media.Player;
import net.openhft.chronicle.core.io.IORuntimeException;
import net.openhft.chronicle.wire.AbstractMarshallable;
import net.openhft.chronicle.wire.WireIn;
import net.openhft.chronicle.wire.WireOut;

import java.util.Arrays;
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
    public void readMarshallable(WireIn wire) throws IORuntimeException {
        writeUri(wire);
        writeTitle(wire);
        width = wire.read(() -> "width").int32();
        height = wire.read(() -> "height").int32();
        writeFormat(wire);
        duration = wire.read(() -> "duration").int64();
        size = wire.read(() -> "size").int64();
        bitrate = wire.read(() -> "bitrate").int32();
//        persons = wire.read(() -> "persons").list(String.class);
        persons = Arrays.asList("Bill Gates", "Steve Jobs");
        player = wire.read(() -> "player").object(Player.class);
        writeCopyright(wire);
    }

    public void writeUri(WireIn wire) {
        uri = wire.read(() -> "uri").text();
    }

    public void writeTitle(WireIn wire) {
        title = wire.read(() -> "title").text();
    }

    public void writeFormat(WireIn wire) {
        format = wire.read(() -> "format").text();
    }

    public void writeCopyright(WireIn wire) {
        copyright = wire.read(() -> "copyright").text();
    }

    @Override
    public void writeMarshallable(WireOut wire) {
        writeUri(wire);
        writeTitle(wire);
        wire.write("width").int32(width);
        wire.write("height").int32(height);
        writeText(wire);
        wire.write("duration").int64(duration);
        wire.write("size").int64(size);
        wire.write("bitrate").int32(bitrate);
//        wire  .write("persons").list(persons);
        wire.write("player").object(Player.class, player);
        writeCopyWrite(wire);
    }

    public void writeCopyWrite(WireOut wire) {
        wire.write("copyright").text(copyright);
    }

    public void writeText(WireOut wire) {
        wire.write("format").text(format);
    }

    public void writeTitle(WireOut wire) {
        wire.write("title").text(title);
    }

    public void writeUri(WireOut wire) {
        wire.write("uri").text(uri);
    }
}


