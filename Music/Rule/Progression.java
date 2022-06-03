package Rule;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Progression {
    public enum Functional {
        TONIC,
        SUB_DOMINANT,
        DOMINANT
    }

    public static final HashMap<Functional, ArrayList<Functional>> harmony = initHarmony();
    public static final HashMap<Functional, ArrayList<Integer>> functional_chord = initFunctionalChord();
    public static final HashMap<Integer, ArrayList<Integer>> tonic_graph = initTonicGraph();
    public static final byte minor_bit_mask = (byte)(1 << 7);
    public static final byte diminished_bit_mask = (byte)(1 << 6);
    public static final byte[] major_scale = {0, 2, 4, 5, 7, 9, 10};
    public static final byte[] major_degree ={0, 
        minor_bit_mask, 
        minor_bit_mask, 
        0, 
        0, 
        minor_bit_mask, 
        diminished_bit_mask
    };
    public static final byte[] minor_degree ={minor_bit_mask, 
        diminished_bit_mask, 
        0, 
        minor_bit_mask, 
        minor_bit_mask, 
        0, 
        0
    };
    public static final byte[] minor_scale = {0, 2, 3, 5, 7, 8, 10};

    public static HashMap<Functional, ArrayList<Functional>> initHarmony() {
        HashMap<Functional, ArrayList<Functional>> result = new HashMap<Functional, ArrayList<Functional>>();
        result.put(Functional.TONIC, new ArrayList<Functional>(Arrays.asList(Functional.TONIC, Functional.SUB_DOMINANT, Functional.DOMINANT)));
        result.put(Functional.SUB_DOMINANT, new ArrayList<Functional>(Arrays.asList(Functional.DOMINANT)));
        result.put(Functional.DOMINANT, new ArrayList<Functional>(Arrays.asList(Functional.TONIC)));
        return result;
    }

    public static HashMap<Functional, ArrayList<Integer>> initFunctionalChord() {
        HashMap<Functional, ArrayList<Integer>> result = new HashMap<Functional, ArrayList<Integer>>();
        result.put(Functional.TONIC, new ArrayList<Integer>(Arrays.asList(0, 2)));
        result.put(Functional.SUB_DOMINANT, new ArrayList<Integer>(Arrays.asList(1, 3, 5)));
        result.put(Functional.DOMINANT, new ArrayList<Integer>(Arrays.asList(4, 6)));
        return result;
    }

    public static HashMap<Integer, ArrayList<Integer>> initTonicGraph() {
        HashMap<Integer, ArrayList<Integer>> result = new HashMap<Integer, ArrayList<Integer>>();
        for (Map.Entry<Functional, ArrayList<Functional>> entry : harmony.entrySet()) {
            ArrayList<Integer> group = functional_chord.get(entry.getKey());
            for (Integer e : group) {
                result.put(e, new ArrayList<Integer>());
                for (Functional f : entry.getValue()) {
                    result.get(e).addAll(functional_chord.get(f));
                }
            }
        }
        result.get(4).add(5);
        result.get(3).add(0);
        return result;
    }

    public static ArrayList<Integer> getNextSrongCadence(int cur) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        switch (cur) {
            case 4:
            case 3:
                result.add(0);
                break;
        }
        return result;
    }

    public static ArrayList<Integer> getNextWeakCadence(int cur) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        switch (cur) {
            case 0:
            case 1:
            case 3:
                result.add(4);
                break;
            case 4:
                result.add(5);
                break;
        }
        return result;
    }

    public static ArrayList<Integer> getNextAsCadence(int cur) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        switch (cur) {
            case 4:
                result.add(5);
            case 0:
            case 2:
            case 6:
            case 3:
                result.add(0);
                break;
        }
        return result;
    }

    public static HashMap<Byte, HashSet<Byte>> getKeysInTone() {
        HashMap<Byte, HashSet<Byte>> result = new HashMap<Byte, HashSet<Byte>>();
        for (byte k = 0; k < 12; ++k) {
            result.put(k, new HashSet<Byte>());
            byte km = (byte)(k | minor_bit_mask);
            result.put(km, new HashSet<Byte>());
            for (int i = 0; i < major_scale.length; ++i) {
                result.get(k).add((byte)(((k + major_scale[i]) % 12) | major_degree[i]));
                result.get(km).add((byte)(((k + minor_scale[i]) % 12) | minor_degree[i]));
            }
        }
        return result;
    }

    public static final HashMap<Byte, HashSet<Byte>> keys_in_tone = getKeysInTone();
    public static HashMap<Byte, ArrayList<Byte>> progression_map;

    public int base;
    public boolean is_minor;

    public Progression(int base, boolean is_minor) {
        this.base = base;
        this.is_minor = is_minor;
    }

    public ArrayList<ArrayList<Integer>> getChordProgression(int length) {
        ArrayList<Integer> schema = new ArrayList<Integer>(length);
        createProgression(schema, length);
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        byte[] degree = is_minor ? minor_degree : major_degree;
        for (Integer e : schema) {
            int root = base + e;
            switch (degree[e]) {
                case minor_bit_mask:
                    result.add(new ArrayList<Integer>(Arrays.asList(root + minor_scale[0], root + minor_scale[2], root + minor_scale[4])));
                    System.out.println("" + e + "m");
                    break;
                case diminished_bit_mask:
                    result.add(new ArrayList<Integer>(Arrays.asList(root, root + 3, root + 6)));
                    System.out.println("" + e + "d");
                    break;
                case 0:
                    result.add(new ArrayList<Integer>(Arrays.asList(root + major_scale[0], root + major_scale[2], root + major_scale[4])));
                    System.out.println(e);
                    break;
            }
        }
        return result;
    }

    public void voiceLead(ArrayList<ArrayList<Integer>> progression) {
        for (ArrayList<Integer> chord :progression) {
            for (Integer e : chord) {
                if (e - base >= 12) {
                    e -= 12;
                }
            }
            Collections.sort(chord);
        }
    }

    private int[] getNeighbourIndex(int cur, int length) {
        return new int[]{(cur + length - 1) % length, cur + 1 % length};
    }

    private int totalDistanceToClosest(ArrayList<Integer> left, ArrayList<Integer> mid, ArrayList<Integer> right) {
        int total = 0;
        for (Integer e : mid) {
            int min_left = 256;
            int min_right = 256;
            for (Integer l : left) {
                int diff = (e - l) * (e - l);
                if (diff < min_left) {
                    min_left = diff;
                }
            }
            for (Integer r : right) {
                int diff = (e - r) * (e - r);
                if (diff < min_right) {
                    min_right = diff;
                }
            }
            total += min_left += min_right;
        }
        return total;
    }

    private void createProgression(ArrayList<Integer> progression, int length) {
        Random random = new Random();
        int cur = random.nextInt(7);
        progression.add(cur);
        while (progression.size() < length) {
            if (progression.size() == length - 1) {
                if (cur == 1 || cur == 5) {
                    progression.set(length - 2, cur = 3);
                }
                ArrayList<Integer> nexts = getNextAsCadence(cur);
                progression.add(nexts.get(random.nextInt(nexts.size())));
                break;
            }
            ArrayList<Integer> nexts = tonic_graph.get(cur);
            cur = nexts.get(random.nextInt(nexts.size()));
            progression.add(cur);
        }
    }

    /*public boolean inKey(byte chord) {
        byte raw_key = (byte)(key & ~(minor_bit_mask | diminished_bit_mask));
        if ((key & minor_bit_mask) == 0) {
            byte raw_chord = 
            byte diff = 
        }
    }
    
    public static byte[] getNext(byte chord_value) {
        byte raw_chord = (byte)(chord_value & ~last_bit_mask);
        byte raw_chord7 = (byte)((raw_chord + 7) % 12);
        byte raw_chord14 = (byte)((raw_chord + 14) % 12);
        byte raw_chord_neg7 = (byte)((raw_chord + 5) % 12);
        byte raw_chord_neg14 = (byte)((raw_chord + 10) % 12);
        if ((chord_value & last_bit_mask) == 0) {
            return new byte[]{raw_chord, 
                raw_chord7, 
                raw_chord14, 
                raw_chord_neg7, 
                raw_chord_neg14, 
                (byte)(((raw_chord + 3) % 12) | last_bit_mask), 
                (byte)(((raw_chord7 + 3) % 12) | last_bit_mask), 
                (byte)(((raw_chord_neg7 + 3) % 12) | last_bit_mask)
            };
        }
        return new byte[]{(byte) (raw_chord | last_bit_mask), 
            (byte) (raw_chord7 | last_bit_mask), 
            (byte) (raw_chord14 | last_bit_mask), 
            (byte) (raw_chord_neg7 | last_bit_mask), 
            (byte) (raw_chord_neg14 | last_bit_mask), 
            (byte) ((raw_chord + 9) % 12), 
            (byte) ((raw_chord7 + 9) % 12), 
            (byte) ((raw_chord_neg7 + 9) % 12)
        };
    }

    public static boolean isEnd(byte cur, byte next) {
        return (cur & last_bit_mask) == 0 && ((next + 7) % 12 == cur || ((next & ~last_bit_mask) + 7) % 12 == cur);
    }

    public static byte[] generateProgession(int min_length, int max_length, byte end_of_progression) {
        byte[] result = new byte[max_length];
        Random random = new Random();
        result[0] = (byte) random.nextInt(12);
        if (random.nextBoolean())
        {
            result[0] |= last_bit_mask;
        }
        int index = 0;
        while (true) {
            if (index == max_length - 3 && (result[index] & last_bit_mask) == 0) {
                result[index + 1] = (byte) (random.nextBoolean() ? ((result[index] + 5) % 12) : ((result[index] + 5) % 12) | last_bit_mask);
                result[max_length - 1] = end_of_progression;
                return result;
            }
            byte[] nexts = getNext(result[index]);
            if (index == max_length - 4 && (result[index] & last_bit_mask) != 0) {
                result[++index] = nexts[random.nextInt(3) + 5];
            }
            else {
                byte next = nexts[random.nextInt(nexts.length)];
                if (index >= min_length - 3 && isEnd(result[index], next)) {
                    result[index + 1] = next;
                    result[index + 2] = end_of_progression;
                    return result;
                }
                result[++index] = next;
            }
        }
    }

    public static int[][] generateChordsProgressionValue(int min_length, int max_length, int base_note) {
        byte[] progression = generateProgession(min_length, max_length, (byte)127);
        int[][] result = new int[progression.length][];
        for (int k = 0; k < progression.length && progression[k] != (byte)127; ++k) {
            int raw = (progression[k] & ~last_bit_mask) + base_note;
            System.out.println("" + raw + " " + (progression[k] & last_bit_mask));
            if ((progression[k] & last_bit_mask) == 0) {
                result[k] = new int[]{raw, raw + major_scale[2], raw + major_scale[4]};
            }
            else {
                result[k] = new int[]{raw, raw + minor_scale[2], raw + minor_scale[4]};
            }
        }
        return result;
    }*/

}
