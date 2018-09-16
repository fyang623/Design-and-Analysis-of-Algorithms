import java.math.BigInteger;

public class Karatsuba {
	public static void main(String[] args) {
		String s1 = "3141592653589793238462643383279502884197169399375105820974944592";
		String s2 = "2718281828459045235360287471352662497757247093699959574966967627";
		BigInteger result = multiply(s1, s2);
		System.out.println(result);
	}

	private static BigInteger multiply(String s1, String s2) {
		
		if(s1.length()==1 || s2.length()==1) {
			int m = (s1.charAt(0) - 48) * (s2.charAt(0) - 48);
			return new BigInteger(Integer.toString(m));
		}
		if(s1.length() != s2.length())
			System.out.println("error!");
		char[] tail1 = new char[s1.length()];
		char[] tail2 = new char[s1.length()/2];
		for(int i=0; i < tail1.length; i++)	tail1[i] = '0';
		for(int i=0; i < tail2.length; i++)	tail2[i] = '0';
		String a = s1.substring(0, s1.length()/2);
		String b = s1.substring(s1.length()/2, s1.length());
		String c = s2.substring(0, s2.length()/2);
		String d = s2.substring(s2.length()/2, s2.length());
		BigInteger ac = multiply(a, c);
		BigInteger bd = multiply(b, d);
		BigInteger ad = multiply(a, d);
		BigInteger bc = multiply(b, c);
		BigInteger ad_Plus_bc = ad.add(bc);
		ac = new BigInteger(ac.toString().concat(String.valueOf(tail1)));
		ad_Plus_bc = new BigInteger(ad_Plus_bc.toString().concat(String.valueOf(tail2)));
		return ac.add(ad_Plus_bc).add(bd);
	}
}
