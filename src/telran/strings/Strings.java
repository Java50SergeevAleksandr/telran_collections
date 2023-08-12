package telran.strings;

import java.util.*;
import java.util.function.*;

public class Strings {
	public static final String WRONG_EXPRESSION = "Wrong arithmetic expresion syntax";
	public static final String VARIABLE_NOT_DEFINED = "Variable is not defined";
	static HashMap<String, BinaryOperator<Double>> mapOperations;
	static {
		mapOperations = new HashMap<>();
		mapOperations.put("+", (a, b) -> a + b);
		mapOperations.put("-", (a, b) -> a - b);
		mapOperations.put("*", (a, b) -> a * b);
		mapOperations.put("/", (a, b) -> a / b);
	}

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

	public static String arithmeticExpression() {
		String operandRE = operand();
		String operatorRE = operator();
		return String.format("%1$s(%2$s%1$s)*", operandRE, operatorRE);
	}

	public static String operator() {
		return "([-+*/])";
	}

	private static String operand() {
		String numberExp = numberExp();
		String variableExp = javaVariable();
		return String.format("(\\s*\\(*\\s*)*((%s|%s))(\\s*\\)*\\s*)*", numberExp, variableExp);
	}

	private static String numberExp() {

		return "(\\d+\\.?\\d*|\\.\\d+)";
	}

	public static boolean isArithmeticExpression(String expression) {
		boolean res = false;
		if (bracketPairsValidation(expression)) {
			res = expression.matches(arithmeticExpression());
		}
		return res;
	}

	private static boolean bracketPairsValidation(String expression) {
		boolean res = true;
		int count = 0;
		char[] chars = expression.toCharArray();
		int index = 0;
		while (index < chars.length && res) {
			if (chars[index] == '(') {
				count++;
			} else if (chars[index] == ')') {
				count--;
				if (count < 0) {
					res = false;
				}
			}
			index++;
		}
		if (res) {
			res = count == 0;
		}
		return res;
	}

	public static double calculation(String expression, Map<String, Double> variableValues) {
		if (!isArithmeticExpression(expression)) {
			throw new IllegalArgumentException(WRONG_EXPRESSION);
		}
		expression = expression.replaceAll("[()\\s]+", ""); // removing brackets and spaces
		String[] operators = expression.split(operand());
		String[] operands = expression.split(operator());
		double res = getValue(operands[0], variableValues);
		for (int i = 1; i < operands.length; i++) {
			double operand = getValue(operands[i], variableValues);
			res = mapOperations.get(operators[i]).apply(res, operand);
		}

		return res;
	}

	private static double getValue(String operand, Map<String, Double> variableValues) {

		double res = 0; // if operand is number then res will be Double.parseDouble(operand) otherwise
		// the value should be got from the map
		// if the operand is a variable and a value doesn't exist in the map the
		// IllegalArgumentException should be thrown

		if (operand.matches(numberExp())) {
			res = Double.parseDouble(operand);
		} else {
			ToDoubleFunction<String> fo = s -> {
				throw new IllegalArgumentException(s);
			};
			res = variableValues.get(operand) == null ? 
					fo.applyAsDouble(VARIABLE_NOT_DEFINED)
					: variableValues.get(operand);
		}
		return res;
	}
}
