package point.of.sale.terminal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import point.of.sale.product.PricingManagerExternalResource;

public class ShoppingCartTest {
    
    @ClassRule
    public static PricingManagerExternalResource pricingManagerExternalResource =
            new PricingManagerExternalResource();
    
    private ShoppingCart shoppingCart;
    
    @Before
    public void before() {
        shoppingCart = new ShoppingCart(pricingManagerExternalResource.getPricingManager());
    }
    
    @Test
    public void test1() {
        shoppingCart.add("A");
        shoppingCart.add("B");
        shoppingCart.add("C");
        shoppingCart.add("D");
        shoppingCart.add("A");
        shoppingCart.add("B");
        shoppingCart.add("A");
        shoppingCart.add("A");
        
        Assert.assertEquals("Invalid total", 32.40f, shoppingCart.checkout(), 0);
    }
    
    @Test
    public void test2() {
        shoppingCart.add("C");
        shoppingCart.add("C");
        shoppingCart.add("C");
        shoppingCart.add("C");
        shoppingCart.add("C");
        shoppingCart.add("C");
        shoppingCart.add("C");
        
        Assert.assertEquals("Invalid total", 7.25f, shoppingCart.checkout(), 0);
    }
    
    @Test
    public void test3() {
        shoppingCart.add("A");
        shoppingCart.add("B");
        shoppingCart.add("C");
        shoppingCart.add("D");
        
        Assert.assertEquals("Invalid total", 15.40f, shoppingCart.checkout(), 0);
    }
    
    @Test
    public void testClear() {
        shoppingCart.add("A");
        shoppingCart.clear();
        Assert.assertEquals("Invalid total", 0, shoppingCart.checkout(), 0);
    }
}
