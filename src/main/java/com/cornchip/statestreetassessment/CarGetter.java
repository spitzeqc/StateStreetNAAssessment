package com.cornchip.statestreetassessment;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface used for defining a function for getting a car, along with a function for re-sorting the car list
 */
// NOTE: We are using an abstract class to allow for easy implementation of different sorting methods
// Using an Abstract Class instead of an Interface to "enforce" the existence of attributes and the constructor
public abstract class CarGetter {
    /**
     * List of all the cars for the CarGetter to access
     */
    protected List<Car> carList;

    /**
     * Class constructor
     * @param carList List of Car objects to work on when determining which vehicle to rent out
     */
    public CarGetter(List<Car> carList) {
        this.carList = carList;
    }

    public Car getCar(DatePair date) { return this.getCar(date.startDate, date.endDate); }
    /**
     * Perform action to return a car to rent
     *
     * @param startDate Start time for the car rental
     * @param endDate End time for the car rental
     *
     * @return Car object to rent out
     */
    abstract Car getCar(LocalDate startDate, LocalDate endDate);

    /**
     * Update the position of the provided Car
     * Assumes that all other cars are properly sorted
     *
     * @param carToFix The Car object within the list to re-sort
     */
    // NOTE: We define an update function to optimize the process. Once everything is sorted once, only one car should really change at a time
    abstract void update(Car carToFix);

    /**
     * Re-sort the entire car list
     */
    // NOTE: We add a sort function to perform the initial sort
    // Can be rerun occasionally to ensure accurate internal lists
    abstract void sort();

    @Override
    public String toString() {

        return "";
    }
}
