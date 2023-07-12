package convertidor_alura;

public class Temperatures {

	private String key;
	private double baseValue;

	public void setKey(String key) {
		this.key = key;
	}

	public void setBaseValue(double baseValue) {
		this.baseValue = baseValue;
	}

	public double tempConverter() {
		double result = 0;
		switch (key) {
		case "00": // C2C
		case "11": // F2F
		case "22": // K2K
			result = baseValue;
			break;
		case "01": // C2F
			result = (baseValue * 9 / 5) + 32;
			break;
		case "02": // C2K
			result = baseValue + 273.15;
			break;
		case "10": // F2C
			result = (baseValue - 32) * 5 / 9;
			break;
		case "12": // F2K
			result = (baseValue - 32) * 5 / 9 + 273.15;
			break;
		case "20": // K2C
			result = baseValue - 273.15;
			break;
		case "21": // K2F
			result = (baseValue - 273.15) * 9 / 5 + 32;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

}
