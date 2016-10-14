package kr.co.netbro.kra.model;

public class Pool {
	public char type;
	public char status;
	public int[] num1;
	public int[] num2;
	public int[] num3;
	public String[] rate;
	public int[] rateInt;

	public String toString() {
		String s = "pool_" + this.type + "_" + this.status + "_" + this.num1.length + "_";
		for (int i = 0; i < this.num1.length; i++) {
			s = s + "[" + this.num1[i] + " " + this.num2[i] + " " + this.rate[i] + "]";
		}
		return s;
	}

	public String getValue() {
		String s = String.valueOf(this.num1.length);
		if ((this.type == '1') || (this.type == '2')) {
			for (int i = 0; i < this.num1.length; i++) {
				s = s + "|" + this.num1[i] + "|" + div10(this.rateInt[i]);
			}
		} else if ((this.type == '3') || (this.type == '4') || (this.type == '5')) {
			for (int i = 0; i < this.num1.length; i++) {
				s = s + "|" + this.num1[i] + "|" + this.num2[i] + "|" + div10(this.rateInt[i]);
			}
		} else if ((this.type == '6') || (this.type == '8')) {
			for (int i = 0; i < this.num1.length; i++) {
				s = s + "|" + this.num1[i] + "|" + this.num2[i] + "|" + this.num3[i] + "|" + div10(this.rateInt[i]);
			}
		} else {
			return "0";
		}
		return s;
	}

	private String div10(int i) {
		if (i > 0) {
			return i / 10 + "." + i % 10;
		}
		return "-";
	}

	public int getMaxInt() {
		int max = 0;
		for (int i = 0; i < this.rateInt.length; i++) {
			max = Math.max(max, this.rateInt[i]);
		}
		return max;
	}
}
