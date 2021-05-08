package site.persipa.amazonspider.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;

/**
 * @author xukunhuang
 * @since 2021/5/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @ExcelProperty(index = 0, value = "书名")
    private String bookName;

    @ExcelProperty(index = 1, value = "地址")
    private String bookHref;

    @ExcelProperty(index = 2, value = "作者和编撰时间")
    private String author;

    @ExcelProperty(index = 3, value = "原价")
    private String price;

    @ExcelProperty(index = 4, value = "现价")
    private String discountPrice;

}
