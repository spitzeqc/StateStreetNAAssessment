package com.cornchip.statestreetassessment;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * A CarGetter class that performs searches based on the Car's license field
 */
public class RandomCarGetter extends CarGetter {
    private Random rng;

    /**
     * Class constructor
     *
     * @param carList List of Car objects to work on when determining which vehicle to rent out
     */
    public RandomCarGetter(List<Car> carList) {
        super(carList);
        this.rng = new Random();
    }
    public RandomCarGetter(List<Car> carList, long seed) {
        super(carList);
        this.rng = new Random(seed);
    }

    @Override
    Car getCar(LocalDate startDate, LocalDate endDate) {
        if(this.carList == null) return null;
        if(this.carList.isEmpty()) return null; // No cars exist

        int startIndex = rng.nextInt( this.carList.size() );
        int index = startIndex;

        while(true) {
            Car ret = this.carList.get(index++);
            if( ret.isAvailable(startDate, endDate) ) return ret; // Car is available, return it
            if(index >= this.carList.size()) index = 0; // go beyond end of list, go back to 0
            if(index == startIndex) return null; // Tried all cars, none found
        }
    }

    @Override
    /**
     * In this implementation, the order of cars does not matter, so we can ignore this method
     *
     * @param carToFix unused
     */
    void update(Car carToFix) { return; }

    /**
     * In this implementation, we are randomly choosing a car every time, so sorting provides no benefit and is ignored
     */
    @Override
    void sort() { return; }
}
