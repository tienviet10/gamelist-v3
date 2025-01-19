package com.gamelist.seeding.elastic.repository;

import com.gamelist.seeding.elastic.modal.*;
import org.springframework.data.elasticsearch.repository.*;

import java.util.*;

public interface ElasticGameRepository extends ElasticsearchRepository<ElasticGame, String> {

    List<ElasticGame> findByName(String name);
}
