package Assignment2;

import Assignment1.InvalidPriceException;
import Assignment1.Price;

import static java.lang.System.nanoTime;


public class QuoteSide implements Tradable {
    private final String user;
    private final String product;
    private final Price price;
    private final BookSide side;
    private final int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;
    private int filledVolume;
    private final String tradableId;

    // Constructor
    public QuoteSide(String user, String product, Price price, BookSide side, int originalVolume) throws InvalidInput, InvalidPriceException {
        if (user == null || !user.matches("[A-Z]{3}")) {
            throw new InvalidInput("Invalid user input: User code must be exactly 3 uppercase letters.");
        }
        if (product == null || !product.matches("[A-Z0-9.]{1,5}")) {
            throw new InvalidInput("Invalid product symbol: Must be 1 to 5 characters and can include a period.");
        }
        if (price == null) {
            throw new InvalidPriceException("Price cannot be null");
        }
        if (originalVolume <= 0 || originalVolume >= 10000) {
            throw new InvalidInput("Original volume must be greater than 0 and less than 10,000.");
        }


        this.user = user;
        this.product = product;
        this.price = price;
        this.originalVolume = originalVolume;
        this.side = side;
        this.remainingVolume = originalVolume;
        this.cancelledVolume = 0;
        this.filledVolume = 0;
        this.tradableId = generateId(user, product, price);
    }

    private String generateId(String user, String product, Price price) {
        return user + product + price.toString() + nanoTime();
    }

    @Override
    public String getId() {
        return tradableId;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getProduct() {
        return product;
    }

    @Override
    public int getOriginalVolume() {
        return originalVolume;
    }

    @Override
    public int getRemainingVolume() {
        return remainingVolume;
    }

    @Override
    public void setRemainingVolume(int newVol) throws InvalidVolumeParameter {
        if (newVol < 0 || newVol > originalVolume) {
            throw new InvalidVolumeParameter("Remaining volume must be between 0 and the original volume.");
        }
        this.remainingVolume = newVol;  // Set new remaining volume
    }

    @Override
    public int getCancelledVolume() {
        return cancelledVolume;
    }

    @Override
    public void setCancelledVolume(int newVol) throws InvalidVolumeParameter {
        if (newVol < 0 || newVol > originalVolume) {
            throw new InvalidVolumeParameter("Cancelled volume must be between 0 and the original volume.");
        }
        this.cancelledVolume = newVol;
    }

    @Override
    public int getFilledVolume() {
        return filledVolume;
    }

    @Override
    public void setFilledVolume(int newVol) throws InvalidVolumeParameter {
        if (newVol < 0 || newVol > originalVolume) {
            throw new InvalidVolumeParameter("Filled volume must be between 0 and the original volume.");
        }
        this.filledVolume = newVol;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public BookSide getSide() {
        return side;
    }

    @Override
    public String toString() {
        return user + " " + side + " side quote for " + product + ": " + price + ", Orig Vol: " + originalVolume +
                ", Rem Vol: " + remainingVolume + ", Fill Vol: " + filledVolume + ", CXL Vol: " + cancelledVolume +
                ", ID: " + tradableId ;
    }

    public TradableDTO makeTradableDTO() {
        return new TradableDTO(this);
    }
}