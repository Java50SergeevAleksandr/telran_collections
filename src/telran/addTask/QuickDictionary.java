package telran.addTask;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class QuickDictionary {
	private static final int LENGHT = 20;
	private static final int CHAR_LENGHT = 'Z' - 'A' + 1;

	HashMap<String, String>[][] lenghtTable = (HashMap<String, String>[][]) new HashMap[LENGHT][];

	public QuickDictionary() {
		for (int i = 0; i < lenghtTable.length; i++) {
			lenghtTable[i] = new HashMap[CHAR_LENGHT];
		}
	}

	String put(String key, String value) {
		key = key.toUpperCase();
		String res = null;
		for (int i = 0; i < key.length(); i++) {
			res = lenghtTable[i][key.charAt(i) - 'A'].put(key, value);
		}
		return res;
	}

	String get(String key) {
		key = key.toUpperCase();
		HashMap<String, String> target = lenghtTable[0][key.charAt(0) - 'A'];
		var targetKeys = target.keySet();

		for (int i = 1; i < key.length(); i++) {
			targetKeys.retainAll(lenghtTable[i][key.charAt(i) - 'A'].keySet());
		}
		var targetArr = targetKeys.toArray();
		return targetArr.length == 0 ? null : lenghtTable[0][key.charAt(0) - 'A'].get(targetArr[0]);
	}

}
