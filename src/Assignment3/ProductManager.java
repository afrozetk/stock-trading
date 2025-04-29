package Assignment3;

import Assignment1.*;
import Assignment2.*;


import java.util.HashMap;


public class ProductManager {
    private static ProductManager instance;
    private final HashMap<String, ProductBook> productBooks;


    private ProductManager(){
        productBooks = new HashMap<>();
    }

    public static ProductManager getInstance(){
        if (instance == null) instance = new ProductManager();
        return instance;
    }
    public void addProduct(String symbol) throws DataValidationException, InvalidInput {
        if (symbol == null || !symbol.matches("[A-Z0-9.]{1,5}")) {
            throw new DataValidationException("Invalid input");
        }
        //create new product book objcet and pass in symbol and add it to the hashmap else throw exception
        ProductBook book = new ProductBook(symbol);
        productBooks.put(symbol, book);
    }
    public ProductBook getProductBook(String symbol) throws  DataValidationException {
        ProductBook productBook = productBooks.get(symbol);
        if (productBook == null) {
            throw new DataValidationException("Product not found");
        }
        //returns product book using the string symbol passed
        return productBook;
    }
    public String getRandomProduct() throws DataValidationException {
        //checks if the product book is empty
        if (productBooks.isEmpty()) {
            throw new DataValidationException("No products available");
        }
        //generate a random number that does not exceed the product book side
        int randomIndex = (int) (Math.random() * productBooks.size());
        //iterate through the hashmap and check if the generated number is in the product book and return the corresponding symbol
        int i = 0;
        for (String symbol : productBooks.keySet()) {
            if (i == randomIndex) {
                return symbol;
            }
            i++;
        }
        throw new DataValidationException("Invalid product");
    }

    public TradableDTO addTradable(Tradable o) throws DataValidationException, InvalidPriceException, InvalidInput, InvalidVolumeParameter {
        if (o == null) {
            throw new DataValidationException("Tradable cannot be null");
        }
//add tradable to the book, call updateTradable giving it the new id and new tradable created and return the DTO
        ProductBook productBook = getProductBook(o.getProduct());
        TradableDTO newTradableDTO = productBook.add(o);
        UserManager.getInstance().updateTradable(o.getUser(), newTradableDTO);
        return newTradableDTO;
    }

    public TradableDTO[] addQuote(Quote q) throws DataValidationException, InvalidInput, InvalidVolumeParameter, InvalidPriceException {
        if (q == null) {
            throw new DataValidationException("Quote cannot be null");
        }
        //get the productbook and call removeQuotesFromUser, call addTradable method passing the buy and sell quotes
            ProductBook book = getProductBook(q.getSymbol());
            book.removeQuotesForUser(q.getUser());
            TradableDTO buyDTO = book.add(q.getQuoteSide(BookSide.BUY));
            TradableDTO sellDTO = book.add(q.getQuoteSide(BookSide.SELL));

            return new TradableDTO[]{buyDTO, sellDTO};
        }


    public TradableDTO cancel(TradableDTO o) throws DataValidationException, InvalidVolumeParameter, InvalidPriceException {
        if (o ==null){
            throw new DataValidationException("Tradable cannot be null");
        }
        String symbol = o.product();
        ProductBook book = productBooks.get(symbol);
        TradableDTO result = book.cancel(o.side(), o.tradableId());
        if (result == null) {
            System.out.println("Failed to cancel TradableDTO");
            return null;
    }else{
            return result;
        }
}
    public TradableDTO[] cancelQuote(String symbol, String user) throws DataValidationException, InvalidVolumeParameter, InvalidPriceException {
        if (symbol == null || user == null) {
            throw new DataValidationException("Invalid symbol or user");
        }
        ProductBook productBook = getProductBook(symbol);
        return productBook.removeQuotesForUser(user);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ProductBook productBook : productBooks.values()) {
            sb.append(productBook.toString()).append("\n");
        }
        return sb.toString();
    }
}

