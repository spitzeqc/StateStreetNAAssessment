import com.cornchip.statestreetassessment.DatePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DatePairTest {
    @Test
    public void testDatePair() {
        LocalDate start = LocalDate.parse( "1900-01-01" );
        LocalDate end = LocalDate.parse("1900-01-02");

        DatePair pairTest = new DatePair(start, end);

        Assertions.assertEquals(pairTest.startDate, LocalDate.parse("1900-01-01"));
        Assertions.assertEquals(pairTest.endDate, LocalDate.parse("1900-01-02"));
    }
}
