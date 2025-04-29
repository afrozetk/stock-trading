package Assignment4;

import Assignment1.*;

public class CurrentMarketSide {

    private final int volume;
    private final Price price;

    //constructor
    public CurrentMarketSide(Price price, int volume) {
        this.price = price;
        this.volume = volume;
    }
    @Override
    public String toString() {
        return price.toString() + "X" + volume;
    }
}
