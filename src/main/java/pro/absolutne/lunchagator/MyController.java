package pro.absolutne.lunchagator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchagator.lunch.DailyMenu;
import pro.absolutne.lunchagator.lunch.provider.GattoMattoMenuProvider;
import pro.absolutne.lunchagator.lunch.provider.ZomatoService;

import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RequestMapping("menu")
@RestController
public class MyController {

    @Autowired
    private GattoMattoMenuProvider provider;


    @GetMapping("dbg")
    public String foo() {
        return "Foo!";
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        return Collections.singleton(provider.findMenuFor());
    }
}
