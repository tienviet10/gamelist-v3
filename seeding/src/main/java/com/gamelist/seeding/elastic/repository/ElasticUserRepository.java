package com.gamelist.seeding.elastic.repository;

import com.gamelist.seeding.elastic.modal.*;
import org.springframework.data.elasticsearch.repository.*;

import java.util.*;

public interface ElasticUserRepository extends ElasticsearchRepository<ElasticUser, String> {

    List<ElasticUser> findByUsername(String username);
}
