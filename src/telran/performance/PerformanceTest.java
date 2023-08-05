package telran.performance;

public abstract class PerformanceTest {
	private String testName;
	private int nRuns;

	PerformanceTest(String testName, int nRuns) {
		this.testName = testName;
		this.nRuns = nRuns;
	}

	protected abstract void runTest();

	public void run() {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < nRuns; i++) {
			runTest();
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Test name: " + testName);
		System.out.printf("Value of runs: %d%n", nRuns);
		System.out.printf("Running time: %d%n",  stopTime - startTime);
		System.out.printf("=============%n%n");
	}
}
