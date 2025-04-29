package Assignment2;

import Assignment1.InvalidPriceException;
import Assignment1.Price;
import Assignment3.DataValidationException;
import Assignment4.CurrentMarketTracker;


public class ProductBook {
    private final String product;
    private final ProductBookSide buySide;
    private final ProductBookSide sellSide;

    // Constructor
    public ProductBook(String symbol) throws InvalidInput {
        if (symbol == null || !symbol.matches("[A-Z0-9.]{1,5}")) {
            throw new InvalidInput("Invalid input");
        }
        this.product = symbol;
        this.buySide = new ProductBookSide(BookSide.BUY);
        this.sellSide = new ProductBookSide(BookSide.SELL);
    }

    public TradableDTO add(Tradable t) throws InvalidInput, InvalidPriceException, InvalidVolumeParameter, DataValidationException {
        if (t == null) {
            throw new InvalidInput("Tradable cannot be null");
        }
        TradableDTO newDto;
        if (t.getSide() == BookSide.BUY) {
            newDto = buySide.add(t);
        } else {
            newDto = sellSide.add(t);
        }
        tryTrade();
        updateMarket();
        return newDto;
    }

    public TradableDTO[] add(Quote qte) throws InvalidInput, InvalidVolumeParameter, InvalidPriceException, DataValidationException {
        if (qte == null) {
            throw new InvalidInput("Quote cannot be null");
        }
        removeQuotesForUser(qte.getUser());
        TradableDTO buyDto = buySide.add(qte.getQuoteSide(BookSide.BUY));
        TradableDTO sellDto = sellSide.add(qte.getQuoteSide(BookSide.SELL));
        tryTrade();

        return new TradableDTO[]{buyDto, sellDto};
    }

    public TradableDTO cancel(BookSide side, String orderId) throws InvalidVolumeParameter, DataValidationException, InvalidPriceException {
        TradableDTO orderCancel;
        if (side == BookSide.BUY) {

             orderCancel = buySide.cancel(orderId);
            updateMarket();
            //Assignment 4
        } else if (side == BookSide.SELL) {
            orderCancel = sellSide.cancel(orderId);
            updateMarket();
        }
        else{
            throw new DataValidationException("Side is invalid");
        }
        return orderCancel;
    }

    public TradableDTO[] removeQuotesForUser(String userName) throws InvalidVolumeParameter, DataValidationException, InvalidPriceException {

        TradableDTO buyDto = buySide.removeQuotesForUser(userName);
        TradableDTO sellDto = sellSide.removeQuotesForUser(userName);
        //assignment4
        updateMarket();
        return new TradableDTO[]{buyDto, sellDto};
    }
    public void tryTrade() throws InvalidPriceException, InvalidVolumeParameter, DataValidationException {
        Price topBuyPrice = buySide.topOfBookPrice();
        Price topSellPrice = sellSide.topOfBookPrice();

        if (topBuyPrice == null || topSellPrice == null) {
            return;
        }

        while (topBuyPrice != null && topSellPrice != null && topSellPrice.compareTo(topBuyPrice) <= 0) {

            int toTrade = Math.min(buySide.topOfBookVolume(), sellSide.topOfBookVolume());
            buySide.tradeOut(topBuyPrice, toTrade);
            sellSide.tradeOut(topSellPrice, toTrade);


            topBuyPrice = buySide.topOfBookPrice();
            topSellPrice = sellSide.topOfBookPrice();
        }
    }


    @Override
    public String toString() {
        return String.format("Product: %s\n%s\n%s", product, buySide.toString(), sellSide.toString());
    }

    public String getTopOfBookString(BookSide side) {
        if (side == BookSide.BUY) {
            return "Top of BUY book: " +
                    (buySide.topOfBookPrice() == null ? "$0.00" : buySide.topOfBookPrice()) +
                    " x " + buySide.topOfBookVolume();
        } else {
            return "Top of SELL book: " +
                    (sellSide.topOfBookPrice() == null ? "$0.00" : sellSide.topOfBookPrice()) +
                    " x " + sellSide.topOfBookVolume();
        }
    }
    //Assignment4
    private void updateMarket() throws InvalidPriceException {
        Price sellTopOfBookPrice = sellSide.topOfBookPrice();
        int sellTopOfBookVolume = sellSide.topOfBookVolume();

        Price buyTopOfBookPrice= buySide.topOfBookPrice();
        int buyTopOfBookVolume = buySide.topOfBookVolume();

        CurrentMarketTracker cmt = CurrentMarketTracker.getInstance();
        cmt.updateMarket(product, buyTopOfBookPrice, buyTopOfBookVolume, sellTopOfBookPrice, sellTopOfBookVolume);

      }


}


