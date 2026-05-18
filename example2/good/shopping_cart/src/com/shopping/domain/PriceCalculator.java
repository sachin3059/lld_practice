package example2.good.shopping_cart.src.com.shopping.domain;


public class PriceCalculator {
    public double calculateSubtotal(ShoppingCart cart){
        if(cart == null){
            throw new IllegalArgumentException("Cart cannot be null");
        }

        return cart.getItems().stream().mapToDouble(Item::getSubtotal).sum();
    }
}
