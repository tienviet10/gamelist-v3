package com.gamelist.game_service.elastic.repository;

import com.gamelist.game_service.elastic.modal.*;
import java.util.*;
import org.springframework.data.elasticsearch.repository.*;

public interface ElasticUserRepository extends ElasticsearchRepository<ElasticUser, String> {

    List<ElasticUser> findByUsername(String username);
}
