package point.of.sale.product;

import org.junit.rules.ExternalResource;

public class PricingManagerExternalResource extends ExternalResource {
    
    private PricingManager pricingManager;
    
    @Override
    protected void before() throws Throwable {
        pricingManager = new PricingManager();
        
        pricingManager.addPricing(new Pricing("A", 2, 7, 4));
        pricingManager.addPricing(new Pricing("B", 12, 0, 0));
        pricingManager.addPricing(new Pricing("C", 1.25f, 6, 6));
        pricingManager.addPricing(new Pricing("D", 0.15f, 0, 0));
    }

    @Override
    protected void after() {
        pricingManager = null;
    }
    
    public PricingManager getPricingManager() {
        return pricingManager;
    }
}
