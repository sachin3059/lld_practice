package example2.good.shopping_cart.src.com.shopping.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {
    private final String customerEmail;
    private final String customerPhone;
    private final List<Item> items;

    public ShoppingCart(String customerEmail, String customerPhone) {
        if (customerEmail == null || customerEmail.isBlank() || !customerEmail.contains("@")) {
            throw new IllegalArgumentException("Invalid customer email");
        }
        if (customerPhone == null || !customerPhone.matches("\\d{10,}")) {
            throw new IllegalArgumentException("Invalid customer phone");
        }
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        boolean removed = items.remove(item);
        if (!removed) {
            throw new IllegalStateException("Item not found in cart: " + item);
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }

    public String getCustomerEmail() { return customerEmail; }
    public String getCustomerPhone() { return customerPhone; }

    @Override
    public String toString() {
        return "ShoppingCart{customerEmail='" + customerEmail +
               "', customerPhone='" + customerPhone +
               "', items=" + items + "}";
    }
}


