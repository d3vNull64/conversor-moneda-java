package convertidor_alura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Currencies {
	private String baseCurrency;
	private String targetCurrency;
	private double amount;

	public void setBaseCurrency(String baseCuString) {
		this.baseCurrency = baseCuString;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double convertCurrency() {
		try {
			String urlStr = "https://api.exchangerate-api.com/v4/latest/" + targetCurrency;

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// Parsear la respuesta JSON
				String jsonResponse = response.toString();
				double exchangeRate = parseExchangeRate(jsonResponse, baseCurrency);

				// Realizar la conversión
				double conversion = amount / exchangeRate;
				return conversion;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static double parseExchangeRate(String jsonResponse, String targetCurrency) {
		int startIndex = jsonResponse.indexOf(targetCurrency);
		int endIndex = jsonResponse.indexOf(",", startIndex);
		String rateSubstring = jsonResponse.substring(startIndex, endIndex);
		double exchangeRate = Double.parseDouble(rateSubstring.split(":")[1]);
		return exchangeRate;
	}

}
