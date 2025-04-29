package Assignment1;


import java.util.HashMap;
import java.util.Map;

public class PriceFactory {
    private static final Map<Integer, Price> priceMap = new HashMap<>();

    public static Price makePrice(int cents) {
        return priceMap.computeIfAbsent(cents, Price::new);
    }

    // Method to create a Assignment1.Price object from a String value
    public static Price makePrice(String stringValueIn) throws InvalidPriceException {
            if (stringValueIn == null || stringValueIn.trim().isEmpty()) {
                throw new InvalidPriceException("Invalid price string.");
            }

            // Remove dollar sign and commas
            String newStr = stringValueIn.replaceAll("[$,]", "").trim();

            //checks for negative number
            boolean isNegative = newStr.startsWith("-");
            newStr = newStr.replace("-", "");

            // Split the price by dollar and cents parts
            String[] parts = newStr.split("\\.");

            int dollars = 0;
            int cents = 0;

            try {
                //before the decimal point, index 0 == before decimal
                if (parts.length > 0 && !parts[0].isEmpty()) {
                    dollars = Integer.parseInt(parts[0]);
                }
                //after decimal, cents part
                if (parts.length > 1) {
                    String centsPart = parts[1];
                    //checks if the cents is greater than 2 or if its 1 d.p
                    if (centsPart.length() > 2 || centsPart.length() == 1) {
                        throw new InvalidPriceException("Invalid price String value: " + stringValueIn);
                    } else {
                        //only returns 2 d.p
                        cents = Integer.parseInt(centsPart.substring(0, 2));
                    }
                }
                // Checks for negative values and sets the price in negative
                if (isNegative) {
                    dollars = -dollars;
                    cents = -cents;
                }
            } catch (NumberFormatException e) {
                throw new InvalidPriceException("Invalid price String value");
            }
            int total =(dollars*100) + cents;
            return new Price(total);
        }
    }