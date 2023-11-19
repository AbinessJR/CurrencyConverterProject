import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CurrencyConverter")
public class CurrencyConverterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch input values from the HTML form
        double amount = Double.parseDouble(request.getParameter("amount"));
        String fromCurrency = request.getParameter("fromCurrency");
        String toCurrency = request.getParameter("toCurrency");

        // Call the API to get the latest exchange rates
        String apiKey = "YOUR_API_KEY"; // Replace with your actual API key
        String apiUrl = "https://open.er-api.com/v6/latest/" + fromCurrency.toUpperCase() + "?apiKey=" + apiKey;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Read the response from the API
        InputStream inputStream = conn.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        StringBuilder responseBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            responseBuilder.append(scanner.nextLine());
        }
        scanner.close();

        // Parse the JSON response to get exchange rates
        double conversionRate = parseExchangeRates(responseBuilder.toString(), toCurrency);
        double convertedAmount = conversionRate * amount;

        // Set the converted amount as an attribute to pass to the JSP page
        request.setAttribute("amount", amount);
        request.setAttribute("fromCurrency", fromCurrency);
        request.setAttribute("toCurrency", toCurrency);
        request.setAttribute("convertedAmount", convertedAmount);
        request.setAttribute("conversionRate", conversionRate);

        // Forward the request to the result page
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }

    // Method to parse JSON response and get the exchange rate for the desired currency
    private double parseExchangeRates(String jsonResponse, String toCurrency) {
        // Implement JSON parsing logic to extract exchange rates for the 'toCurrency'
        // Extract the exchange rate for the 'toCurrency' from the JSON response
        // Return the exchange rate

        // Example parsing logic (you need to implement this)
        return 1.5; // Placeholder value for demonstration
    }
}
