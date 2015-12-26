package Data;

/**
 * Created by Ozu94 on 12/13/2015.
 */
public class FoodData {
	private byte[] img;
	private String name;
	private String prices;
	private String number = "0";

	public FoodData() {
		super();
	}

	public FoodData(byte[] icon, String name, String prices) {
		super();
		this.img = icon;
		this.name = name;
		this.prices = prices;
	}

	public byte[] getimg() {
		return img;
	}

	public String getname() {
		return name;
	}

	public String getprices() {
		return prices;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
