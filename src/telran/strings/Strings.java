package telran.strings;

public class Strings {
	static public String javaVariable() {

		return "[a-zA-Z$][\\w$]*|_[\\w$]+";
	}

	static public String zero_300() {
		return "[1-9]\\d?|[1-2]\\d\\d|300|0";
	}

	static public String ipV4Octet() {
		// 0 - 255 with possible leading zeros
		return "(\\d\\d?|[0-1]\\d\\d|2([0-4]\\d|5[0-5]))";
	}

	static public String ipV4Address() {
		String octet = ipV4Octet();
		return String.format("%1$s(\\.%1$s){3}", octet);
	}

	static public String arithmeticExpression() {
		String operator = operator();
		String operand = operand();
		return String.format("%1$s(%2$s%1$s)*", operand, operator);
	}

	private static String operand() {
		String var = javaVariable();
		return String.format("\\p{Blank}?(%1$s|\\.\\d+|\\d+\\.?|\\d+\\.\\d+)\\p{Blank}?", var);
	}

	private static String operator() {
		return "\\p{Blank}?[*+/-]\\p{Blank}?";
	}
}
