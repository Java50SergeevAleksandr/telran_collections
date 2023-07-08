package telran.util;

/**
 * This class represents Lottery 7 of 49 The purpose of this task is to
 * implement rateCombination() with complexity O[N], where N=7
 */
public class Lottery {
	int[] winComb;

	public Lottery(int[] winningCombination) {
		winComb = winningCombination;
	}

	public int rateCombination(int playerCombination[]) {
		int rate = 0;
		boolean[] values = new boolean[Byte.MAX_VALUE];

		for (int i = 0; i < winComb.length; i++) {

			if (values[winComb[i]] == true) {
				rate++;
			}
			if (values[playerCombination[i]] == true) {
				rate++;
			}
			values[winComb[i]] = true;
			values[playerCombination[i]] = true;
		}
		return rate;
	}

	/**
	 * Simple sanity test
	 */
	public static void main(String[] args) {
		Lottery loto = new Lottery(new int[] { 5, 2, 17, 48, 43, 7, 9 });
		if (loto.rateCombination(new int[] { 3, 5, 12, 17, 44, 10, 7 }) != 3) {
			System.out.println("Test 1 failed");
			return;
		}
		if (loto.rateCombination(new int[] { 3, 5, 12, 17, 49, 9, 7 }) != 4) {
			System.out.println("Test 2 failed");
			return;
		}
		System.out.println("Success");
	}
}
