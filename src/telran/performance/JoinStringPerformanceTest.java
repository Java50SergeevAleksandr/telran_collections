package telran.performance;

import telran.text.JoinStrings;

public class JoinStringPerformanceTest extends PerformanceTest {
	private String[] strings;
	private JoinStrings joinStrings;

	public JoinStringPerformanceTest(String testName, int nRuns, String[] strings, JoinStrings joinStrins) {
		super(testName, nRuns);
		this.strings = strings;
		this.joinStrings = joinStrins;
	}

	@Override
	protected void runTest() {
		joinStrings.join(strings, " ");
	}

}
