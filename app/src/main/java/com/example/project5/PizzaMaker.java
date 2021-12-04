package com.example.project4;

/**
 * The CLass defines the PizzaMaker object, which is used by the GUi to create pizza objects.
 * The type of pizza created depends on the input string.
 */
public class PizzaMaker {
    /**
     * Creates a Pizza object based on the given flavor input.
     *
     * @param flavor the flavor of the pizza
     * @return the Pizza object created
     */
    public static Pizza createPizza(String flavor) {
//write the code for creating different instances of subtypes of pizza
        if (flavor.equalsIgnoreCase("Hawaiian")){
            Hawaiian newHawaiianPizza = new Hawaiian(Size.small);
            return  newHawaiianPizza;
        } else if (flavor.equalsIgnoreCase("Deluxe")) {
            Deluxe newDeluxePizza = new Deluxe(Size.small);
            return  newDeluxePizza;
        } else {
            Pepperoni newPepperoniPizza = new Pepperoni(Size.small);
            return  newPepperoniPizza;
        }
    }

}
