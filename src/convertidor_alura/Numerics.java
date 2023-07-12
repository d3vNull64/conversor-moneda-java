package convertidor_alura;

public class Numerics {

	private String key, numberString;

	public void setKey(String key) {
		this.key = key;
	}

	public void setNumberString(String numberString) {
		this.numberString = numberString;
	}

	public String decToBase(int baseTarget) {

		try {
			int number = Integer.parseInt(numberString);
			if (baseTarget == 2) {
				return Integer.toBinaryString(number);
			} else if (baseTarget == 8) {
				return Integer.toOctalString(number);
			} else
				return Integer.toHexString(number);
		} catch (Exception e) {
			return "Error";
		}

	}

	public String baseToDec(int baseBase) {
		try {
			return String.valueOf(Integer.parseInt(numberString, baseBase));
		} catch (Exception e) {
			return "Error";
		}

	}

	public String conversionFilter() {
		String result;
		switch (key) {
		case "00":
		case "11":
		case "22":
		case "33":
			result = numberString;
			break;
		case "01": // D2H
			result = decToBase(16);
			break;
		case "02": // D2O
			result = decToBase(8);
			break;
		case "03": // D2B
			result = decToBase(2);
			break;
		case "10": // H2D
			result = baseToDec(16);
			break;
		case "12": // H2O
			numberString = baseToDec(16);
			result = decToBase(8);
			break;
		case "13": // H2B
			numberString = baseToDec(16);
			result = decToBase(2);
			break;
		case "20": // O2D
			result = baseToDec(8);
			break;
		case "21": // O2H
			numberString = baseToDec(8);
			result = decToBase(16);
			break;
		case "23": // O2B
			numberString = baseToDec(8);
			result = decToBase(2);
			break;
		case "30": // B2D
			result = baseToDec(2);
			break;
		case "31": // B2H
			numberString = baseToDec(2);
			result = decToBase(16);
			break;
		case "32": // B2O
			numberString = baseToDec(2);
			result = decToBase(8);
			break;
		default:
			result = "Error";
			break;
		}
		return result;
	}

}
