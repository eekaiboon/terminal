package point.of.sale.terminal;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import point.of.sale.product.PricingManager;

public class Terminal {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private PricingManager pricingManager;
    private ShoppingCart shoppingCart;
    
    public Terminal() {
        this.pricingManager = new PricingManager();
        this.shoppingCart = new ShoppingCart(pricingManager);
    }
    
    public void setPricing(String pricingFile) {
        log.info("Setting pricing");
        try {
            pricingManager.setPricing(pricingFile);
        } catch (IOException e) {
            log.error("Error while parsing pricing file", e);
        }
    }
    
    public void scan(String productCode) {
        log.info("Scanning " + productCode);
        shoppingCart.add(productCode);
    }
    
    public float total() {
        float total = shoppingCart.checkout();
        shoppingCart.clear();
        log.info(String.format("Total is %.2f", total));
        return total;
    }
    
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.setPricing("src/main/resources/pricing.txt");
        terminal.scan("A");
        terminal.scan("B");
        terminal.scan("C");
        terminal.scan("D");
        terminal.scan("A");
        terminal.scan("B");
        terminal.scan("A");
        terminal.scan("A");
        float result = terminal.total();
        log.info(String.format("Total is %.2f", result));
    }
}
