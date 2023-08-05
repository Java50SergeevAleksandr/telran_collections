package telran.test;

import java.util.Arrays;

import telran.performance.JoinStringPerformanceTest;
import telran.text.JoinStringsOnBuilder;
import telran.text.JoinStringsOnStandard;
import telran.text.JoinStringsOnString;

public class JoinStringsPerformanceAppl {
	private static final int N_STRINGS = 1000;
	private static final String[] bigArray;

	static {
		bigArray = new String[N_STRINGS];
		Arrays.fill(bigArray, "Hello");
	}

	public static void main(String[] args) {

		JoinStringsOnBuilder joinBuilder = new JoinStringsOnBuilder();
		JoinStringsOnString joinString = new JoinStringsOnString();
		JoinStringsOnStandard joinStandartString = new JoinStringsOnStandard();

		JoinStringPerformanceTest BuilderTest = new JoinStringPerformanceTest("JoinBuilderTest", 10000, bigArray,
				joinBuilder);
		JoinStringPerformanceTest StringTest = new JoinStringPerformanceTest("JoinStringTest", 1000, bigArray,
				joinString);
		JoinStringPerformanceTest StandartStringTest = new JoinStringPerformanceTest("JoinStandartStringTest", 10000,
				bigArray, joinStandartString);

		BuilderTest.run();
		StringTest.run();
		StandartStringTest.run();
	}
}
