package Assignment1;

import Assignment1.InvalidPriceException;
import Assignment1.PriceFactory;

import java.util.Objects;

public class Price implements Comparable<Price> {
    private final int cents;

    //constructor
    //implement flyweight pattern
    Price(int cents){
        this.cents=cents;
    }
    public boolean isNegative(){
        return this.cents < 0;
    }
    public Price add(Price p) throws InvalidPriceException {
        if (p==null) {
            throw new InvalidPriceException("Cannot add null to a price object");
        }
        return PriceFactory.makePrice(this.cents + p.cents);
    }
    public Price subtract(Price p) throws InvalidPriceException {
        if (p==null){
            throw new InvalidPriceException("Cannot subtract null from a Assignment1.Price object");
        }
        return PriceFactory.makePrice(this.cents -p.cents);
    }
    public Price multiply(int n){
        return PriceFactory.makePrice(this.cents * n);
    }
    public boolean greaterOrEqual(Price p) throws InvalidPriceException {
        if (p == null){
            throw new InvalidPriceException("Cannot check greater than or equal with a null");
        }
        return this.cents >= p.cents;
    }
    public boolean lessOrEqual(Price p) throws InvalidPriceException {
        if (p == null){
            throw new InvalidPriceException("Cannot check less than or equal with a null");
        }
        return this.cents <= p.cents;
    }
    public boolean greaterThan(Price p) throws InvalidPriceException {
        if (p == null){
            throw new InvalidPriceException("Cannot check greater than with a null");
        }
        return this.cents > p.cents;
    }
    public boolean lessThan(Price p) throws InvalidPriceException {
        if (p == null){
            throw new InvalidPriceException("Cannot check lesser than with a null");
        }
        return this.cents < p.cents;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;

        }
        Price price = (Price) o;
        return this.cents == price.cents;

    }


    @Override
    public int compareTo(Price p) {
        if (p==null){
            return -1;
        }
        return this.cents - p.cents;

    }
    @Override
    public String toString(){
        return String.format("$%.2f",(double)cents/100);
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.cents);

    }
}


