package telran.strings.test;

import static org.junit.jupiter.api.Assertions.*;
import static telran.strings.Strings.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class StringsTest {

	@SuppressWarnings("unused")
	@Test
	void javaVariableTrueTest() {
		String regex = javaVariable();
		assertTrue("a".matches(regex));
		assertTrue("$".matches(regex));
		int $;
		assertTrue("a123".matches(regex));
		assertTrue("__".matches(regex));
		int __;
		assertTrue("_$".matches(regex));
		int _$;
		assertTrue("$_".matches(regex));
		int $_;
		assertTrue("aA".matches(regex));
		assertTrue("Aa".matches(regex));

	}

	@Test
	void javaVariableFalseTest() {
		String regex = javaVariable();
		assertFalse("1a".matches(regex));
		assertFalse("123".matches(regex));
		assertFalse("a&c".matches(regex));
		assertFalse("a C".matches(regex));
		assertFalse("_".matches(regex));
	}

	@Test
	void zero_300_true_test() {
		String regex = zero_300();
		assertTrue("0".matches(regex));
		assertTrue("10".matches(regex));
		assertTrue("9".matches(regex));
		assertTrue("100".matches(regex));
		assertTrue("199".matches(regex));
		assertTrue("200".matches(regex));
		assertTrue("299".matches(regex));
		assertTrue("300".matches(regex));
	}

	@Test
	void zero_300_false_test() {
		String regex = zero_300();
		assertFalse("01".matches(regex));
		assertFalse("1 0".matches(regex));
		assertFalse("1_0".matches(regex));
		assertFalse("301".matches(regex));
		assertFalse("1000".matches(regex));
		assertFalse("2.5".matches(regex));
		assertFalse("a".matches(regex));
		assertFalse("-1_0".matches(regex));
	}

	@Test
	void ipV4OctetTrueTest() {
		String regex = ipV4Octet();
		assertTrue("0".matches(regex));
		assertTrue("00".matches(regex));
		assertTrue("000".matches(regex));
		assertTrue("01".matches(regex));
		assertTrue("1".matches(regex));
		assertTrue("199".matches(regex));
		assertTrue("220".matches(regex));
		assertTrue("249".matches(regex));
		assertTrue("250".matches(regex));
		assertTrue("255".matches(regex));
	}

	@Test
	void ipV4OctetFalseTest() {
		String regex = ipV4Octet();
		assertFalse("-21".matches(regex));
		assertFalse("a".matches(regex));
		assertFalse("1 0".matches(regex));
		assertFalse("0000".matches(regex));
		assertFalse("256".matches(regex));
		assertFalse("1000".matches(regex));
		assertFalse("300".matches(regex));
	}

	@Test
	void ipV4TrueTest() {
		String regex = ipV4Address();
		assertTrue("1.2.3.4".matches(regex));
		assertTrue("0.0.0.0".matches(regex));
		assertTrue("000.0.0.0".matches(regex));
		assertTrue("255.255.255.255".matches(regex));
	}

	@Test
	void ipV4TFalseTest() {
		String regex = ipV4Address();
		assertFalse("1.2.3.".matches(regex));
		assertFalse("1.2.3&4".matches(regex));
		assertFalse("12.3.".matches(regex));
		assertFalse("100".matches(regex));
		assertFalse("1 2.3.4".matches(regex));
	}

	@Test
	void arithmeticExpressionTrueTest() {
		String regex = arithmeticExpression();
		assertTrue("1 + a".matches(regex));
		assertTrue("1.5 + a/2*10 - 21".matches(regex));
		assertTrue(" .5 + $/2* 10.0 /21.1234".matches(regex));
		assertTrue("5. + __/2* 0.0 /0 ".matches(regex));
		assertTrue(" aA123 ".matches(regex));
		assertTrue("25.".matches(regex));
	}

	@Test
	void arithmeticExpressionFalseTest() {
		String regex = arithmeticExpression();
		assertFalse("1.5 # a/2*10 - 21".matches(regex));
		assertFalse(".5 + $ 1/2* 10.0 /21.1234".matches(regex));
		assertFalse("5. + _/2* 0.0 /0".matches(regex));
		assertFalse("25 .".matches(regex));
		assertFalse("aA123*".matches(regex));
		assertFalse(" + a * b".matches(regex));
	}

	@Test
	void calcultaionTest() {
		Map<String, Double> variableValues = new HashMap<>();
		variableValues.put("a", 10.);
		variableValues.put("b", 1.);
		variableValues.put("c", 2.);
		assertEquals(10, calculation("a", variableValues));
		assertThrowsExactly(IllegalArgumentException.class, () -> calculation(" + a * b", variableValues));
		assertThrowsExactly(IllegalArgumentException.class, () -> calculation("var", variableValues));
		assertEquals(12, calculation("12", variableValues));
		assertEquals(2, calculation("1 + b", variableValues));
		assertEquals(-11, calculation("1.5 + c/3.5*10 - 21", variableValues));
		assertEquals(Double.POSITIVE_INFINITY, calculation("20 / 0 + a", variableValues));
		assertEquals(Double.NEGATIVE_INFINITY, calculation("b - a / 0 + 7", variableValues));
	}
}
