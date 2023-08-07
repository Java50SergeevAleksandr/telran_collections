package telran.addTask;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class QuickDictionary {
	private static final int LENGHT = 20;
	private static final int CHAR_LENGHT = 'Z' - 'A' + 1;

	HashMap<String, String>[][] lenghtTable = (HashMap<String, String>[][]) new HashMap[LENGHT][CHAR_LENGHT];

	public QuickDictionary() {
		for (int i = 0; i < lenghtTable.length; i++) {
			lenghtTable[i] = new HashMap[CHAR_LENGHT];
			for (int j = 0; j < CHAR_LENGHT; j++) {
				lenghtTable[i][j] = new HashMap<String, String>();
			}
		}
	}

	String put(String key, String value) {
		if (!key.equals(key.toUpperCase())) {
			throw new IllegalArgumentException();
		}

		String res = null;
		for (int i = 0; i < key.length(); i++) {
			var test = lenghtTable[i][(int) (key.charAt(i) - 'A')];
			res = test.put(key, value);

		}
		return res;
	}

	String get(String key) {
		if (!key.equals(key.toUpperCase())) {
			throw new IllegalArgumentException();
		}
		HashMap<String, String> target = lenghtTable[0][key.charAt(0) - 'A'];
		String res = null;
		if (target != null) {
			var targetKeys = target.keySet();

			for (int i = 1; i < key.length(); i++) {
				targetKeys.retainAll(lenghtTable[i][key.charAt(i) - 'A'].keySet());
			}
			var targetArr = targetKeys.toArray();
			res = targetArr.length == 0 ? null : lenghtTable[0][key.charAt(0) - 'A'].get(targetArr[0]);
		}

		return res;
	}

}
