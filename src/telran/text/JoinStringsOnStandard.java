package telran.text;

public class JoinStringsOnStandard implements JoinStrings {

	@Override
	public String join(String[] strings, String delimiter) {
		String res = "";

		if (strings != null && strings.length > 0) {
			res = String.join(delimiter, strings);
		}
		return res;
	}

}
