package test.json;

import kr.co.netbro.common.utils.GSON;
import kr.co.netbro.kra.dto.KRARate;

public class MakeJsonTest {

	public static void main(String[] args) {
		try {
			KRARate rate = new KRARate();
			rate.setZoneName("서울");
			rate.setNum(1);
			rate.setType(1);
			rate.setTypeName("연식");
			//rate.setMoney(12345L);

			String[][] data = {{null, "1", "2", "3", "4", "5", "6", "7", "8", "9"}, {null, "9999.9", "9999.9", "9999.9", "9999.9", "9999.9", "9999.9", "9999.9", "9999.9", "9999.9"}};
			rate.setData(data);
			System.out.println(GSON.toString(rate));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
