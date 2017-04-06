package pro.absolutne;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.absolutne.lunchgator.LunchgatorApplication;

// TODO setup db
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LunchgatorApplication.class)
public class LunchgatorApplicationTests {

	@Test
	public void contextLoads() {
	}

}
