package testLinkedList;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utilities.SLL;
import utilities.SLLNode;

public class ListTest {
	
	SLL sll;
	
	@Before
	public void loadList() {
		
		sll = new SLL();
		sll.append("Yee");
		sll.append("Haw");
		sll.append("Hi Khosro");
		
	}
	
	@Test
	public void TestAppend() {
		
		sll.append(1234);
		String test = sll.getLast() + "";
		assertEquals("1234", test);
		
	}
	
	@Test
	public void TestAddFirst() {
		
		sll.add("Programming");
		String test = (String) sll.get();
		assertEquals("Programming", test);
		
	}
	
	@Test
	public void TestAddPosition() {
		
		sll.add("Programming",3);
		String test = (String) sll.get(3);
		assertEquals("Programming", test);
		
	}
	
	@Test
	public void TestClear() {
		
		sll.clear();
		assertEquals(true, sll.isEmpty());
		
	}
	
	@Test
	public void TestRemoveFirst() {
		
		String test = (String) sll.remove();
		assertEquals("Yee", test);
		
	}
	
	@Test
	public void TestRemoveLast() {
		
		String test = (String) sll.removeLast();
		assertEquals("Hi Khosro", test);
		
	}
	
	@Test 
	public void TestRemovePosition() {
		
		String test = (String) sll.remove(1);
		assertEquals("Haw", test);
	}
	
	@Test
	public void TestGet() {
		String test = (String) sll.get();
		assertEquals("Yee", test);
	}
	
	@Test
	public void TestGetLast() {
		String test = (String) sll.getLast();
		assertEquals("Hi Khosro", test);
	}
	
	@Test
	public void TestGetPosition() {
		String test = (String) sll.get(1);
		assertEquals("Haw", test);
	}
	
	@Test 
	public void TestContainsTrue() {
		boolean check = sll.contains("Haw");
		assertEquals(true, check);
	}
	
	@Test 
	public void TestContainsFalse() {
		boolean check = sll.contains("djkssgkjfhgkjdf");
		assertEquals(false, check);
	}
	
	@Test
	public void TestIndexOf() {
		int test = sll.indexOf("Haw");
		assertEquals(1, test);
	}
	

}
