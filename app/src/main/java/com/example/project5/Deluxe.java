package com.example.project5;

import java.text.DecimalFormat;

/**
 * This class defines the Deluxe object, which extends the Pizza
 * superclass. The Deluxe class contains the same instance variables and methods
 * as the Pizza class and implements the price() method for calculating the price
 * of the pizza. The deluxe pizza starts with 5 default toppings: Pepperoni, Sausage,
 * Mushrooms, Onions, and Peppers.
 *
 * @author Pawel Zajkowski, Mark Ayad
 */
public class Deluxe extends Pizza{
    public static final double BASE_PRICE = 12.99;
    public static final int BASE_TOPPINGS = 5;
    double price;

    /**
     * Creates a Deluxe object using a Size parameter. Also adds the five
     * default toppings to the pizza: Pepperoni, Sausage, Mushrooms, Onions,
     * and Peppers.
     *
     * @param size the default size of the pizza
     */
    public Deluxe(Size size) {
        super(size);
        price = price();
        super.addToppings(Topping.Pepperoni);
        super.addToppings(Topping.Sausage);
        super.addToppings(Topping.Mushrooms);
        super.addToppings(Topping.Onions);
        super.addToppings(Topping.Peppers);
    }

    /**
     * Calculates the price of the pizza depending on the size and
     * number of toppings. The default price is $12.99.
     *
     * @return the price of the pizza before tax
     */
    @Override
    public double price() {
        double price = BASE_PRICE;
        if(super.toppings.size() > BASE_TOPPINGS) {
            price += (super.toppings.size() - BASE_TOPPINGS) * Pizza.TOPPING_PRICE;
        }
        price += super.size.sizePrice();
        return price;
    }
    /**
     * Converts the pizza information to a string.
     *
     * @return String of Pizza information
     */
    @Override
    public String toString() {
        String pizzaString = "Deluxe Pizza, ";
        for(int i  = 0; i < super.toppings.size(); i++) {
            pizzaString += super.toppings.get(i).name();
            pizzaString += ", ";
        }
        pizzaString += super.size.name();
        pizzaString += ", ";
        DecimalFormat df = new DecimalFormat("###.##");
        String formattedPrice = df.format(price());
        pizzaString += "$" + formattedPrice;
        return pizzaString;
    }
}
