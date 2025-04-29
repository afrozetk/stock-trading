package Assignment3;
import Assignment1.Price;
import Assignment2.*;
import Assignment4.*;

import java.util.HashMap;
import java.util.Map;

public class User implements CurrentMarketObserver {
    private final String userId;
    private final HashMap<String, TradableDTO> tradables;
    private HashMap<String, CurrentMarketSide[]> currentMarkets;

    public User(String userId) throws InvalidInput {
        setUserId(userId);
        this.userId = userId;
        this.tradables = new HashMap<>();
        this.currentMarkets = new HashMap<>();
    }

    private void setUserId(String userId) throws InvalidInput {
        if (userId == null || userId.isEmpty() || !userId.matches("[A-Z]{3}")) {
            throw new InvalidInput("Invalid input");
        }
    }

    public String getUserId() {
        return userId;
    }

    public void updateTradable(TradableDTO o) {
        if (o != null) {
            tradables.put(o.tradableId(), o);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User Id: " + userId + "\n");
        for (TradableDTO tradable : tradables.values()) {
            sb.append("\tProduct: ").append(tradable.product())
                    .append(", Price: ").append(tradable.price())
                    // .append(", Price: ").append(tradable.price())
                    .append(", Original Volume: ").append(tradable.originalVolume())
                    .append(", Remaining volume: ").append(tradable.remainingVolume())
                    .append(", Cancelled Volume: ").append(tradable.cancelledVolume())
                    .append(", Filled Volume: ").append(tradable.filledVolume())
                    .append(", User: ").append(tradable.user())
                    .append(" , Side: ").append(tradable.side())
                    .append(", id:").append(tradable.tradableId())
                    .append("\n")
            ;



        }
        return sb.toString();
    }

    //ASSIGNMENT 4
    @Override
    public void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) {
        //create a 2-element array
        CurrentMarketSide[] cmuObjects = new CurrentMarketSide[2];
        cmuObjects[0] = buySide;
        cmuObjects[1] = sellSide;
        //add array to hashmap
        currentMarkets.put(symbol, cmuObjects);

    }


    public String getCurrentMarkets() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CurrentMarketSide[]> entry : currentMarkets.entrySet()) {
            sb.append(entry.getKey())
                    .append(" ")
                    .append(entry.getValue()[0].toString()).append(" - ")
                    .append(entry.getValue()[1].toString()).append("\n");
        }
        //System.out.println("Current Markets Data: ");  // Debug print
        return sb.toString();
    }
}

