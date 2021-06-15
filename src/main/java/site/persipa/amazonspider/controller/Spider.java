package site.persipa.amazonspider.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.persipa.amazonspider.constant.Constants;
import site.persipa.amazonspider.enums.UserAgentEnum;
import site.persipa.amazonspider.utils.JsoupUtil;
import site.persipa.amazonspider.vo.Book;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xukunhuang
 * @since 2021/5/4
 */
public class Spider {

    public List<Book> getBookList(String url) throws IOException {
        url = "https://www.amazon.cn/s?rh=n%3A" + url + "&fs=true";
        Document document = JsoupUtil.getDocument(url);

        // 该活动的书有多少页
        Element pageBar = document.getElementsByClass("a-pagination").first();
        String pageStr = pageBar.getElementsByTag("li").last().previousElementSibling().text();
        int pages = Integer.parseInt(pageStr);
        System.out.println("There are " + pages + " pages");

        List<Book> bookList = new ArrayList<>();
        for (int i = 1; i < pages; i++) {
            url = url + "&page=" + i;
            List<Book> tmpList = parseDocument(JsoupUtil.getDocument(url));
            bookList.addAll(tmpList);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bookList = bookList.stream()
                .distinct().collect(Collectors.toList());

        return bookList;
    }

    public Map<Integer, String> getNavbarUrl() throws IOException {
        String url = "https://www.amazon.cn/Kindle%E7%94%B5%E5%AD%90%E4%B9%A6/b/node=116169071";
        Document document = Jsoup.connect(url)
                .userAgent(UserAgentEnum.randomUA())
                .headers(Constants.header)
                .get();
        Element tagElement = document.getElementById("cc-lm-tcgShowContainer");
        Elements imageElements = tagElement.getElementsByClass("cc-lm-tcgImgItem");
        List<String> hrefList = imageElements.stream()
                .map(e -> e.getElementsByTag("a").attr("href"))
                .map(URI::create)
                .map(URI::getQuery)
                .map(q -> Arrays.stream(q.split("&"))
                        .filter(s -> s.startsWith("node="))
                        .map(s -> s.substring(s.indexOf("=") + 1))
                        .findFirst()
                        .orElse(""))
                .collect(Collectors.toList());

        List<String> titleList = tagElement.getElementById("cc-lm-navItems")
                .getElementsByTag("li")
                .stream().map(Element::text)
                .collect(Collectors.toList());
        Map<Integer, String> map = new HashMap<>(titleList.size());
        for (int i = 0; i < titleList.size() - 1; i++) {
            String title = titleList.get(i);
            String value = hrefList.get(i);
            map.put(i, value);
            System.out.println("(" + i + ") " + title + "==>" + value);
        }
        return map;
    }

    private List<Book> parseDocument(Document document) {
        Elements elements = document.getElementsByAttributeValue("data-component-type", "s-search-result");
        List<Book> list = new ArrayList<>();
        for (Element element : elements) {
            Elements sgRowElements = element.getElementsByClass("sg-row");

            Element content = sgRowElements.get(1);
            Elements imageElement = content.getElementsByAttributeValue("data-component-type", "s-product-image");
            Elements imageHref = imageElement.first().getElementsByTag("img").first().getElementsByAttribute("href");

            Element bookTitle = content.getElementsByTag("h2").first();
            String bookName = bookTitle.text();
            String bookHref = bookTitle.getElementsByTag("a").first().attr("href");
            String author = bookTitle.nextElementSibling().text();

            // 原价
            Element priceElement = content.getElementsByClass("sg-row").last();
            String priceSymbol = priceElement.getElementsByClass("a-price-symbol").text();
            String priceWhole = priceElement.getElementsByClass("a-price-whole").text();
            String priceFraction = priceElement.getElementsByClass("a-price-fraction").text();
            String price = priceSymbol + priceWhole + priceFraction;

            // todo 限时折扣价格 可能不准
            String discountPrice = priceElement.getElementsByClass("a-color-secondary").first().text();

            Book book = new Book(bookName, bookHref, author, price, discountPrice);
            list.add(book);
        }

        return list;
    }

}
