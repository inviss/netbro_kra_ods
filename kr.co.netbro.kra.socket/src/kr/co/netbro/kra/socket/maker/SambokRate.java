package kr.co.netbro.kra.socket.maker;

public class SambokRate {
	
	public int i;
	public int j;
	public int k;
	String rate;

	public SambokRate(int i, int j, int k, String rate) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.rate = rate;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(Util.addSpace(String.valueOf(this.i)) + " ").append(Util.addSpace(String.valueOf(this.j)) + " ").append(Util.addSpace(String.valueOf(this.k)) + " ").append(this.rate).append('\r').append('\n');

		return sb.toString();
	}
}
