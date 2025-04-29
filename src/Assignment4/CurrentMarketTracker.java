package Assignment4;

import Assignment1.*;

import static Assignment1.PriceFactory.makePrice;
import static com.sun.tools.javac.main.Option.O;

public class CurrentMarketTracker {
    //singleton has get instance
    private static CurrentMarketTracker instance;

    public static CurrentMarketTracker getInstance() {
        if (instance == null) {
            instance = new CurrentMarketTracker();
        }
        return instance;
    }

    //    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws InvalidPriceException {
//        int marketWidth=0;
//        if (sellPrice==0 ||  buyPrice == 0){
//            marketWidth=0;
//        }else{
//            marketWidth = sellPrice.compareTo(buyPrice);
//            //create a CurrentMarketSide object for buySide, using the buyPrice and buyVolume
//            CurrentMarketSide CMUBuy = new CurrentMarketSide(buyPrice,buyVolume);
//            //create a CurrentMarketSide object for buySide, using the sellPrice and sellVolume
//            CurrentMarketSide CMUSell = new CurrentMarketSide(sellPrice,sellVolume);
//            //print the current Market

    //            System.out.println("*********** Current Market ***********\n" + "*" + symbol +
//                    " " + buyPrice + "x" + buyVolume + "–" + sellPrice + "x" + sellVolume
//                    + "[" + difference + "] \n" + "**************************************");
//            CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol, buySide, sellSide);
//
//
//        }
//
//    }
    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws InvalidPriceException {
        int marketWidth;

        if (buyPrice == null || sellPrice == null) {
            marketWidth = 0;
        } else {
            marketWidth = sellPrice.compareTo(buyPrice);
        }

        Price safeBuyPrice;
        if (buyPrice != null) {
            safeBuyPrice = buyPrice;
        } else {
            safeBuyPrice = PriceFactory.makePrice(0);
        }

        Price safeSellPrice;
        if (sellPrice != null) {
            safeSellPrice = sellPrice;
        } else {
            safeSellPrice = PriceFactory.makePrice(0);
        }


        CurrentMarketSide buySide = new CurrentMarketSide(safeBuyPrice, buyVolume);
        CurrentMarketSide sellSide = new CurrentMarketSide(safeSellPrice, sellVolume);

        // Print the current market data in the required format
        System.out.println("*********** Current Market ***********");
        System.out.println("* " + symbol + " " + buySide + " - " + sellSide + " [" + String.format("$%.2f", (double) marketWidth / 100) +   "] ");
        System.out.println("**************************************");

        // Publish the current market information to the CurrentMarketPublisher
        CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol, buySide, sellSide);
    }
}
