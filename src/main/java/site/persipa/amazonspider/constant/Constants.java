package site.persipa.amazonspider.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xukunhuang
 * @since 2021/5/8
 */
public class Constants {

   public static final Map<String, String> header = new HashMap<>();
    static {
        header.put("Host", "www.amazon.cn");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
    }
}
