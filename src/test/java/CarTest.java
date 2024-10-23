import com.cornchip.statestreetassessment.Car;
import com.cornchip.statestreetassessment.DatePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarTest {
    @Test
    public void testCarTypes() {
        // Test car types are set correctly when initing
        Car carSedanTest = new Car(Car.CarType.SEDAN, 0, "abcdef");
        Assertions.assertSame(carSedanTest.getType(), Car.CarType.SEDAN);
        Car carSuvTest = new Car(Car.CarType.SUV, 0, "abcdef");
        Assertions.assertSame(carSuvTest.getType(), Car.CarType.SUV);
        Car carVanTest = new Car(Car.CarType.VAN, 0, "abcdef");
        Assertions.assertSame(carVanTest.getType(), Car.CarType.VAN);
    }
    @Test
    public void testCarMiles() {
        Car milesTest = new Car(Car.CarType.SEDAN, -2, "abcdef");
        Assertions.assertEquals( milesTest.getMiles(), -2 ); // Miles init correctly?
        milesTest.setMiles(18);
        Assertions.assertEquals(milesTest.getMiles(), 18); // Setting miles correctly?
        milesTest.addMiles(2);
        Assertions.assertEquals(milesTest.getMiles(), 20); // Adding miles correctly?
    }

    @Test
    public void testCarAddDate() {
        Car dateTest = new Car(Car.CarType.SEDAN, 0, "abcdef");

        DatePair date1 = new DatePair(
                LocalDate.parse( "1900-01-01" ),
                LocalDate.parse( "1900-01-02" )
        );

        DatePair date2 = new DatePair(
                LocalDate.parse( "1901-02-05" ),
                LocalDate.parse( "1901-02-28" )
        );

        DatePair date3 = new DatePair(
                LocalDate.parse( "1900-01-05" ),
                LocalDate.parse( "1900-02-05" )
        );

        List<DatePair> correctOrder = new ArrayList<>(3);
        correctOrder.add(date1);
        correctOrder.add(date3);
        correctOrder.add(date2);

        dateTest.addDate(date1);
        dateTest.addDate(date2);
        dateTest.addDate(date3);
        // should be order 1, 3, 2
        List<DatePair> bookedDates = dateTest.getBookedDates();

        Assertions.assertEquals( bookedDates.size(), correctOrder.size() );
        for(int i=0; i<bookedDates.size(); ++i) {
            Assertions.assertEquals( bookedDates.get(i).startDate, correctOrder.get(i).startDate );
            Assertions.assertEquals( bookedDates.get(i).endDate, correctOrder.get(i).endDate );
        }
    }

    @Test
    public void testCarIsAvailable() {
        Car availableTest = new Car(Car.CarType.SEDAN, 0, "abcdef");
        DatePair date1 = new DatePair(
                LocalDate.parse("1901-01-01"),
                LocalDate.parse("1901-02-01")
        );

        Assertions.assertTrue( availableTest.isAvailable(date1) );
        availableTest.addDate(date1);

        // Add additional dates for further testing
        availableTest.addDate(
                LocalDate.parse("1902-01-01"),
                LocalDate.parse("1902-01-03")
        );
        availableTest.addDate(
                LocalDate.parse("1901-06-01"),
                LocalDate.parse("1901-06-03")
        );

        // Left of existing date
        Assertions.assertTrue( availableTest.isAvailable(
                LocalDate.parse("1901-03-01"),
                LocalDate.parse("1901-03-05")
        ) );
        // Right of existing date
        Assertions.assertTrue( availableTest.isAvailable(
                LocalDate.parse("1901-08-01"),
                LocalDate.parse("1901-08-05")
        ) );


        // Interfere with existing date in some way
        Assertions.assertFalse( availableTest.isAvailable(date1) ); // Same date
        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1901-01-01"),
                LocalDate.parse("1901-01-02"))
        ); // Same start
        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1901-02-01"),
                LocalDate.parse("1901-02-02"))
        ); // Start is same as existing end

        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1900-01-01"),
                LocalDate.parse("1901-02-01"))
        ); // Same end

        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1900-01-01"),
                LocalDate.parse("1901-01-01"))
        ); // End is same as existing start

        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1901-01-05"),
                LocalDate.parse("1901-05-02"))
        ); // Start is in range of existing date

        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1900-01-01"),
                LocalDate.parse("1901-01-05"))
        ); // End is in range of existing date

        Assertions.assertFalse( availableTest.isAvailable(
                LocalDate.parse("1901-01-10"),
                LocalDate.parse("1901-01-12"))
        ); // entire date is within existing date
    }
}
