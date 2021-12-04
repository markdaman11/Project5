package com.example.project4;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the Order object, which is used by the StoreOrders class.
 * An order Object contains an Arraylist of pizzas in the order and an order total value.
 * Methods included allow the user to calculate the total for an order, add a pizza to an order,
 * calculate the sales tax, and add a specified amount to the total.
 *
 * @author Pawel Zajkowski, Mark Ayad
 */
public class Order {
    protected ArrayList<Pizza> pizzas;
    protected String phoneNumber;
    protected double orderTotal;

    public static final double TAX_RATE = 0.06625;
    public static final double TAX_MULTIPLIER = 1.06625;

    /**
     * Creates an Order Object with a String parameter for the phone number.
     * Also creates an ArrayList of pizzas for the order.
     *
     * @param phoneNumber
     */
    public Order(String phoneNumber) {
        pizzas = new ArrayList<Pizza>();
        this.phoneNumber = phoneNumber;
        orderTotal = 0;
    }

    /**
     * Adds a pizza to the current order.
     *
     * @param p pizza to add to the order
     */
    public void addToOrder(Pizza p) {
        pizzas.add(p);
        addToTotal(p.price() * TAX_MULTIPLIER);
    }

    /**
     * Calculates the total for an order, including tax.
     *
     * @return the order total
     */
    public double calcTotal() {
        orderTotal = calcSubtotal() * TAX_MULTIPLIER;
        return orderTotal;
    }

    public double calcSubtotal() {
        double total = 0;
        for (Pizza p: pizzas){
            total += p.price();
        }
        return total;
    }

    /**
     * Increases the order total by a specified amount.
     *
     * @param amount the amount to add
     */
    public void addToTotal(double amount) {
        orderTotal += amount;
    }

    /**
     * Calculates the sales tax for the order
     *
     * @return the tax amount
     */
    public double calcTax(){
        return orderTotal * TAX_RATE;
    }

    /**
     Checks if two orders are equal by comparing their names and majors.
     @param obj the order to compare to.
     @return true if the orders are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order){
            Order o = (Order) obj;
            if (this.phoneNumber.equals(o.phoneNumber)){
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the Order to a string.
     *
     * @return String of Order information
     */
    @Override
    public String toString() {
        String orderString = "Order phone number: " + phoneNumber + "\n";
        for (Pizza pizza: pizzas) {
            if (pizza instanceof Hawaiian) {
                Hawaiian h = (Hawaiian) pizza;
                orderString += h.toString() + "\n";
            } else if (pizza instanceof Pepperoni) {
                Pepperoni p = (Pepperoni) pizza;
                orderString += p.toString() + "\n";;
            } else if (pizza instanceof Deluxe) {
                Deluxe d = (Deluxe) pizza;
                orderString += d.toString() + "\n";;
            }
        }
        DecimalFormat df = new DecimalFormat("###.##");
        String formattedTotal = df.format(calcTotal());
        orderString += "Order total: $" + formattedTotal;
        return orderString;
    }



}
