package kr.co.netbro.kra.dto;

import org.apache.commons.lang.StringUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KRARate {
	
	@Expose
	@SerializedName("zone_name")
	private String zoneName;
	
	@Expose
	@SerializedName("num")
	private Integer num;
	
	@Expose
	@SerializedName("type")
	private Integer type;
	
	@Expose
	@SerializedName("type_name")
	private String typeName;
	
	@Expose
	@SerializedName("time")
	private String time;
	
	@Expose
	@SerializedName("money")
	private String money;
	
	@Expose
	@SerializedName("minimum")
	private String minimum;
	
	@Expose
	@SerializedName("data")
	private String[][] data;

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		if(StringUtils.isNotBlank(time)) {
			if(time.equals("xx")) {
				this.time = "마감";
			} else if(time.equals("yy")) {
				this.time = "분전";
			} else {
				this.time = "마감 "+time+"분전";
			}
		} else
			this.time = time;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	
}
