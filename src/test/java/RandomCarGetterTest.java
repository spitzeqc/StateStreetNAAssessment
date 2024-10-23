import com.cornchip.statestreetassessment.Car;
import com.cornchip.statestreetassessment.CarGetter;
import com.cornchip.statestreetassessment.DatePair;
import com.cornchip.statestreetassessment.RandomCarGetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RandomCarGetterTest {
    @Test
    public void testRandomCarGetter() {
        List<Car> carList = new ArrayList<>();
        Car car1 = new Car(Car.CarType.SUV, 0, "1");
        Car car2 = new Car(Car.CarType.SUV, 0, "2");
        Car car3 = new Car(Car.CarType.SUV, 0, "3");
        Car car4 = new Car(Car.CarType.SUV, 0, "4");
        Car car5 = new Car(Car.CarType.SUV, 0, "5");

        carList.add(car1);
        carList.add(car2);
        carList.add(car3);
        carList.add(car4);
        carList.add(car5);

        CarGetter getter = new RandomCarGetter(carList, 0);

        DatePair date = new DatePair(
                LocalDate.parse("1900-01-01"),
                LocalDate.parse("1900-01-05")
        );

        // 1,4,5,3,1 normally (when no dates are booked)
        Assertions.assertEquals( getter.getCar(date), car1 );
        Assertions.assertEquals( getter.getCar(date), car4 );
        Assertions.assertEquals( getter.getCar(date), car5 );
        Assertions.assertEquals( getter.getCar(date), car3 );

        // add date to car1 to force it to go to car2
        car1.addDate(date);

        Assertions.assertEquals( getter.getCar(date), car2 );

        // Add date to all remaining cars
        car2.addDate(date);
        car3.addDate(date);
        car4.addDate(date);
        car5.addDate(date);

        // All cars booked on our date, none should return
        Assertions.assertNull( getter.getCar(date) );
    }
}
