package pro.absolutne.lunchagator.lunch.provider;

import pro.absolutne.lunchagator.lunch.*;

import java.time.LocalDate;
import java.util.Collections;

public class FixedMenuProvider implements MenuProvider {

    private Restaurant restaurant = new Restaurant(
            "fixed-restaurant",
            new Location("nowhere", 23, 55));

    @Override
    public DailyMenu findMenuFor() {

        MenuItem i = new MenuItem("pre dobytok", 24);

        return new DailyMenu(LocalDate.now(), Collections.singleton(i), restaurant);
    }
}
