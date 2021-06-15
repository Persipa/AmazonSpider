package site.persipa.amazonspider.utils;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

/**
 * @author xukunhuang
 * @since 2021/6/15
 */
public class ProgressBarUtil {

    static Terminal terminal;
    static {
        try {
            terminal = TerminalBuilder.terminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印出一个进度条来（100为总进度）
     *
     * @param curr 当前进度
     */
    public static void print(Integer curr) {
        print(curr, 100);
    }

    /**
     * 打印出一个进度条来
     *
     * @param curr  当前的进度
     * @param total 总进度
     */
    public static void print(Integer curr, Integer total) {
        char e = '=';
        double percent = curr.doubleValue() / total.doubleValue();
        int totalWidth = (int) Math.floor(terminal.getWidth() * 0.8);
        if (totalWidth == 0) {
            printWithoutBar(curr, total);
            return;
        }
        int eTotalCount = totalWidth - 7;
        int eCount = (int) Math.floor(eTotalCount * percent);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < eCount; i++) {
            stringBuilder.append(e);
        }
        String s = stringBuilder.toString();
        int rightSpace = eTotalCount - eCount;
        if (rightSpace == 0) {
            System.out.printf("\r[%s] %d%%\n", s, curr);
        } else {
            System.out.printf("\r[%s%-" + rightSpace + "s] %d%%", s, "", curr);
        }
    }

    /**
     * 打印出进度来
     *
     * @param curr  当前进度
     * @param total 总进度
     */
    public static void printWithoutBar(Integer curr, Integer total) {
        int percent = curr * 100 / total;
        if (percent < 100) {
            System.out.printf("%d%%\r", percent);

        } else {
            System.out.print("100%\n");
        }
    }
}
