package point.of.sale.terminal;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import point.of.sale.product.Pricing;
import point.of.sale.product.PricingManager;

public class ShoppingCart {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private PricingManager pricingManager;
    private Map<String, Integer> items;

    public ShoppingCart(PricingManager pricingManager) {
        this.pricingManager = pricingManager;
        this.items = new HashMap<>();
    }

    public void add(String productCode) {
        if (pricingManager.hasProduct(productCode)) {
            if (items.containsKey(productCode)) {
                items.put(productCode, items.get(productCode) + 1);
            } else {
                items.put(productCode, 1);
            }
        } else {
            log.error("Invalid product code : " + productCode);
        }
    }

    public void clear() {
        items.clear();
    }

    public float checkout() {
        float total = 0;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productCode = entry.getKey();
            int amount = entry.getValue();

            Pricing pricing = pricingManager.getPricing(productCode);
            if (pricing.isSoldInVolume()) {
                total += (amount / pricing.getUnitPerVolume()) * pricing.getVolumePrice();
                total += (amount % pricing.getUnitPerVolume()) * pricing.getUnitPrice();
            } else {
                total += amount * pricing.getUnitPrice();
            }
        };

        return total;
    }
}
