package site.persipa.amazonspider;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.persipa.amazonspider.controller.MainController;

import java.io.IOException;

@SpringBootTest
class AmazonSpiderApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test() throws IOException {
        new MainController().getNavbarUrl();

    }

}
