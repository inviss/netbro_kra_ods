package kr.co.netbro.kra.socket.maker;

public class SamssangPage {
	public static final int DEFUALT_ROW = 17;
	public static final int DEFUALT_COLUMN = 16;
	public static int ROW_SIZE;
	public static int COLUMN_SIZE;
	int horseNum;
	public Object[][] data;

	public SamssangPage(int horseNum) {
		this.horseNum = horseNum;
		ROW_SIZE = 17;
		COLUMN_SIZE = 16;
		this.data = new Object[ROW_SIZE][COLUMN_SIZE];
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int r = 0; r < ROW_SIZE; r++) {
			for (int c = 0; c < COLUMN_SIZE; c++) {
				if (this.data[r][c] == null) {
					sb.append(c % 2 == 0 ? "  " : "     ");
				} else if ((this.data[r][c] instanceof SambokRate)) {
					sb.append(((SambokRate)this.data[r][c]).rate);
				} else {
					sb.append(this.data[r][c].toString());
				}
				sb.append(' ');
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}
}
