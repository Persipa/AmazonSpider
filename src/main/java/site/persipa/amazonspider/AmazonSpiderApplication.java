package site.persipa.amazonspider;

import com.alibaba.excel.EasyExcel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.persipa.amazonspider.controller.MainController;
import site.persipa.amazonspider.vo.Book;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class AmazonSpiderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AmazonSpiderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Book> bookList = new MainController().getBookList("");
        String filePath = "C:\\Users\\WDZN\\Downloads\\logs\\amazon.xlsx";
        File file = new File(filePath);
        EasyExcel.write(file, Book.class)
                .sheet(0)
                .doWrite(bookList);
    }
}
