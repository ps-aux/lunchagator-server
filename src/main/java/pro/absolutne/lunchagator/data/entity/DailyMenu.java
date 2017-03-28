package pro.absolutne.lunchagator.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
public class DailyMenu {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate day;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "daily_menu_id")
    private Collection<MenuItem> items;

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getDay() {
        return day;
    }


}
