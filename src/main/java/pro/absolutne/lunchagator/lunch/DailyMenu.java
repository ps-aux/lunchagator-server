package pro.absolutne.lunchagator.lunch;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class DailyMenu {

    private final LocalDate day;
    private final Restaurant restaurant;
    private final Collection<MenuItem> items;

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getDay() {
        return day;
    }

}
