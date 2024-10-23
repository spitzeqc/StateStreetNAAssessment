package com.cornchip.statestreetassessment;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Car {
    public enum CarType {
        SEDAN,
        SUV,
        VAN;
    }

    private final CarType type; // Type of car
    private int miles; // Miles on car
    private String license; // License plate of car

    // NOTE: This list should be sorted based on either end of the Pair. No dates can ever overlap, so sorting one will sort the other
    // Additionally, this allows us to do two binary searches (one for start and one for end) to quickly determine availability

    // Using array list because continuous blocks of memory allows for fast binary search
    private List<DatePair> bookedDates = new ArrayList<>();

    public Car(CarType type, int miles, String license) {
        this.type = type;
        this.miles = miles;
        this.license = license;
    }

    /**
     * Add miles onto the car
     * @param amount number of miles to add to the car
     */
    public void addMiles(int amount) { this.miles += amount; }

    /**
     * Set the miles on the car to a specific amount
     * @param amount Number of miles to set the car to
     */
    public void setMiles(int amount) { this.miles = amount; }

    /**
     * Get the current miles on the car
     * @return Number of miles on the car
     */
    public int getMiles() { return this.miles; }

    public CarType getType() { return this.type; }


    public boolean isAvailable(@NotNull DatePair date) {
        return this.isAvailable(date.startDate, date.endDate);
    }
    /**
     *
     * @param start Start date for the booking
     * @param end End date for the booking
     * @return True if the car is available during the given date range
     */
    public boolean isAvailable(LocalDate start, LocalDate end) {
        if(this.bookedDates.isEmpty()) return true; // no dates exist, so cannot be booked

        // Perform binary search on the list to determine availability
        int startPos = 0;
        int endPos = this.bookedDates.size()-1;
        while(true) {
            int checkPoint = startPos + ((endPos - startPos) / 2); // Start at midpoint

            LocalDate checkStartDate = (this.bookedDates.get(checkPoint)).startDate;
            LocalDate checkEndDate = (this.bookedDates.get(checkPoint)).endDate;

            // Start or end exactly matches start/end of existing date, cannot book
            if (start.isEqual(checkStartDate) ||
                    start.isEqual(checkEndDate) ||
                    end.isEqual(checkStartDate) ||
                    end.isEqual(checkEndDate))
                return false;

            // Both start and end are before current check date begins, go to left of "tree"
            if (start.isBefore(checkStartDate) && end.isBefore(checkStartDate)) {
                endPos = checkPoint - 1; // already checked "checkPoint", move it over to the left 1 spot
            }
            // Both start and end are after current check date ends, go to right of "tree"
            else if (start.isAfter(checkEndDate) && end.isAfter(checkEndDate)) {
                startPos = checkPoint + 1; // already checked "checkPoint", move it over to the right 1 spot
            }
            // start or end of the proposed dates falls between an existing date range, cannot check out car
            else {
                return false;
            }

            // Start position for search is past end position, should not be possible unless we run out of items
            if(startPos > endPos) return true;
        }
    }

    public void addDate(@NotNull DatePair dates) {
        this.addDate(dates.startDate, dates.endDate);
    }

    /**
     * Add a date to the internal list of dates
     * Note: Does NOT check if dates would overlap
     * @param start start Date
     * @param end end Date
     */
    public void addDate(LocalDate start, LocalDate end) {
        if(this.bookedDates.isEmpty()) {
            this.bookedDates.add( new DatePair(start, end) );
            return;
        }

        // Do linear search to find proper location
        // We only need to compare start to end dates because we assume there will be no overlap
        // TODO: change to binary search to improve speed
        for(int i=0; i<this.bookedDates.size(); ++i) {
            if( end.isBefore(this.bookedDates.get(i).startDate) ) {
                this.bookedDates.add(i, new DatePair(start, end));
                return;
            }
        }
        // New date is last in (sorted) list, append to end
        this.bookedDates.add( new DatePair(start, end) );
    }
    public List<DatePair> getBookedDates() { return this.bookedDates; }

    // NOTE: We override this to get a clean output if we want to print a car
    public String toString() {
        return "Type: " + this.type + "\nLicense: " + this.license + "\nMiles: " + this.miles;
    }
}
