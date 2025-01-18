package com.gamelist.game_service.elastic.repository;

import com.gamelist.game_service.elastic.modal.*;
import org.springframework.data.elasticsearch.repository.*;

import java.util.*;

public interface ElasticUserRepository extends ElasticsearchRepository<ElasticUser, String> {

    List<ElasticUser> findByUsername(String username);
}
