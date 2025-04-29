package Assignment2;
import Assignment1.InvalidPriceException;
import Assignment1.Price;

public class Quote {
    private  String user;
    private  String product;
    private final QuoteSide buySide;
    private final QuoteSide sellSide;


    public Quote(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume, String userName) throws InvalidInput, InvalidPriceException {
        setProduct(symbol);
        setUser(userName);

//        //validate symbol and username
//        if (symbol == null || !symbol.matches("[A-Z0-9.]{1,5}")) {
//            throw new InvalidInput("Invalid input");
//        }
//        if (userName == null || !userName.matches("[A-Z]{3}")) {
//            throw new InvalidInput("Invalid input");
//        }

        //set product to symbol and user to username
        this.buySide = new QuoteSide(user, product, buyPrice, BookSide.BUY, buyVolume);
        this.sellSide = new QuoteSide(user, product, sellPrice, BookSide.SELL, sellVolume);

    }
    private void setProduct(String symbol) throws InvalidInput {
        if (symbol == null || !symbol.matches("[A-Z0-9.]{1,5}")) {
            throw new InvalidInput("Invalid product symbol");
        }
        this.product = symbol;
    }

    private void setUser(String userName) throws InvalidInput {
        if (userName == null || !userName.matches("[A-Z]{3}")) {
            throw new InvalidInput("Invalid user name");
        }
        this.user = userName;
    }

    public QuoteSide getQuoteSide (BookSide sideIn){
        if (sideIn == BookSide.BUY){
            return buySide;
        } else if (sideIn == BookSide.SELL) {
            return sellSide;
        } else {
            throw new IllegalArgumentException("Invalid side");
        }

    }
    public String getSymbol() {
        return product;
    }

    public String getUser() {
        return user;
    }
}



