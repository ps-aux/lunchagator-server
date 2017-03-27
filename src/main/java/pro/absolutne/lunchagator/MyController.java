package pro.absolutne.lunchagator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchagator.lunch.DailyMenu;
import pro.absolutne.lunchagator.lunch.MenuProvider;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RequestMapping("menu")
@RestController
public class MyController {

    @Autowired
    private List<MenuProvider> providers;


    @GetMapping("dbg")
    public String foo() {
        return "Foo!";
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        return providers.stream()
                .map(MenuProvider::findDailyMenu)
                .collect(toList());
    }
}
