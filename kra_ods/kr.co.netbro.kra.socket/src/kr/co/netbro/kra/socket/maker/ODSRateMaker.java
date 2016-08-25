package kr.co.netbro.kra.socket.maker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ODSRateMaker {
	public static final int HEADER_SIZE = 25;
	private static final char[] MAX_RATE = { '9', '9', '9', '9', '9' };
	private static final char[] NULL_RATE = { '-', '-', '-', '-', '-' };
	public static final String HORSE_MARKER = "##";
	private static char[] topDataLen;

	public static char[] makeData(byte[] source, int offset, int len) {
		char[] c = new String(source, offset, len).toCharArray();

		int horseNum = (c[26] - '0') * 10 + (c[27] - '0'); // '0' == (char)48
		int type = (c[19] - '0') * 10 + (c[20] - '0');

		if(type == 52) {
			System.out.println("byte len: "+len+", char len: "+c.length);
		}
		
		char[] data = makeBody(type, horseNum, c, false);

		data[0] = c[2];

		data[1] = c[19];
		data[2] = c[20];

		data[3] = c[7];
		data[4] = c[8];

		if (c[17] == '0') {
			data[5] = c[28];
			data[6] = c[29];
		} else if (c[17] == '1') {
			data[5] = (data[6] = 120);
		} else if (c[17] == '2') {
			data[5] = (data[6] = 99);
		} else if (c[17] == '3') {
			data[5] = (data[6] = 121);
		}

		if ((type == 52) || (type == 54)) {
			data[7] = topDataLen[0];
			data[8] = topDataLen[1];
		} else {
			data[7] = c[26];
			data[8] = c[27];
		}

		int moneyPtr;
		switch (type) {
		case 1: 
		case 2: 
			moneyPtr = 30 + 5 * horseNum;
			break;
		case 4: 
			moneyPtr = 30 + 5 * horseNum * (horseNum - 1);
			break;
		case 6: 
			moneyPtr = 30 + 5 * horseNum * (horseNum - 1) * (horseNum - 2) / 6;
			break;
		case 10: 
			moneyPtr = 30 + 5 * horseNum * (horseNum - 1) * (horseNum - 2);
			break;
		case 52: 
		case 54: 
			moneyPtr = 30 + 11 * horseNum;
			break;
		default: 
			moneyPtr = 30 + 5 * horseNum * (horseNum - 1) / 2;
		}

		System.arraycopy(c, moneyPtr, data, 9, 11);
		return data;
	}

	private static char[] makeBody(int type, int horseNum, char[] c, boolean isNull) {
		int maxHorseNum = isNull ? horseNum : Math.max(horseNum, 5); // min:5, max: horseNum(9)

		int dataPtr = HEADER_SIZE;
		int cPtr = 30;

		char[] data = null;
		char[] max = new char[5];
		for (int i = 0; i < 5; i++) {
			max[i] = '0';
		}

		switch (type) {
		case 1: 
		case 2: 
			data = new char[HEADER_SIZE + 5 * maxHorseNum];
			updateMinimum(data, MAX_RATE, 0);
			for (int i = 1; i <= maxHorseNum; i++) {
				if ((i > horseNum) || (isNull)) {
					System.arraycopy(NULL_RATE, 0, data, dataPtr, 5);
				} else {
					System.arraycopy(c, cPtr, data, dataPtr, 5);
					updateMinimum(data, c, cPtr);
					updateMaximum(max, c, cPtr);
					cPtr += 5;
				}
				dataPtr += 5;
			}
			break;
		case 4: 
			data = new char[HEADER_SIZE + 5 * maxHorseNum * (maxHorseNum - 1)];
			updateMinimum(data, MAX_RATE, 0);
			for (int i = 1; i <= maxHorseNum; i++) {
				for (int j = 1; j <= maxHorseNum; j++) {
					if (i != j) {
						if ((i > horseNum) || (j > horseNum) || (isNull)) {
							System.arraycopy(NULL_RATE, 0, data, dataPtr, 5);
						} else {
							System.arraycopy(c, cPtr, data, dataPtr, 5);
							updateMinimum(data, c, cPtr);
							updateMaximum(max, c, cPtr);
							cPtr += 5;
						}
						dataPtr += 5;
					}
				}
			}
			break;
		case 6: 
			data = makeSambokBody(horseNum, maxHorseNum, c, isNull, max);
			break;
		case 10: 
			data = makeSamssangBody(horseNum, maxHorseNum, c, isNull, max);
			break;
		case 52: 
		case 54: 
			data = makeTripleTopBody(horseNum, c, isNull, max);
			break;
		default:
			data = new char[HEADER_SIZE + 5 * maxHorseNum * (maxHorseNum - 1) / 2]; // 205
			updateMinimum(data, MAX_RATE, 0);
			for (int i = 1; i <= maxHorseNum; i++) {
				for (int j = i + 1; j <= maxHorseNum; j++) {
					if ((i > horseNum) || (j > horseNum) || (isNull)) {
						System.arraycopy(NULL_RATE, 0, data, dataPtr, 5);
					} else {
						System.arraycopy(c, cPtr, data, dataPtr, 5);
						updateMinimum(data, c, cPtr);
						updateMaximum(max, c, cPtr);
						cPtr += 5;
					}
					dataPtr += 5;
				}
			}
		}

		boolean allSame = true;
		for (int i = 0; i < 5; i++) {
			if (data[(20 + i)] != max[i]) {
				allSame = false;
				break;
			}
		}

		if (allSame) {
			System.arraycopy(NULL_RATE, 0, data, 20, 5);
		}

		return data;
	}

	public static char[] makeNullData(int zoneIndex, int type, int horse, int race) {
		char[] c = new char[1];
		if ((type == 52) || (type == 54)) {
			horse = 60;
		}
		char[] data = makeBody(type, horse, c, true);

		data[0] = ((char)(zoneIndex + 49)); // +1

		data[1] = ((char)(48 + type / 10));
		data[2] = ((char)(48 + type % 10));

		data[3] = ((char)(48 + race / 10));
		data[4] = ((char)(48 + race % 10));

		data[5] = (data[6] = 121);

		data[7] = ((char)(48 + horse / 10));
		data[8] = ((char)(48 + horse % 10));
		for (int i = 0; i < 11; i++) {
			data[(9 + i)] = '0';
		}
		return data;
	}

	private static void updateMinimum(char[] data, char[] c, int cPtr) {
		if ((data[20] == 0) || (data[20] == ' ') || (data[20] == '-')) {
			System.arraycopy(c, cPtr, data, 20, 5);
		} else {
			if (c[cPtr] == '-') {
				return;
			}
			for (int i = 0; i < 5; i++) {
				if (c[(cPtr + i)] < data[(20 + i)]) {
					System.arraycopy(c, cPtr, data, 20, 5);
					return;
				}
				if (c[(cPtr + i)] > data[(20 + i)]) {
					return;
				}
			}
		}
	}

	private static void updateMaximum(char[] data, char[] c, int cPtr) {
		if ((data[0] == 0) || (data[0] == ' ') || (data[0] == '-')) {
			System.arraycopy(c, cPtr, data, 0, 5);
		} else {
			if (c[cPtr] == '-') {
				return;
			}

			for (int i = 0; i < 5; i++) {
				if (c[(cPtr + i)] > data[i]) {
					System.arraycopy(c, cPtr, data, 0, 5);
					return;
				}
				if (c[(cPtr + i)] < data[i]) {
					return;
				}
			}
		}
	}

	private static char[] makeTripleTopBody(int horseNum, char[] c, boolean isNull, char[] max) {
		int cPtr = 30;

		char[] data = new char[25];

		updateMinimum(data, MAX_RATE, 0);
		if ((!isNull) && (horseNum > 0)) {
			updateMinimum(data, c, cPtr + 6);
		}
		StringBuffer buf = new StringBuffer();
		buf.append(data);
		buf.append('\r');
		buf.append('\n');

		int count = 0;
		if ((isNull) || (c[17] == '2')) {
			for (int i = 0; i < horseNum; i++) {
				buf.append("-- -- -- -----\r\n");
				count++;
			}
		} else {
			for (int i = 0; i < horseNum; i++) {
				if (c[(cPtr + 6)] == '-') {
					cPtr += 11;
				} else {
					buf.append(Util.replaceZero(c[(cPtr++)]));
					buf.append(c[(cPtr++)]);
					buf.append(' ');
					buf.append(Util.replaceZero(c[(cPtr++)]));
					buf.append(c[(cPtr++)]);
					buf.append(' ');
					buf.append(Util.replaceZero(c[(cPtr++)]));
					buf.append(c[(cPtr++)]);
					buf.append(' ');

					updateMaximum(max, c, cPtr);

					buf.append(c[(cPtr++)]);
					buf.append(c[(cPtr++)]);
					buf.append(c[(cPtr++)]);
					buf.append(c[(cPtr++)]);
					buf.append(c[(cPtr++)]);
					buf.append('\r');
					buf.append('\n');
					count++;
				}
			}
		}
		String countStr = Util.addZero(String.valueOf(count));

		topDataLen = countStr.toCharArray();

		return buf.toString().toCharArray();
	}

	private static char[] makeSambokBody(int horseNum, int maxHorseNum, char[] c, boolean isNull, char[] max) {
		int cPtr = 30;

		char[] data = new char[25];

		updateMinimum(data, MAX_RATE, 0);

		List<SambokRate[]> list = new ArrayList<SambokRate[]>();
		char[] rate = new char[5];
		for (int i = 1; i <= maxHorseNum; i++) {
			for (int j = i + 1; j <= maxHorseNum; j++) {
				int size = maxHorseNum - j;
				if (size > 0) {
					SambokRate[] array = new SambokRate[size];
					for (int k = j + 1; k <= maxHorseNum; k++) {
						if ((i > horseNum) || (j > horseNum) || (k > horseNum) || (isNull)) {
							System.arraycopy(NULL_RATE, 0, rate, 0, 5);
						} else {
							System.arraycopy(c, cPtr, rate, 0, 5);
							updateMinimum(data, c, cPtr);
							updateMaximum(max, c, cPtr);
							cPtr += 5;
						}
						array[(k - j - 1)] = new SambokRate(i, j, k, new String(rate));
					}
					list.add(array);
				}
			}
		}

		List<SambokPage> pages = new ArrayList<SambokPage>();
		SambokPage sp = new SambokPage(horseNum);
		pages.add(sp);

		Iterator<SambokRate[]> it = list.iterator();
		int row = 0;
		int col = 0;
		while (it.hasNext()) {
			SambokRate[] rates = (SambokRate[])it.next();

			if(row > SambokPage.ROW_SIZE - 1 || 
					row == SambokPage.ROW_SIZE - 1 && (horseNum != 14 || rates[0].i != 5) || 
					horseNum == 9 && rates[0].i == 5 && rates[0].j == 7 || 
					horseNum == 10 && rates[0].i == 6 && rates[0].j == 8 || 
					horseNum == 5 && rates[0].i == 1 && rates[0].j == 4 || 
					horseNum == 5 && rates[0].i == 3 && rates[0].j == 4 || 
					horseNum == 6 && rates[0].i == 1 && rates[0].j == 4 || 
					horseNum == 6 && rates[0].i == 2 && rates[0].j == 4 || 
					horseNum == 6 && rates[0].i == 3 && rates[0].j == 5 || 
					horseNum == 7 && rates[0].i == 1 && rates[0].j == 4 || 
					horseNum == 7 && rates[0].i == 2 && rates[0].j == 6 || 
					horseNum == 7 && rates[0].i == 4 && rates[0].j == 5) {
				row = 0;
				col += 2;
			}

			if (((col == SambokPage.COLUMN_SIZE - 2) && (row + rates.length + 1 > SambokPage.ROW_SIZE)) || (col >= SambokPage.COLUMN_SIZE)) {
				sp = new SambokPage(horseNum);
				pages.add(sp);
				row = 0;
				col = 0;
			}

			sp.data[row][col] = HORSE_MARKER;
			sp.data[(row++)][(col + 1)] = getRateMarker(rates[0]);
			for (int i = 0; i < rates.length; i++) {
				if (horseNum == 7) {
					if (row >= 11) {
						row = 0;
						col += 2;
					}
				} else if (row >= SambokPage.ROW_SIZE) {
					row = 0;
					col += 2;
				}

				int k = rates[i].k;
				sp.data[row][col] = (k <= 9 ? " " + k : String.valueOf(k));
				sp.data[(row++)][(col + 1)] = rates[i];
			}
		}

		if (true) {
			shiftLastPage(horseNum, pages);
		}
		StringBuffer buf = new StringBuffer();
		buf.append(data);
		buf.append('\r');
		buf.append('\n');

		Iterator<SambokPage> it2 = pages.iterator();
		while (it2.hasNext()) {
			sp = (SambokPage)it2.next();
			buf.append(sp.toString());
			buf.append('\r');
			buf.append('\n');
		}

		return buf.toString().toCharArray();
	}

	private static char[] makeSamssangBody(int horseNum, int maxHorseNum, char[] c, boolean isNull, char[] max) {
		int cPtr = 30;

		char[] data = new char[25];

		updateMinimum(data, MAX_RATE, 0);

		List<SambokRate[]> list = new ArrayList<SambokRate[]>();
		char[] rate = new char[5];
		for (int i = 0; i < maxHorseNum; i++) {
			for (int j = 0; j < maxHorseNum; j++) {
				SambokRate[] rates = new SambokRate[maxHorseNum - 2];
				int rateIndex = 0;
				if (i != j) {
					for (int k = 0; k < maxHorseNum; k++) {
						if ((i != k) && (j != k)) {
							if ((isNull) || (c.length < cPtr + 5)) {
								System.arraycopy(NULL_RATE, 0, rate, 0, 5);
							} else {
								System.arraycopy(c, cPtr, rate, 0, 5);
								updateMinimum(data, c, cPtr);
								updateMaximum(max, c, cPtr);
								cPtr += 5;
							}
							rates[rateIndex] = new SambokRate(i + 1, j + 1, k + 1, new String(rate));

							rateIndex++;
						}
					}
					list.add(rates);
				}
			}
		}

		List<SamssangPage> pages = new ArrayList<SamssangPage>();
		SamssangPage sp = new SamssangPage(horseNum);
		pages.add(sp);

		Iterator<SambokRate[]> it = list.iterator();
		int row = 0;
		int col = 0;
		while (it.hasNext()) {
			SambokRate[] rates = (SambokRate[])it.next();
			if (row >= SamssangPage.ROW_SIZE) {
				row = 0;
				col += 2;
			}
			if (((col == SamssangPage.COLUMN_SIZE - 2) && (row + rates.length + 1 > SamssangPage.ROW_SIZE)) || (col >= SamssangPage.COLUMN_SIZE)) {
				sp = new SamssangPage(horseNum);
				pages.add(sp);
				row = 0;
				col = 0;
			}
			sp.data[row][col] = HORSE_MARKER;
			sp.data[(row++)][(col + 1)] = getRateMarker(rates[0]);

			for (int i = 0; i < rates.length; i++) {
				if (row >= SamssangPage.ROW_SIZE) {
					row = 0;
					col += 2;
				}
				int k = rates[i].k;
				sp.data[row][col] = (k <= 9 ? " " + k : String.valueOf(k));
				sp.data[(row++)][(col + 1)] = rates[i];
			}
		}

		StringBuffer buf = new StringBuffer();
		buf.append(data);
		buf.append('\r');
		buf.append('\n');

		Iterator<SamssangPage> it2 = pages.iterator();
		while (it2.hasNext()) {
			sp = (SamssangPage)it2.next();
			buf.append(sp.toString());
			buf.append('\r');
			buf.append('\n');
		}

		return buf.toString().toCharArray();
	}

	private static void shiftLastPage(int horse, List<SambokPage> pages) {
		int shiftColumn = 0;
		switch (horse) {
		case 10: 
			shiftColumn = 2;
			break;
		case 11: 
			shiftColumn = 6;
			break;
		case 9: 
		case 12: 
			shiftColumn = 8;
			break;
		case 13: 
			shiftColumn = 10;
			break;
		case 15: 
			shiftColumn = 4;
			break;
		case 14: 
		default: 
			return;
		}

		int pageSize = pages.size();
		if (pageSize < 2) {
			return;
		}

		SambokPage lastPage = (SambokPage)pages.get(pageSize - 1);
		SambokPage previousPage = (SambokPage)pages.get(pageSize - 2);

		for (int r = 0; r < SambokPage.ROW_SIZE; r++) {
			for (int c = SambokPage.COLUMN_SIZE - shiftColumn - 1; c >= 0; c--) {
				lastPage.data[r][(c + shiftColumn)] = lastPage.data[r][c];
			}
			for (int c = 0; c < shiftColumn; c++) {
				lastPage.data[r][c] = previousPage.data[r][(SambokPage.COLUMN_SIZE - shiftColumn + c)];
			}
		}

		if (lastPage.data[0][0] != HORSE_MARKER) {
			if (lastPage.data[1][0] == HORSE_MARKER) {
				for (int r = 0; r < SambokPage.ROW_SIZE; r++) {
					for (int c = 0; c < shiftColumn; c++) {
						if (r == SambokPage.ROW_SIZE - 1) {
							if ((c == shiftColumn - 1) || (c == shiftColumn - 2)) {
								lastPage.data[r][c] = (c % 2 == 0 ? "  " : "     ");
							} else {
								lastPage.data[r][c] = previousPage.data[0][(SambokPage.COLUMN_SIZE - shiftColumn + c + 2)];
							}
						} else {
							lastPage.data[r][c] = previousPage.data[(r + 1)][(SambokPage.COLUMN_SIZE - shiftColumn + c)];
						}
					}
				}
			} else {
				lastPage.data[0][0] = HORSE_MARKER;
				lastPage.data[0][1] = getRateMarker((SambokRate)lastPage.data[0][1]);
			}
		}
	}

	private static String getRateMarker(SambokRate r) {
		int i = r.i;
		int j = r.j;
		StringBuffer sb = new StringBuffer(7);
		sb.append(i);
		sb.append('-');
		sb.append(j);

		int spaceCount = 0;
		if (i < 10) {
			spaceCount++;
		}
		if (j < 10) {
			spaceCount++;
		}
		for (int x = 0; x < spaceCount; x++) {
			sb.append(' ');
		}
		return sb.toString();
	}
}
