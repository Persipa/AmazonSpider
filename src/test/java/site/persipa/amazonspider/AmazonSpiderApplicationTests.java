package site.persipa.amazonspider;

import org.junit.jupiter.api.Test;
import site.persipa.amazonspider.enums.UserAgentEnum;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AmazonSpiderApplicationTests {

    @Test
    void contextLoads() {
        String s = UserAgentEnum.randomUA();
        System.out.println(s);
    }

    @Test
    public void test() {
        String str = "111";
        String p = "^\\d*$";
        Pattern pattern = Pattern.compile(p);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            System.out.println(false);
        }
    }

    @Test
    public void testUriParse() {
        String s = "/b/461-3126843-2204829?_encoding=UTF8&node=1892374071&pf_rd_i=116169071&pf_rd_m=A1AJ19PSB66TGU&pf_rd_p=b5b56065-36aa-458b-8fa1-11e78972adc2&pf_rd_r=FBK4PX2FPPHGTJNP6TQE&pf_rd_s=tcg-slide-5&pf_rd_t=101&ref_=ch_auto_pc_slides";
        URI uri = URI.create(s);
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int index = pair.indexOf("=");
            String key = index > 0 ? pair.substring(0, index) : pair;
            String value = index > 0 && pair.length() > index + 1 ? pair.substring(index + 1) : null;
        }
        System.out.println(query);
    }



}
