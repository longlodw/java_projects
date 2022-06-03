package Components;
import java.util.ArrayList;

import javax.sound.midi.Track;

public class Voice {
    int instrument;
    int base;
    int down;
    int up;
    int start;
    ArrayList<Note> line;

    public void addVoiceToTrack(Track track) throws Exception {
        for (Note e: line) {
            e.addNoteToTrack(track, instrument, start, base, down, up);
        }
    }
}
