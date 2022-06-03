import javax.sound.midi.*;
import java.io.File;
import java.util.ArrayList;

import Rule.Composer;
import Rule.Progression;

public class Main {
    public static void main(String[] args) {
        try {
            //int[][] chords_progression = Progression.generateChordsProgressionValue(5, 9, 60);
            //Progression progression = new Progression(60, true);
            /*ArrayList<ArrayList<Integer>> chords_progression = progression.getChordProgression(8);
            progression.voiceLead(chords_progression);*/
            Sequence sequence = new Sequence(Sequence.PPQ, 256);
            Track t = sequence.createTrack();
            Composer com = new Composer();
            com.setParams(4, 4, 1024, 1);
            com.addchords(t, 0, 2, 127, 127, 8, 62, true);
            /*for (int k = 0; k < chords_progression.size(); ++k) {
                ArrayList<Integer> chord = chords_progression.get(k);
                for (int i = 0; i < chord.size(); ++i) {
                    t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, chord.get(i), 93), k * 256));
                    t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, chord.get(i), 93), (k + 1) * 256));
                }
            }*/
            //t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 93), 0));
            //t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 93), 8));
            File out = new File("./out.mid");
            MidiSystem.write(sequence, 0, out);
        } catch (Exception e) {
            System.out.println("failed");
            System.err.println(e);
            e.printStackTrace(System.out);
        }
    }
}