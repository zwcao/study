package com.weston.study.boot.elasticsearch.starter;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface PersonRepository extends ElasticsearchRepository<Person,Long> {

}
