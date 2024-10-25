package com.cornchip.statestreetassessment;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String []args) {
        List<Car> sedanList = new ArrayList<>();
        sedanList.add( new Car(Car.CarType.SEDAN, 182, "sedan1") );
        sedanList.add( new Car(Car.CarType.SEDAN, 100, "sedan2") );
        sedanList.add( new Car(Car.CarType.SEDAN, 1000, "sedan3") );

        List<Car> suvList = new ArrayList<>();
        suvList.add( new Car(Car.CarType.SUV, 1802, "suv1") );
        suvList.add( new Car(Car.CarType.SUV, 20080, "suv2") );
        suvList.add( new Car(Car.CarType.SUV, 3, "suv3") );
        suvList.add( new Car(Car.CarType.SUV, 14, "suv4") );
        suvList.add( new Car(Car.CarType.SUV, 909, "suv5") );

        List<Car> vanList = new ArrayList<>();
        vanList.add( new Car(Car.CarType.VAN, 999, "van1") );

        CarGetter sedanGetter = new RandomCarGetter(sedanList);
        CarGetter suvGetter = new RandomCarGetter(suvList);
        CarGetter vanGetter = new RandomCarGetter(vanList);

        Scanner inputScanner = new Scanner(System.in);
        while(true) {
            Car ret;

            Car.CarType type = null;
            LocalDate startDate = null;
            LocalDate endDate = null;

            // Loop until car type is determined
            boolean running = true;
            while(running) {
                System.out.print("Enter desired car type (Sedan, Van, sUv)> ");
                String carType = inputScanner.nextLine();
                switch(carType.toLowerCase()) {
                    case "sedan":
                    case "s":
                        type = Car.CarType.SEDAN;
                        running=false;
                        break;
                    case "van":
                    case "v":
                        type = Car.CarType.VAN;
                        running=false;
                        break;
                    case "suv":
                    case "u":
                        type = Car.CarType.SUV;
                        running=false;
                        break;
                    default:
                        System.out.println("Unable to determine provided car type (" + carType + ")");
                }
            }

            // Get start date
            running = true;
            while(running) {
                System.out.print("Enter desired start date (YYYY-MM-DD)> ");
                String startString = inputScanner.nextLine();

                try {
                    startDate = LocalDate.parse(startString);
                    running = false;
                } catch (DateTimeParseException e) {
                    System.out.println("Unable to parse date (" + startString + ")");
                }
            }

            // Get end date
            running = true;
            while(running) {
                System.out.print("Enter desired end date (YYYY-MM-DD)> ");
                String endString = inputScanner.nextLine();

                try {
                    endDate = LocalDate.parse(endString);
                    if( endDate.isBefore(startDate) ) {
                        System.out.println("Start date (" + startDate + ") is after End date (" + endDate + ")");
                        continue;
                    }
                    running = false;
                } catch (DateTimeParseException e) {
                    System.out.println("Unable to parse date (" + endString + ")");
                }
            }

            // Find car that matches requirements
            switch(type) {
                case SUV:
                    ret = suvGetter.getCar(startDate, endDate);
                    break;
                case SEDAN:
                    ret = sedanGetter.getCar(startDate, endDate);
                    break;
                case VAN:
                    ret = vanGetter.getCar(startDate, endDate);
                    break;
                default:
                    ret = null;
            }

            if(ret == null) {
                System.out.println("Unable to find " + type + " for dates " + startDate + " to " + endDate);
                continue;
            }

            ret.addDate(startDate, endDate);
            System.out.println(ret + "\n");
        }
    }
}
