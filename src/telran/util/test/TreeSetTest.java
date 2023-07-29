package telran.util.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Collection;
import telran.util.TreeSet;

class TreeSetTest extends SetTest {
	TreeSet<Integer> treeSet;

	@Override
	@BeforeEach
	void setUp() {
		treeSet = new TreeSet<Integer>();
		collection = treeSet;
		super.setUp();

	}

	@Override
	protected Collection<Integer> getCollection(Integer[] ar) {
		TreeSet<Integer> res = new TreeSet<>();
		for (Integer num : ar) {
			res.add(num);
		}
		return res;
	}

	@Override
	protected void runArrayTest(Integer[] expected, Integer[] actual) {
		Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
		Integer[] actualSorted = Arrays.copyOf(actual, actual.length);
		Arrays.sort(expectedSorted);
		assertArrayEquals(expectedSorted, actualSorted);

	}

	@Test
	protected void getNodeTest() {
		assertTrue(collection.contains(numbers[0]));
		assertTrue(collection.contains(10));
		assertFalse(collection.contains(500));
		collection.clear();
		assertFalse(collection.contains(10));
	}

	@Test
	protected void getFirstTest() {
		assertEquals(Integer.valueOf(-20), treeSet.first());
		collection.clear();
		assertNull(treeSet.first());
	}

	@Test
	protected void getLastTest() {
		assertEquals(Integer.valueOf(100), treeSet.last());
		collection.clear();
		assertNull(treeSet.last());
	}

	@Test
	void ceilingTest() {
		assertEquals(Integer.valueOf(-20), treeSet.ceiling(-30));
		assertEquals(Integer.valueOf(-20), treeSet.ceiling(-20));
		assertEquals(Integer.valueOf(100), treeSet.ceiling(80));
		assertEquals(Integer.valueOf(100), treeSet.ceiling(100));
		assertNull(treeSet.ceiling(200));
		collection.clear();
		assertNull(treeSet.ceiling(1));
	}

	@Test
	void floorTest() {
		assertEquals(Integer.valueOf(100), treeSet.floor(200));
		assertEquals(Integer.valueOf(100), treeSet.floor(100));
		assertEquals(Integer.valueOf(30), treeSet.floor(80));
		assertEquals(Integer.valueOf(-20), treeSet.floor(-20));
		assertNull(treeSet.floor(-30));
		collection.clear();
		assertNull(treeSet.ceiling(1));
	}
	@Test
	void headSetCopyTest() {		
		
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14 }, 			treeSet.headSetCopy(14, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12 },				treeSet.headSetCopy(14, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12 },    			treeSet.headSetCopy(13, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12 },     			treeSet.headSetCopy(13, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.headSetCopy(100, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30 },      treeSet.headSetCopy(100, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.headSetCopy(200, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.headSetCopy(200, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20 }, 						treeSet.headSetCopy(-20, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[0] , 								treeSet.headSetCopy(-20, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[0] ,             (new TreeSet<Integer>()).headSetCopy(14, true).toArray(new Integer[0]));
	}
	
	@Test
	void tailSetCopyTest() {
		
		assertArrayEquals(new Integer[]	               {14, 30 , 100 }, treeSet.tailSetCopy(14, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]	                   {30 , 100 }, treeSet.tailSetCopy(14, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]                    {30 , 100 }, treeSet.tailSetCopy(20, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]                    {30 , 100 }, treeSet.tailSetCopy(14, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.tailSetCopy(-20, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]      {8, 10, 12, 14, 30, 100 }, treeSet.tailSetCopy(-20, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.tailSetCopy(-200, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.tailSetCopy(-200, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]                         {100 }, treeSet.tailSetCopy(100, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[0] , 								treeSet.tailSetCopy(100, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[0] ,             (new TreeSet<Integer>()).tailSetCopy(14, true).toArray(new Integer[0]));
	}

	@Test
	void subSetCopyTest() {
		
		assertThrows(IllegalArgumentException.class, ()->treeSet.subSetCopy(101, true, 100, true));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30, 100 }, treeSet.subSetCopy(-20, true, 100, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]     { 8, 10, 12, 14, 30, 100 }, treeSet.subSetCopy(-20, false, 100, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20, 8, 10, 12, 14, 30 },      treeSet.subSetCopy(-20, true, 100, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[]     { 8, 10, 12, 14, 30 },      treeSet.subSetCopy(-20, false, 100, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] {-20 },							treeSet.subSetCopy(-20, true, -20, true).toArray(new Integer[0]));
		assertArrayEquals(new Integer[0] , 								treeSet.subSetCopy(-20, true, -20, false).toArray(new Integer[0]));
		assertArrayEquals(new Integer[0] , 								treeSet.subSetCopy(-20, false, -20, true).toArray(new Integer[0]));		
	}
}
