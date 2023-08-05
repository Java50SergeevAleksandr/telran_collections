package telran.test;

import java.util.Arrays;

import telran.performance.JoinStringPerformanceTest;
import telran.text.JoinStringsOnBuilder;

public class JoinStringsPerformanceAppl {
	private static final int N_STRINGS = 1000;

	public static void main() {
		String[] bigArray = getBigArray();
		JoinStringsOnBuilder joinBuilder = new JoinStringsOnBuilder();
		JoinStringPerformanceTest BuilderTest = new JoinStringPerformanceTest("JoinStringBuilderTest", 1000, bigArray,
				joinBuilder);
		BuilderTest.run();
	}

	private static String[] getBigArray() {
		String[] res = new String[N_STRINGS];
		Arrays.fill(res, "Hello");
		return res;
	}
}
