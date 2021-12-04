package com.example.project4;

import java.util.ArrayList;

/**
 * This class defines the StoreOrders object, which stores all the orders currently placed.
 * The orders are stored in an ArrayList of orders. Methods included allow the user to cancel
 * an order.
 *
 * @author Pawel Zajkowski, Mark Ayad
 */
public class StoreOrders {
    protected ArrayList<Order> orders;

    /**
     * Creates a new StoreOrders Object with an empty arrayList of Orders.
     */
    public StoreOrders() {
        orders = new ArrayList<Order>();
    }

    /**
     * Removes an order from the Store orders list.
     *
     * @param o order to remove
     * @return true if the order was removed, false otherwise
     */
    public boolean cancelOrder(Order o) {
        if (!orders.contains(o)) {
            return false;
        }
        orders.remove(o);
        return true;
    }

    public Order findOrder(String num) {
        for (Order o: orders) {
            if (o.phoneNumber.equals(num)){
                return o;
            }
        }
        return null;
    }
}
