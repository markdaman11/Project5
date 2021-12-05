package com.example.project5;

import android.os.Parcel;

import java.text.DecimalFormat;

/**
 * This class defines the Hawaiian object, which extends the Pizza
 * superclass. The Hawaiian class contains the same instance variables and methods
 * as the Pizza class and implements the price() method for calculating the price
 * of the pizza. The deluxe pizza starts with 1 default topping: Pepperoni.
 *
 * @author Pawel Zajkowski, Mark Ayad
 */
public class Pepperoni extends Pizza{
    public static final double BASE_PRICE = 8.99;
    public static final int BASE_TOPPINGS = 1;
    double price;

    /**
     * Creates a Pepperoni object using a Size parameter. Also adds the
     * default topping to the pizza: Pepperoni
     *
     * @param size the default size of the pizza
     */
    public Pepperoni(Size size) {
        super(size);
        price = price();
        super.addToppings(Topping.Pepperoni);
    }

    /**
     * Calculates the price of the pizza depending on the size and
     * number of toppings. The default price is $8.99.
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
     * Converts the pizza to a string.
     *
     * @return String of Pizza information
     */
    @Override
    public String toString() {
        String pizzaString = "Pepperoni Pizza, ";
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
