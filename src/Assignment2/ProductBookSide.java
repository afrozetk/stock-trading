package Assignment2;

import Assignment1.InvalidPriceException;
import Assignment1.Price;
import Assignment3.DataValidationException;
import Assignment3.UserManager;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class ProductBookSide {
    private final BookSide side;
    private final TreeMap<Price, ArrayList<Tradable>> bookEntries;

    public ProductBookSide(BookSide side) throws InvalidInput {
        if (side == null) {
            throw new InvalidInput("Side cannot be Null");
        }
        this.side = side;
        if (side == BookSide.BUY) {
            this.bookEntries = new TreeMap<Price, ArrayList<Tradable>>(Collections.reverseOrder());
        } else {
            this.bookEntries = new TreeMap<Price, ArrayList<Tradable>>();
        }
    }


    public TradableDTO add(Tradable o) throws DataValidationException {
        Price p = o.getPrice();
        if (!bookEntries.containsKey(p)) {
            ArrayList<Tradable> tradables = new ArrayList<>();
            tradables.add(o);
            bookEntries.put(p, tradables);
        } else {
            bookEntries.get(p).add(o);
        }
        //assignment 3
        UserManager.getInstance().updateTradable(o.getUser(), o.makeTradableDTO());
        return o.makeTradableDTO();
    }


    public TradableDTO cancel(String tradableId) throws InvalidVolumeParameter, DataValidationException {
        for (Price price : bookEntries.keySet()) {

            ArrayList<Tradable> tradablesAtPrice = bookEntries.get(price);
            for (Tradable q : tradablesAtPrice) {

                if (q.getId().equals(tradableId)) {

                    int remainingVolume = q.getRemainingVolume();
                    q.setCancelledVolume(q.getCancelledVolume() + remainingVolume);
                    q.setRemainingVolume(0);
                    tradablesAtPrice.remove(q);
                }

                if (tradablesAtPrice.isEmpty()) {
                    bookEntries.remove(price);
                    }
                //assignment 3
                UserManager.getInstance().updateTradable(q.getUser(), q.makeTradableDTO());
                return q.makeTradableDTO();
                }

        }
        return null;
    }



    public TradableDTO removeQuotesForUser(String userName) throws InvalidVolumeParameter, DataValidationException {
        for (Price price : bookEntries.keySet()) {
            ArrayList<Tradable> trad = bookEntries.get(price);
            for (Tradable t : trad) {
                if (t.getUser().equals(userName)) {
                    t.setCancelledVolume(t.getRemainingVolume());
                    t.setRemainingVolume(0);
                    trad.remove(t);

                    if (trad.isEmpty()) {
                        bookEntries.remove(price);
                    }
                    //assignment 3
                    UserManager.getInstance().updateTradable(t.getUser(), t.makeTradableDTO());
                    return t.makeTradableDTO();
                }
            }
        }
        return null;
    }


    public Price topOfBookPrice() {
        if (bookEntries.isEmpty()) {
            return null;
        } else {
            return bookEntries.firstKey();
        }
    }

    public int topOfBookVolume() {
        if (bookEntries.isEmpty()) {
            return 0;
        }   Price topPrice;
            if (side == BookSide.SELL) {
                topPrice = bookEntries.lastKey();
            } else {
                topPrice = bookEntries.firstKey();
            }
            ArrayList<Tradable> topTradables = bookEntries.get(topPrice);
            //return bookEntries.get(topPrice).stream().mapToInt(Tradable::getRemainingVolume).sum();
            int ctr = 0;
            for (Tradable t : topTradables){
                ctr += t.getRemainingVolume();
            }
            return ctr;
        }




        public void tradeOut(Price price, int vol) throws InvalidVolumeParameter, DataValidationException {
            //get top book price
            Price topPrice = topOfBookPrice();
            //check if it is null else return
            if (topPrice == null) {
                return;
            }
            //check if the top price is greater than the price passed, if not return
            if (topPrice.compareTo(price) > 0) {
                return;
            }
            ArrayList<Tradable> atPrice = bookEntries.get(topPrice);
            int totalVolAtPrice = 0;
            for (Tradable t : atPrice) {
                totalVolAtPrice += t.getRemainingVolume();
            }
            if (vol >= totalVolAtPrice) {
                for (Tradable t : atPrice) {
                    t.getRemainingVolume();
                    t.setFilledVolume(t.getOriginalVolume());
                    t.setRemainingVolume(0);
                    //FULL FILL: (BUY 50) XEN BUY side quote for TGT: $133.00, Orig Vol: 50, Rem Vol: 0, Fill Vol: 50, CXL Vol: 0, ID: XENTGT$133.00518653408166800
                    //assignment 3
                    UserManager.getInstance().updateTradable(t.getUser(), t.makeTradableDTO());
                    System.out.println(" FULL FILL: (" + t.getSide() + " " + t.getOriginalVolume() + ") " + t.toString());
                }
                bookEntries.remove(topPrice);

            } else {
                int remainder = vol;
                for (Tradable t : atPrice) {

                    double ratio = (double) t.getRemainingVolume() / totalVolAtPrice;
                    int toTrade = (int) Math.ceil(vol * ratio);
                    toTrade = Math.min(toTrade, remainder);
                    t.setFilledVolume(t.getFilledVolume() + toTrade);
                    t.setRemainingVolume(t.getRemainingVolume() - toTrade);
                    System.out.println(" PARTIAL FILL: (" + t.getSide() + " " + toTrade + ") " + t.toString());

                    remainder -= toTrade;

                    if (remainder == 0) {
                        break;
                    }
                }
                for (Tradable t : atPrice) {
                    //assignment 3
                    UserManager.getInstance().updateTradable(t.getUser(), t.makeTradableDTO());

//                }
                }
            }
        }

    @Override
    public String toString() {
        StringBuilder sideSummary = new StringBuilder();
        sideSummary.append("Side: ").append(side).append("\n");
        if (bookEntries.isEmpty()) {
            sideSummary.append("<Empty>").append("\n");
        } else {

            for (Price price : bookEntries.keySet()) {
                sideSummary.append("Price: ").append(price).append("\n");
                ArrayList<Tradable> tradablesAtPrice = bookEntries.get(price);

                for (Tradable tradable : tradablesAtPrice) {
                    sideSummary.append(tradable.toString()).append("\n");
                }
            }

        }
        return sideSummary.toString();
    }
}







