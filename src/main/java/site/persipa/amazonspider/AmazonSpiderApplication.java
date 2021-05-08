package site.persipa.amazonspider;

import com.alibaba.excel.EasyExcel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.persipa.amazonspider.controller.MainController;
import site.persipa.amazonspider.vo.Book;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class AmazonSpiderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AmazonSpiderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MainController mainController = new MainController();
        Map<String, String> navbarUrlMap = mainController.getNavbarUrl();

        Scanner scanner = new Scanner(System.in);
        System.out.println("输入一个活动：");
        String input = null;
        input = scanner.nextLine();
        String node = navbarUrlMap.get(input);
        if (node == null) {
            System.out.println("没有这个活动");
            return;
        }

        List<Book> bookList = mainController.getBookList(node);
        String filePath = "C:\\Users\\WDZN\\Downloads\\logs\\amazon.xlsx";
        File file = new File(filePath);
        EasyExcel.write(file, Book.class)
                .sheet(0)
                .doWrite(bookList);
        System.out.println("Write Success.");
    }
}
