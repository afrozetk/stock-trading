package Assignment2;
import Assignment1.Price;


// primary constructor
public record TradableDTO(Tradable tradableDTO,String user, String product, Price price, BookSide side, int originalVolume,
                          int remainingVolume, int cancelledVolume,
                          int filledVolume, String tradableId) {


    // second constructor
    public TradableDTO(Tradable tradable) {
        this(   tradable,
                tradable.getUser(),
                tradable.getProduct(),
                tradable.getPrice(),
                tradable.getSide(),
                tradable.getOriginalVolume(),
                tradable.getRemainingVolume(),
                tradable.getCancelledVolume(),
                tradable.getFilledVolume(),
                tradable.getId()
        );
    }
}

