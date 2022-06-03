package Composers;

import java.util.ArrayList;
import java.util.Random;
import Rule.Progression;

public class TonicComposer {
    public boolean is_minor;
    public ArrayList<Integer> generateProgression(int length, int strong_cadence) {
        ArrayList<Integer> progression = new ArrayList<Integer>(length);
        Random random = new Random();
        int cur = random.nextInt(7);
        progression.add(cur);
        while (progression.size() < length) {
            if (progression.size() == length - 1) {
                if (cur == 1 || cur == 5) {
                    progression.set(length - 2, cur = 3);
                }
                ArrayList<Integer> nexts = Progression.getNextAsCadence(cur);
                progression.add(nexts.get(random.nextInt(nexts.size())));
                break;
            }
            ArrayList<Integer> nexts = Progression.tonic_graph.get(cur);
            cur = nexts.get(random.nextInt(nexts.size()));
            progression.add(cur);
        }
        return progression;
    }


}
