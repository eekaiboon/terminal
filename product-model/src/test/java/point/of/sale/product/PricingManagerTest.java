package point.of.sale.product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PricingManagerTest {
   
    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();
    
    private static File pricingFile;
    
    private PricingManager pricingManager;
    
    @BeforeClass
    public static void beforeClass() throws IOException {
        pricingFile = folder.newFile("pricing.txt");
        PrintWriter out = new PrintWriter(new FileWriter(pricingFile));
        out.println("A,2,7,4");
        out.println("B,12,,");
        out.println("C,1.25,6,6");
        out.println("D,0.15,,");
        out.close();
    }
    
    @Before
    public void before() {
        pricingManager = new PricingManager();
    }

    @Test
    public void testSetPricing() throws IOException {
        pricingManager.setPricing(pricingFile.getAbsolutePath());
    }
    
    @Test(expected=IOException.class)
    public void testInvalidSetPricing() throws IOException {
        pricingManager.setPricing("invalidFile.txt");
    }
    
    @Test
    public void testAddPricing() {
        final String productCode = "A";
        final float initialUnitPrice = 1;
        
        pricingManager.addPricing(new Pricing(productCode, initialUnitPrice, 1, 1));
        Assert.assertEquals("Invalid unit price",
                pricingManager.getPricing(productCode).getUnitPrice(), 
                initialUnitPrice, 0);
        
        final float updatedUnitPrice = 2;
        pricingManager.addPricing(new Pricing(productCode, updatedUnitPrice, 1, 1));
        Assert.assertEquals("Invalid unit price",
                pricingManager.getPricing(productCode).getUnitPrice(), 
                updatedUnitPrice, 0);
   
    }
    
    @Test
    public void testHasPricing() {
        pricingManager.addPricing(new Pricing("A", 1, 1, 1));
        Assert.assertFalse("Invalid product code", pricingManager.hasProduct("B"));
    }
}
