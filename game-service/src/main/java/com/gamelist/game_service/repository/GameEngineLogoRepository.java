package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface GameEngineLogoRepository extends JpaRepository<GameEngineLogo, Long> {

}
