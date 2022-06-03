import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

public class SparseList <E> implements List <E>{
	private final E DEFAULT_VALUE;
	private TreeMap <Integer, E> map;
	private int size;
	
	SparseList(){
		this(null);
	}

	SparseList(E defaultValue){
		this.DEFAULT_VALUE = defaultValue;
		map = new TreeMap<>();
	}

	@Override
	public boolean add(E e) {
		// could also call add(size, e);
		map.put(size, e);
		this.size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index >= this.size){
			map.put(index, element);
			this.size = index + 1;
		}else{
			for (int i = this.size ; i >= index; i--){
				if (map.containsKey(i))
					map.put(i + 1, map.get(i));
				else
					map.remove(i + 1);
			}
			map.put(index, element);
			this.size++;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		map.clear();
		this.size = 0;
	}

	@Override
	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException();
		E value = map.get(index); 
		if (value != null) return value;
		else return this.DEFAULT_VALUE;
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException();
		
		E removed = map.remove(index);
		for (int i = index; i < this.size-1; i++){
			if (map.containsKey(i + 1))
				map.put(i, map.get(i + 1));
			else
				map.remove(i);
		}
		map.remove(this.size - 1);
		this.size--;
		return removed;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException();
		E oldValue = map.get(index);
		map.put(index, element);
		return oldValue;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		Object [] array = new Object[this.size];
		for (int i = 0; i < this.size; i++){
			if (map.containsKey(i))
				array[i] = map.get(i);
			else
				array[i] = this.DEFAULT_VALUE;
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (!a.getClass().getComponentType().isAssignableFrom(this.DEFAULT_VALUE.getClass()))
			throw new ArrayStoreException();
		
		if (a.length < this.size) a = Arrays.copyOf(a, this.size);
		for (int i = 0; i < this.size; i++){
			if (map.containsKey(i)) 
				a[i] = (T) map.get(i);
			else 
				a[i] = (T) this.DEFAULT_VALUE;
		}
		return a;
	}

	
	@Override
	public String toString(){
		if (this.size == 0) return "[]"; 
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < this.size - 1; i++){
			E value = (E) this.map.get(i);
			if (value != null) 
				sb.append(value);
			else 
				sb.append(this.DEFAULT_VALUE);
			sb.append(", ");
		}

		E value = (E) this.map.get(this.size - 1);
		if (value != null) 
			sb.append(value);
		else 
			sb.append(this.DEFAULT_VALUE);
		//sb.append(map.get(this.size - 1));
		sb.append(']');
		return sb.toString(); //+ " " + map.toString();
	}
}
