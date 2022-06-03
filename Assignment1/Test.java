public class Test {
    public static void main(String[] args) {
        TenLinkedList<Integer> l = new TenLinkedList<Integer>();
        for (int k = 0; k < 28; k++) {
            l.add(k);
        }
        System.out.println(l);
        System.out.println(l.size());
        l.add(0, 0);
        l.add(29, 29);
        l.add(10, 0);
        System.out.println(l);
        System.out.println(l.size());
        l.remove(10);
        l.remove(l.size() - 1);
        l.remove(0);
        System.out.println(l);
        System.out.println(l.size());
        for (int k = 0; k < l.size(); k++) {
            System.out.println(l.get(k));
        }
        l.clear();
        System.out.println(l);
        System.out.println(l.size());
    }
}
