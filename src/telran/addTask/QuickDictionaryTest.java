package telran.addTask;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickDictionaryTest {
	QuickDictionary qd = new QuickDictionary();

	@Test
	void put_EmptyArray_newWord_Word() {
		String word = qd.put("WINDOW", "Okno");
		assertNull(word);
	}

	@Test
	void put_IncorrectKey_IllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> qd.put("window", "Okno"));
	}

	@Test
	void put_ExistingKey_ReturnPrevious() {
		qd.put("WINDOW", "Okno");
		assertEquals(qd.put("WINDOW", "Okno2"), "Okno");
	}

	@Test
	void put_MoreEntries_NotFails() {
		qd.put("WINDOW", "Okno");
		qd.put("DOOR", "Dver");
		qd.put("WALL", "Stena");
		qd.put("TABLE", "Stol");
		qd.put("CHAIR", "Stool");
		qd.put("IOUY", "kjhf");
		qd.put("IOFA", "sadf");
		qd.put("ODSHAF", "ifwge");
		qd.put("FGLKASDBFLADF", "sdflksdf");
		qd.put("IUSHFLKASF", "sfdf");
		qd.put("GOFIEWWDF", "sdgarga");
		qd.put("IUFGHLISDKB", "fdue");
		qd.put("GYIFVEILWFVBKW", "jewfkllbawe");
		assertTrue(true);
	}

	@Test
	void get_NotExistingKey_ReturnNull() {
		assertNull(qd.get("WINDOW"));
	}

	@Test
	void get_IllegalKey_Illegal() {
		assertThrows(IllegalArgumentException.class, () -> qd.get("window"));
	}

	@Test
	void get_ExistingKey_Value() {
		qd.put("WINDOW", "Okno");
		assertEquals("Okno", qd.get("WINDOW"));
	}

	@Test
	void get_MoreEntries_ValuesBack() {
		qd.put("WINDOW", "Okno");
		qd.put("DOOR", "Dver");
		qd.put("WALL", "Stena");
		qd.put("TABLE", "Stol");
		qd.put("CHAIR", "Stool");
		qd.put("IOUY", "kjhf");
		qd.put("IOFA", "sadf");
		qd.put("ODSHAF", "ifwge");
		qd.put("FGLKASDBFLADF", "sdflksdf");
		qd.put("IUSHFLKASF", "sfdf");
		qd.put("GOFIEWWDF", "sdgarga");
		qd.put("IUFGHLISDKB", "fdue");
		qd.put("GYIFVEILWFVBKW", "jewfkllbawe");
		assertEquals("jewfkllbawe", qd.get("GYIFVEILWFVBKW"));
	}
}