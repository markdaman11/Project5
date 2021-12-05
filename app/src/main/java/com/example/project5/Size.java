package com.example.project5;

/**
 * This class defines the enum Size, which is used by the different pizza classes.
 * The different Toppings are small, medium, or large. There is also a method for calculating
 * the premium for each size for the pizza.
 */
public enum Size {
    small, medium, large;

    public static final double SMALL_PRICE = 0;
    public static final double MEDIUM_PRICE = 2.00;
    public static final double LARGE_PRICE = 4.00;

    /**
     * Calculates the price increase the pizza will have due to the size increase.
     *
     * @return the additional price of the size
     */
    public double sizePrice() {
        switch(this) {
            case small:
                return SMALL_PRICE;
            case medium:
                return MEDIUM_PRICE;
            case large:
                return LARGE_PRICE;
        }
        return SMALL_PRICE;
    }
}
