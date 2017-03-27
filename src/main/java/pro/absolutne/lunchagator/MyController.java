package pro.absolutne.lunchagator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchagator.lunch.DailyMenu;
import pro.absolutne.lunchagator.lunch.MenuProvider;
import pro.absolutne.lunchagator.lunch.provider.GattoMattoMenuProvider;
import pro.absolutne.lunchagator.lunch.provider.ZomatoService;

import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RequestMapping("menu")
@RestController
public class MyController {

    private final MenuProvider provider = new GattoMattoMenuProvider();

    @Autowired
    private ZomatoService zs;


    @GetMapping("dbg")
    public String foo() {
        return "Foo!";
    }

    @GetMapping("go")
    public String go() {
        zs.get();
        return "done";
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        return Collections.singleton(provider.findMenuFor());
    }
}
