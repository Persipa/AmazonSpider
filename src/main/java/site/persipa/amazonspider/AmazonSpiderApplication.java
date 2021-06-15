package site.persipa.amazonspider;

import com.alibaba.excel.EasyExcel;
import org.jline.terminal.Size;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.jansi.win.JansiWinSysTerminal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.persipa.amazonspider.controller.Spider;
import site.persipa.amazonspider.utils.ProgressBarUtil;
import site.persipa.amazonspider.vo.Book;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class AmazonSpiderApplication implements CommandLineRunner {

   private static final String EXIT_CODE = "e";
   private static final String REFRESH_CODE = "r";
   private static final String PATTERN_STR = "^\\d$";

  private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);



    public static void main(String[] args) {
        SpringApplication.run(AmazonSpiderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Spider mainController = new Spider();

        for (int i = 0; i <= 100; i++) {
            double random = Math.random();
            i += Math.round(random * 10);
            if (i > 100) {
                i = 100;
            }
            ProgressBarUtil.print(i);
            Thread.sleep(2000);
        }

        String input;
        do {
            Map<Integer, String> navbarUrlMap = mainController.getNavbarUrl();
            Scanner scanner = new Scanner(System.in);
            System.out.println("(r) 刷新");
            System.out.println("(e) 退出");
            System.out.println("输入一个活动/功能（数字/e）：");
            input = scanner.nextLine();
            if (EXIT_CODE.equals(input)) {
                System.exit(0);
            }
            if (REFRESH_CODE.equals(input)) {
                continue;
            }
            Matcher isNum = PATTERN.matcher(input);
            if (!isNum.matches()) {
                System.out.println("Input Error.");
                continue;
            }
            String node = navbarUrlMap.get(Integer.parseInt(input));
            if (node == null) {
                System.out.println("没有这个活动");
                continue;
            }

            List<Book> bookList = mainController.getBookList(node);
            String filePath = "C:\\Users\\WDZN\\Downloads\\logs\\amazon.xlsx";
            File file = new File(filePath);
            EasyExcel.write(file, Book.class)
                    .sheet(0)
                    .doWrite(bookList);
            System.out.println("Write Success.");
        } while (REFRESH_CODE.equals(input));
    }
}
