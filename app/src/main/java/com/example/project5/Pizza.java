package com.example.project5;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines the Pizza object, which is extended by the Deluxe, Hawaiian,
 * and Pepperoni classes. The Pizza class contains an ArrayList of Toppings that are
 * on the pizza, as well as a Size value for the size of the pizza. It allows the user
 * to add/ remove toppings and change the size
 *
 * @author Pawel Zajkowski, Mark Ayad
 */
public abstract class Pizza implements Serializable {
    protected ArrayList<Topping> toppings;
    protected Size size;
    public abstract double price();
    public static final double TOPPING_PRICE = 1.49;
    public static final int MAX_TOPPINGS = 7;

    /**
     * Creates a Pizza object using a Size parameter, and creates
     * an arraylist for the toppings.
     *
     * @param size the default size of the pizza
     */
    public Pizza(Size size) {
        this.size = size;
        toppings = new ArrayList<Topping>();
    }


    /**
     * Tries to add a selected topping to the pizza.
     *
     * @param topping Topping to be added
     * @return true if the topping was added, false otherwise
     */
    public boolean addToppings(Topping topping) {
        if (toppings.size() < MAX_TOPPINGS) {
            toppings.add(topping);
            return true;
        }
        return false;
    }

    /**
     * Tries to remove a topping from the pizza.
     *
     * @param topping Topping to be removed
     * @return true if removed, false if not
     */
    public boolean RemoveToppings(Topping topping) {
        if (toppings.remove(topping)) {
            return true;
        }
        return false;
    }

    /**
     * Changes the size of the pizza to a new size.
     *
     * @param size new Size of the pizza
     */
    public void setSize(Size size) {
        this.size = size;
    }

}
