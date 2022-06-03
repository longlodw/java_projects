import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.Test;

public class SparseListUnitTester {

	@Test
	public void constructorTester10() {
		Field[] fields = SparseList.class.getDeclaredFields();
		for (Field f: fields){
			assertTrue("SparseList contains a public field", 
					!Modifier.isPublic(f.getModifiers()));
		}

		assertTrue ("Number of constructors != 2", 
				SparseList.class.getDeclaredConstructors().length == 2);

		assertTrue ("List interface not implemented or other interfaces are", 
				SparseList.class.getInterfaces().length == 1 
				&& SparseList.class.getInterfaces()[0].getName().equals("java.util.List"));
	}

	@Test
	public void addTester1_5() {
		SparseList<String> list = new SparseList<>();
		assertTrue(list.size() == 0);
		list.add("1");
		assertTrue(list.size() == 1);
	}

	@Test
	public void addTester2_5() {
		SparseList<String> list = new SparseList<>();
		assertTrue(list.size() == 0);
		list.add(999, "999");
		assertTrue(list.size() == 1000);
	}

	@Test
	public void addTester3_5() {
		SparseList<Integer> list = new SparseList<>();
		list.add(0);
		list.add(2, 2);
		list.add(1, 1);
		assertTrue("Add_4 failed", list.get(0) == 0 && list.get(1) == 1 
				&& list.get(2) == null && list.get(3) == 2);
	}

	@Test
	public void addTester4_5() {
		long memoryBefore, memoryAfter;
		SparseList<String> list = new SparseList<>();

		Runtime.getRuntime().gc();
		memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		//System.out.println(memoryBefore + " bytes");

		list.add(999999, "999999");
		
		Runtime.getRuntime().gc();
		memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		//System.out.println(memoryAfter + " bytes");

		assertTrue("Inefficient Use of Memory", memoryAfter - memoryBefore < 10000);
	}

	@Test
	public void removeTester1_5() {
		SparseList<String> list = new SparseList<>();
		//check for exception
		try{
			list.remove(0);
			fail("Exception was to be thrown");
		}catch (IndexOutOfBoundsException e){
			//OK
		}catch (Exception ex) {
			fail("Wrong type of exception");
		}
	}

	@Test
	public void removeTester2_5() {
		SparseList<String> list = new SparseList<>();
		assertTrue(list.size() == 0);
		list.add("1");
		assertTrue(list.size() == 1);
		list.remove(0);
		assertTrue(list.size() == 0);
	}

	@Test
	public void removeTester3_5() {
		SparseList<Integer> list = new SparseList<>();
		for(int i = 0; i < 10; i++) list.add(i);
		assertTrue(list.size() == 10);
		list.remove(0);
		assertTrue(list.size() == 9);
		for(int i = 0; i < 9; i++) assertTrue(list.get(i) == i + 1);

		list.remove(4);
		assertTrue(list.size() == 8);
		for(int i = 0; i < 4; i++) assertTrue(list.get(i) == i + 1);
		for(int i = 4; i < 8; i++) assertTrue(list.get(i) == i + 2);
	}

	@Test
	public void removeTester4_5() {
		long memoryBefore, memoryAfter;
		SparseList<String> list = new SparseList<>();
		list.add(999999, "999999"); //1M sparse elements
		
		Runtime.getRuntime().gc();
		memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		//System.out.println(memoryBefore + " bytes");

		list.remove(1);
		
		Runtime.getRuntime().gc();
		memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		//System.out.println(memoryAfter + " bytes");

		assertTrue("Inefficient Use of Memory", memoryAfter - memoryBefore < 10000);
	}

	@Test
	public void getTester1_5() {
		SparseList<String> list = new SparseList<>();
		//check for exception
		try{
			list.get(0);
			fail("Exception was to be thrown");
		}catch (IndexOutOfBoundsException e){
			//OK
		}catch (Exception ex) {
			fail("Wrong type of exception");
		}
	}

	@Test
	public void getTester2_5() {
		SparseList<String> list = new SparseList<>("2.5");
		list.add(999, "999");
		assertTrue(list.get(999).equals("999"));
		assertTrue(list.get(998).equals("2.5"));
	}

	@Test
	public void setTester1_5() {
		SparseList<String> list = new SparseList<>();
		//check for exception
		try{
			list.set(0, "1");
			fail("Exception was to be thrown");
		}catch (IndexOutOfBoundsException e){
			//OK
		}catch (Exception ex) {
			fail("Wrong type of exception");
		}
	}

	@Test
	public void setTester2_5() {
		SparseList<String> list = new SparseList<>();
		list.add("1"); list.add("2"); list.add("3");
		list.set(1, "22");
		assertTrue(list.get(1).equals("22"));
		assertTrue(list.size() == 3);
	}

	@Test
	public void sizeTester5() {
		SparseList<String> list = new SparseList<>();
		assertTrue(list.size() == 0);
		list.add("1");
		assertTrue(list.size() == 1);
		list.add(9, "9");
		assertTrue(list.size() == 10);
	}

	@Test
	public void clearTester5() {
		SparseList<String> list = new SparseList<>();
		assertTrue(list.isEmpty());
		list.add("1");
		assertTrue(!list.isEmpty());
		list.clear();
		assertTrue(list.isEmpty());
		assertTrue(list.size() == 0);
	}

	@Test
	public void isEmptyTester5() {
		SparseList<String> list = new SparseList<>();
		assertTrue(list.isEmpty());
		list.add("1");
		assertTrue(!list.isEmpty());
	}

	@Test
	public void toStringTester1_5() {
		SparseList<String> list = new SparseList<>();
		list.add("1"); list.add("2"); list.add("3");
		assertTrue(list.toString().equals("[1, 2, 3]"));
	}

	@Test
	public void toStringTester2_5() {
		SparseList<String> list = new SparseList<>();
		list.add(2, "3");
		assertTrue(list.toString().equals("[null, null, 3]"));
	}

	@Test
	public void toStringTester3_5() {
		SparseList<String> list = new SparseList<>("boo");
		list.add(2, "3");
		assertTrue(list.toString().equals("[boo, boo, 3]"));
	}

}