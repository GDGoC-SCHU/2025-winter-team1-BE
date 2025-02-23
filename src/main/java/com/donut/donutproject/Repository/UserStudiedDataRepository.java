package com.donut.donutproject.Repository;

import com.donut.donutproject.Entity.UserStudiedData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStudiedDataRepository extends JpaRepository<UserStudiedData, Long> {
    List<UserStudiedData> findAllByUsernameOrderByModifiedAtDesc(String username);
    Optional<UserStudiedData> findByProblemIdAndUsername(Long id, String username);
}
