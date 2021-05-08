package site.persipa.amazonspider.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import site.persipa.amazonspider.constant.Constants;
import site.persipa.amazonspider.enums.UserAgentEnum;

import java.io.IOException;

/**
 * @author xukunhuang
 * @since 2021/5/8
 */
public class JsoupUtil {

    public static Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent(UserAgentEnum.randomUA())
                .headers(Constants.header)
                .get();
    }
}
