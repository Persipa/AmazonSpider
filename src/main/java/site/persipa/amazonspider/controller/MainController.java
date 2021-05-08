package site.persipa.amazonspider.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.persipa.amazonspider.vo.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xukunhuang
 * @since 2021/5/4
 */
public class MainController {

    public List<Book> getBookList(String url) throws IOException {
        url = "https://www.amazon.cn/s?rh=n%3A2334070071&fs=true";
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Host", "www.amazon.cn")
                .get();
        String title = document.title();
        System.out.println(title);
        Elements elements = document.getElementsByAttributeValue("data-component-type", "s-search-result");
        List<Book> list = new ArrayList<>();
        for (Element element : elements) {
            Elements sgRowElements = element.getElementsByClass("sg-row");

            Element content = sgRowElements.get(1);
            Elements imageElement = content.getElementsByAttributeValue("data-component-type", "s-product-image");
            Elements imageHref = imageElement.first().getElementsByTag("img").first().getElementsByAttribute("href");
            System.out.println("--------------------");
            System.out.println(element);
            Element h2 = content.getElementsByTag("h2").first();
            String bookName = h2.text();
            String bookHref = h2.getElementsByTag("a").first().attr("href");

            String author = h2.nextElementSibling().text();

            // 原价
            Element priceElement = content.getElementsByClass("sg-row").last();
            String priceSymbol = priceElement.getElementsByClass("a-price-symbol").text();
            String priceWhole = priceElement.getElementsByClass("a-price-whole").text();
            String priceFraction = priceElement.getElementsByClass("a-price-fraction").text();
            String price = priceSymbol + priceWhole + priceFraction;

            // 限时折扣价格
            String discountPrice = priceElement.getElementsByClass("a-color-secondary").first().text();

            Book book = new Book(bookName, bookHref, author, price, discountPrice);
            list.add(book);
        }

        return list;
    }

    public void getNavbarUrl() throws IOException {
        String url = "https://www.amazon.cn/Kindle%E7%94%B5%E5%AD%90%E4%B9%A6/b/node=116169071";
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Host", "www.amazon.cn")
                .get();
        Element tagElement = document.getElementById("cc-lm-tcgShowContainer");
        Elements imageElements = tagElement.getElementsByClass("cc-lm-tcgImgItem");
        List<String> hrefList = imageElements.stream()
                .map(e -> e.getElementsByTag("a").attr("href"))
                .collect(Collectors.toList());

        List<String> titleList = tagElement.getElementById("cc-lm-navItems")
                .getElementsByTag("li")
                .stream().map(Element::text)
                .collect(Collectors.toList());
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < titleList.size()-1; i++) {
            map.put(titleList.get(i), hrefList.get(i));
        }
        map.forEach((k,v)->System.out.println(k+"===>"+v));
    }

}
