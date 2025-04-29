package Assignment2;

import Assignment1.Price;

public interface Tradable {
    String getId();
    int getRemainingVolume();
    void setCancelledVolume(int newVol) throws InvalidVolumeParameter;
    int getCancelledVolume();
    void setRemainingVolume(int newVol) throws InvalidVolumeParameter;
    TradableDTO makeTradableDTO();
    Price getPrice();
    void setFilledVolume(int newVol) throws InvalidVolumeParameter;
    int getFilledVolume();
    BookSide getSide();
    String getUser();
    String getProduct();
    int getOriginalVolume();

}
