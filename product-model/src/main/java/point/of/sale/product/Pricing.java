package point.of.sale.product;

import net.jcip.annotations.Immutable;

@Immutable
public class Pricing {
    private final String productCode;
    private final float unitPrice;
    private final float volumePrice;
    private final int unitPerVolume;
    
    public Pricing(String productCode, float unitPrice, float volumePrice,
            int unitPerVolume) {
        this.productCode = productCode;
        this.unitPrice = unitPrice;
        this.volumePrice = volumePrice;
        this.unitPerVolume = unitPerVolume;
    }

    public String getProductCode() {
        return productCode;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getVolumePrice() {
        return volumePrice;
    }

    public int getUnitPerVolume() {
        return unitPerVolume;
    }
    
    public boolean isSoldInVolume() {
        return volumePrice > 0 && unitPerVolume > 0;
    }
}
