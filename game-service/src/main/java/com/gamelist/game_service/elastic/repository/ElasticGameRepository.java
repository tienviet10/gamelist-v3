package com.gamelist.game_service.elastic.repository;

import com.gamelist.game_service.elastic.modal.*;
import java.util.*;
import org.springframework.data.elasticsearch.repository.*;

public interface ElasticGameRepository extends ElasticsearchRepository<ElasticGame, String> {

    List<ElasticGame> findByName(String name);
}
