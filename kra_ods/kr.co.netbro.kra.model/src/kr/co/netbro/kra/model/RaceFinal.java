package kr.co.netbro.kra.model;

public class RaceFinal {
	public String date;
	public char zone;
	public int race;
	public boolean isForce;
	public boolean isFinal;
	public char[] poolMap = new char[10];
	public int[][] order = new int[3][];
	public Pool[] pool = new Pool[10];
	public String canID;
	public int[] can;

	public String toString()
	{
		String s = this.date + " zone=" + this.zone + " race=" + this.race + " force=" + this.isForce + " final=" + this.isFinal + " " + new String(this.poolMap);
		for (int oi = 0; oi < this.order.length; oi++)
		{
			s = s + " [";
			for (int i = 0; i < this.order[oi].length; i++) {
				s = s + this.order[oi][i] + " ";
			}
			s = s + "]";
		}
		for (int i = 0; i < this.pool.length; i++) {
			if (this.pool[i] != null) {
				s = s + " " + this.pool[i].toString();
			}
		}
		s = s + ", can=";
		for (int i = 0; i < this.can.length; i++) {
			s = s + this.can[i] + ",";
		}
		return s;
	}

	public int getValidPoolSize()
	{
		int count = 0;
		for (int i = 0; i < this.poolMap.length; i++) {
			if ((this.poolMap[i] != '0') && (this.poolMap[i] != 0)) {
				count++;
			}
		}
		return count;
	}

	public Pool getPool(int type)
	{
		if ((type >= 0) && (type < this.pool.length)) {
			return this.pool[type];
		}
		return null;
	}

	public int getMaxInt()
	{
		int max = 0;
		for (int i = 0; i < this.pool.length; i++) {
			if (this.pool[i] != null) {
				max = Math.max(max, this.pool[i].getMaxInt());
			}
		}
		return max;
	}
}
