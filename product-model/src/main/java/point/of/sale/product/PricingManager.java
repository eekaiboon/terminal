package point.of.sale.product;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PricingManager holds all products that offered by the store and their
 * pricing details.
 * 
 * @author kaiboonee
 */
public class PricingManager {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Map<String, Pricing> pricings;
    
    public PricingManager() {
        this.pricings = new HashMap<>();
    }
    
    // Pricing file should have the following fields with comma-separated
    // (1) product code
    // (2) unit price
    // (3) volume price
    // (4) unit per volume
    public void setPricing(String pricingFile) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(pricingFile));
        for(String line : lines) {
            String[] tokens = line.split(",");
            
            if (tokens.length < 2) {
                log.debug(Arrays.toString(tokens));
                String errorMessage = "Invalid pricing file";
                log.error(errorMessage);
                // Fail hard if pricing file is invalid
                throw new RuntimeException(errorMessage);
            }
            
            String productCode = tokens[0];
            float unitPrice;
            float volumePrice;
            int unitPerVolume;
            
            try {
                unitPrice = Float.parseFloat(tokens[1]);
                volumePrice = (tokens.length < 3) ? 0 : Float.parseFloat(tokens[2]);
                unitPerVolume = (tokens.length < 4) ? 0 : Integer.parseInt(tokens[3]);
            } catch (NumberFormatException e) {
                String errorMessage = "Invalid pricing file";
                log.error(errorMessage);
                // Fail hard if pricing file is invalid
                throw new RuntimeException(errorMessage, e);
            }
            
            addPricing(new Pricing(productCode, unitPrice, volumePrice, unitPerVolume));
        }
    }
    
    // A current product that is being held by product manager will be 
    // overwritten if a new product has the same product code as the current 
    // product
    public void addPricing(Pricing product) {
        pricings.put(product.getProductCode(), product);
    }
    
    public Pricing getPricing(String productCode) {
        return pricings.get(productCode);
    }
    
    public boolean hasProduct(String productCode) {
        return pricings.containsKey(productCode);
    }
}
