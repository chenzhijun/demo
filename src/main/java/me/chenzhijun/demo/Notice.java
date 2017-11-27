package me.chenzhijun.demo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "idx_notice", type = "type_notice")
@Data
@ToString
public class Notice {
    private String id;
    private String name;
    private String type;
    private String author;
    private String body;
}
