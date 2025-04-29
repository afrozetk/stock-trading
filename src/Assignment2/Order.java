package Assignment2;
import Assignment1.InvalidPriceException;
import Assignment1.Price;
import static java.lang.System.nanoTime;

public class Order implements Tradable {
    private  String user;
    private  String product;
    private  Price price;
    private  BookSide side;
    private int originalVolume;
    public int remainingVolume;
    public int cancelledVolume;
    public int filledVolume;
    private final String tradableId;

    // constructor
    public Order(String user, String product, Price price, int originalVolume, BookSide side) throws InvalidInput, InvalidVolumeParameter, InvalidPriceException {
        setUser(user);
        setProduct(product);
        setOriginalVolume(originalVolume);
        setPrice(price);
        setSide(side);
        this.remainingVolume = originalVolume;
        this.tradableId = setId(user,product,price);
        this.cancelledVolume = 0;
        this.filledVolume = 0;
    }

    private void setSide(BookSide side) {
        if(side != null){
            this.side=side;
        }

    }

    private void setPrice(Price price) throws InvalidPriceException {
        if(price == null){
            throw new InvalidPriceException("Price cannot be null");
        }
        this.price=price;
    }


    private String setId(String user, String product, Price price) {
        return user + product + price.toString() + nanoTime();
    }
    //getter methods
    @Override
    public String getId() {
        return tradableId;
    }

    private void setUser(String user) throws InvalidInput {
        if (user == null || !user.matches("[A-Z]{3}")) {
            throw new InvalidInput("Invalid user input");
        }
        this.user=user;

    }

    @Override
    public String getUser() {
        return user;
    }

    private void setProduct(String product) throws InvalidInput {
        if (product == null || !product.matches("[A-Z0-9.]{1,5}")) {
            throw new InvalidInput("Invalid product symbol");
        }
        this.product=product;
    }
    @Override
    public String getProduct() {
        return product;
    }

    private void setOriginalVolume(int originalVolume) throws InvalidVolumeParameter {
        if (originalVolume < 0 || originalVolume > 10000) {
            throw new InvalidVolumeParameter("Original volume must be greater than 0 and less than 10,000");
        }
        this.originalVolume=originalVolume;
    }

    @Override
    public int getOriginalVolume() {
        return originalVolume;
    }


    @Override
    public void setRemainingVolume(int newVol) throws InvalidVolumeParameter{
        if (newVol < 0 && newVol < originalVolume) {
            throw new InvalidVolumeParameter("Remaining volume must be between 0 and the original volume");
        }
        this.remainingVolume = newVol;
    }

    @Override
    public int getRemainingVolume() {
        return remainingVolume;
    }

    @Override
    public void setCancelledVolume(int newVol) throws InvalidVolumeParameter {
        if (newVol < 0 || newVol > originalVolume) {
            throw new InvalidVolumeParameter("Cancelled volume must be between 0 and the original volume");
        }
        this.cancelledVolume = newVol;
    }
    @Override
    public int getCancelledVolume() {
        return cancelledVolume;
    }

    @Override
    public void setFilledVolume(int newVol) {
        if (filledVolume < 0 || newVol > originalVolume - cancelledVolume) {
            throw new IllegalArgumentException("Filled volume must be between 0 and (original volume - cancelled volume)");
        }
        this.filledVolume=newVol;
    }
    @Override
    public int getFilledVolume() {
        return filledVolume;
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
        return String.format("%s %s order: %s at %s, Orig Vol: %d, Rem Vol: %d, Fill Vol: %d, CXL Vol: %d, ID: %s",
                user, side, product, price, originalVolume, remainingVolume, filledVolume, cancelledVolume, tradableId);
    }


    public TradableDTO makeTradableDTO() {
        return new TradableDTO(this);
    }
}


