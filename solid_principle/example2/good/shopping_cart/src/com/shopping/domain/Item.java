package solid_principle.example2.good.shopping_cart.src.com.shopping.domain;

import java.util.Objects;

public class Item {
    private final String name;
    private final double price;
    private final int quantity;
    private final String category;

    public Item(String name, double price, int quantity, String category){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if(quantity < 0){
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if(category == null || category.isBlank()){
            throw new IllegalArgumentException("Category cannoit be null or blank");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getCategory(){
        return category;
    }

    public double getSubtotal(){
        return price * quantity;
    }

    @Override
    public String toString(){
        return "Item{name='" + name + "', price=" + price + ", quantity=" + quantity + ", category='" + category + "'}";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Item)) return false;

        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 && quantity == item.quantity && name == item.name && category == item.category;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(name, price, quantity, category);
    }
}
