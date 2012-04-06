
public class CvString {
	String[] tach(String s, int k) {
		String[] s1 = new String[k];
		int i = 0;
		String s2 = "";
		for(int j = 0; j < s.length(); j++) {
			if(s.charAt(j) != ' ' && s.charAt(j) != '	') {
				s2 = s2 + s.charAt(j);
			} else {
				if(j != s.length() -1) {
					s1[i++] = s2;
					s2 = "";
				}
			}
		}
		s1[i] = s2;
		return s1;
	}
}
