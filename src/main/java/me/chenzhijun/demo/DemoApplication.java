package me.chenzhijun.demo;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class DemoApplication {

    @Autowired
    ElasticsearchRepository elasticsearchRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/notice")
    public Notice saveNotice(@RequestBody Notice notice) {
        Notice save = (Notice) elasticsearchRepository.save(notice);
        return save;
    }

    @PostMapping(value = "/notice/query")
    public List<Notice> queryList(@RequestBody Notice notice) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("name", notice.getName()));
        System.err.println("query:" + queryBuilder);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withPageable(pageable)
                .withQuery(queryBuilder).build();

        System.err.println("searchQuery:" + searchQuery.getQuery());
        Iterable<Notice> search = elasticsearchRepository.search(searchQuery);
        List<Notice> list = new ArrayList<>();
        search.forEach(obj -> {
            System.err.println(obj);
            list.add(obj);
        });
        return list;


    }
}
