package com.donut.donutproject.Repository;

import com.donut.donutproject.Entity.UserStudiedData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStudiedDataRepository extends JpaRepository<UserStudiedData, Long> {
    List<UserStudiedData> findByUserId(Long userId);

    List<UserStudiedData> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
