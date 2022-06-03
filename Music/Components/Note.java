package Components;
import javax.sound.midi.*;

public class Note {
    public static int tick_per_increment;
    public int start;
    public int note_type;
    public int distance;
    public void addNoteToTrack(Track track, int instrument, int start, int base, int down, int up) throws Exception {
        int actual_start = start + this.start * tick_per_increment;
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, instrument, distance + base, down), actual_start));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, instrument, distance + base, up), actual_start + (tick_per_increment << note_type)));
    }
}
