import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TenLinkedList<E> implements List<E> {

    public TenLinkedList() {
        head = null;
        tail = null;
        length = 0;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        String result = "";
        Node<E> cur = head;
        if (head != null) {
            result += head.value.toString();
            cur = head.right;
        }
        while (cur != null) {
            result += ", " + cur.value;
            cur = cur.right;
        }
        return "[" + result + "]";
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean add(E arg0) {
        Node<E> temp = new Node<E>();
        temp.value = arg0;
        return addNode(temp);
    }

    
    /** 
     * @param arg0
     * @param arg1
     */
    @Override
    public void add(int arg0, E arg1) {
        if (arg0 == length) {
            add(arg1);
            return;
        }
        Node<E> cur = getNode(arg0);
        Node<E> old_tail = tail;
        int old_length = length;
        length = arg0;
        tail = cur.left;
        if (length == 0) {
            head = null;
        }
        Node<E> temp = new Node<E>();
        temp.value = arg1;
        addNode(temp);
        for (int k = 0; k < 10 && cur != null; k++, cur = cur.right) {
            addNode(cur);
        }
        length = old_length + 1;
        tail = old_tail;
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /** 
     * @param arg0
     * @param arg1
     * @return boolean
     */
    @Override
    public boolean addAll(int arg0, Collection<? extends E> arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        length = 0;
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean contains(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean containsAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /** 
     * @param arg0
     * @return E
     */
    @Override
    public E get(int arg0) {
        return getNode(arg0).value;
    }

    
    /** 
     * @param arg0
     * @return int
     */
    @Override
    public int indexOf(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    
    /** 
     * @return Iterator<E>
     */
    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @param arg0
     * @return int
     */
    @Override
    public int lastIndexOf(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    /** 
     * @return ListIterator<E>
     */
    @Override
    public ListIterator<E> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @param arg0
     * @return ListIterator<E>
     */
    @Override
    public ListIterator<E> listIterator(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean remove(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /** 
     * @param arg0
     * @return E
     */
    @Override
    public E remove(int arg0) {
        Node<E> result = getNode(arg0);
        Node<E> old_tail = tail;
        int old_length = length;
        length = arg0;
        tail = result.left;
        if (tail != null) {
            tail.right = null;
            tail.right10 = null;
        }
        if (length == 0) {
            head = null;
        }
        Node<E> cur = result.right;
        if (cur == null) {
            length = old_length - 1;
            return result.value;
        }
        for (int k = 0; k < 10 && cur != null; k++, cur = cur.right) {
            addNode(cur);
        }
        length = old_length - 1;
        tail = old_tail;
        return result.value;
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean removeAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /** 
     * @param arg0
     * @return boolean
     */
    @Override
    public boolean retainAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    
    /** 
     * @param arg0
     * @param arg1
     * @return E
     */
    @Override
    public E set(int arg0, E arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @return int
     */
    @Override
    public int size() {
        return length;
    }

    
    /** 
     * @param arg0
     * @param arg1
     * @return List<E>
     */
    @Override
    public List<E> subList(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @return Object[]
     */
    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @param arg0
     * @return T[]
     */
    @Override
    public <T> T[] toArray(T[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private static class Node<E> {
        E value = null;
        Node<E> left = null;
        Node<E> right = null;
        Node<E> left10 = null;
        Node<E> right10 = null;
    }

    private static interface Iterable<E> {
        Node<E> next(Node<E> cur);
    }
    
    private Node<E> head;
    private Node<E> tail;
    private int length;
    
    /** 
     * @param index
     * @return Node<E>
     */
    private Node<E> getNode(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
        int neg_index = length - 1 - index;
        int steps = index;
        boolean down = false;
        Node<E> cur = head;
        if (neg_index < index) {
            steps = neg_index;
            down = true;
            cur = tail;
        }
        int jump10 = steps / 10;
        int jump1 = steps % 10;
        boolean overshoot = false;
        Iterable<E> next10 = (e) -> e.right10;
        Iterable<E> next = (e) -> e.right;
        if (jump1 > 5) {
            jump10++;
            jump1 = 10 - jump1;
            overshoot = true;
        }
        if (down) {
            next10 = (e -> e.left10);
            if (!overshoot) {
                next = (e) -> e.left;
            }
        }
        else if (overshoot) {
            next = (e) -> e.left;
        }
        for (int k = 0; k < jump10; k++) {
            cur = next10.next(cur);
        }
        for (int k = 0; k < jump1; k++) {
            cur = next.next(cur);
        }
        return cur;
    }

    
    /** 
     * @param node
     * @return boolean
     */
    private boolean addNode(Node<E> node) {
        if (length == 0) {
            head = node;
            tail = node;
        }
        else {
            tail.right = node;
            node.left = tail;
            if (tail.left10 != null) {
                node.left10 = tail.left10.right;
                tail.left10.right.right10 = node;
            }
            else if (length == 10) {
                node.left10 = head;
                head.right10 = node;
            }
        }
        ++length;
        tail = node;
        return true;
    }
}