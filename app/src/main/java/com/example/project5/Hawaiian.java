package com.example.project5;

import java.text.DecimalFormat;

/**
 * This class defines the Hawaiian object, which extends the Pizza
 * superclass. The Hawaiian class contains the same instance variables and methods
 * as the Pizza class and implements the price() method for calculating the price
 * of the pizza. The deluxe pizza starts with 2 default toppings: Pineapple and Ham.
 *
 * @author Pawel Zajkowski, Mark Ayad
 */
public class Hawaiian extends Pizza{
    public static final double BASE_PRICE = 10.99;
    public static final int BASE_TOPPINGS = 2;
    double price;

    /**
     * Creates a Hawaiian object using a Size parameter. Also adds the two
     * default toppings to the pizza: Pineapple and Ham.
     *
     * @param size the default size of the pizza
     */
    public Hawaiian(Size size) {
        super(size);
        price = price();
        super.addToppings(Topping.Ham);
        super.addToppings(Topping.Pineapple);
    }

    /**
     * Calculates the price of the pizza depending on the size and
     * number of toppings. The default price is $10.99.
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
     * Converts the pizza to a string
     *
     * @return String of Pizza information
     */
    @Override
    public String toString() {
        String pizzaString = "Hawaiian Pizza, ";
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
