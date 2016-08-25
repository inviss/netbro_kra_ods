package kr.co.netbro.kra.socket.maker;

public class SambokPage {
	public static final int DEFUALT_ROW = 15;
	public static final int DEFUALT_COLUMN = 12;
	public static int ROW_SIZE;
	public static int COLUMN_SIZE;
	int horseNum;
	public Object[][] data;

	public SambokPage(int horseNum) {
		this.horseNum = horseNum;

		switch (horseNum) {
		case 15: 
			ROW_SIZE = 17;
			COLUMN_SIZE = 14;
			break;
		case 16: 
			ROW_SIZE = 17;
			COLUMN_SIZE = 16;
			break;
		default: 
			ROW_SIZE = 15;
			COLUMN_SIZE = 12;
		}
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
