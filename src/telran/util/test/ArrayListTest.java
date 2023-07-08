package telran.util.test;

import org.junit.jupiter.api.BeforeEach;

import telran.util.ArrayList;
import telran.util.Collection;

public class ArrayListTest extends ListTest {

	@Override
	protected Collection<Integer> getCollection(Integer[] ar1) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		for (Integer num : ar1) {
			arrayList.add(num);
		}
		return arrayList;
	}

	@BeforeEach
	@Override
	void setUp() {
		collection = new ArrayList<>(3);
		super.setUp();
	}

}