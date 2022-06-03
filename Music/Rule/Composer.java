package Rule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.sound.midi.*;

public class Composer {
    public int beat_per_bar;
    public int note_type;
    public int pulse_per_note;
    public int pulse_per_bar;
    public double chords_per_bar;
    public int pulse_per_chord;
    public int smallest_increment;
    
    public void setParams(int beat_per_bar, int note_type, int pulse_per_note, double chords_per_bar) {
        this.beat_per_bar = beat_per_bar;
        this.note_type = note_type;
        this.pulse_per_note = pulse_per_note;
        this.chords_per_bar = chords_per_bar;
        this.pulse_per_bar = (pulse_per_note >> note_type) * beat_per_bar;
        pulse_per_chord = (int)(pulse_per_bar * chords_per_bar);
        smallest_increment = pulse_per_note >> 8;
    }

    public void getPlayingPattern(ArrayList<ArrayList<Integer>> line) {
        Random random = new Random();
        int cur_pulse = 0;
        while (cur_pulse < pulse_per_chord) {
            boolean play = random.nextBoolean();
            //System.out.println("next: " + Integer.max(pulse_per_chord - smallest_increment - cur_pulse, 0));
            int duration = ((random.nextInt(Integer.max(pulse_per_chord - cur_pulse, 0)) / smallest_increment + 1)) * smallest_increment;
            if (play) {
                line.add(new ArrayList<Integer>(Arrays.asList(cur_pulse, duration)));
            }
            cur_pulse += duration;
        }
    }

    public void addchords(Track track, int start, int instrument, int down, int up, int bar_num, int base_key, boolean is_minor) {
        Progression p = new Progression(base_key, is_minor);
        ArrayList<ArrayList<Integer>> chords = p.getChordProgression((int)chords_per_bar * bar_num);
        ArrayList<ArrayList<ArrayList<Integer>>> lines = new ArrayList<ArrayList<ArrayList<Integer>>>();

        p.voiceLead(chords);
        for (int k = 0; k < chords.size(); ++k) {
            ArrayList<Integer> chord = chords.get(k);
            int chord_start = start + k * pulse_per_chord;
            System.out.println(chord.toString());
            for (int i = 0; i < chord.size(); ++i) {
                if (i >= lines.size()) {
                    lines.add(new ArrayList<ArrayList<Integer>>());
                    getPlayingPattern(lines.get(i));
                }
                ArrayList<ArrayList<Integer>> line = lines.get(i);
                for (ArrayList<Integer> play : line) {
                    try {
                        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, instrument, chord.get(i), down), chord_start + play.get(0)));
                        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, instrument, chord.get(i), up), chord_start + play.get(0) + play.get(1)));
                    }
                    catch (Exception e) {
                        e.printStackTrace(System.out);
                    }
                }
                
            }
        }
        System.out.println(lines);
    }
}
