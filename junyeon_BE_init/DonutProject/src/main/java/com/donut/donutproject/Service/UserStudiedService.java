package com.donut.donutproject.Service;

import com.donut.donutproject.Dto.UserStudiedRequestDto;
import com.donut.donutproject.Dto.UserStudiedResponseDto;
import com.donut.donutproject.Entity.UserStudiedData;
import com.donut.donutproject.Entity.Users;
import com.donut.donutproject.Repository.UserStudiedDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStudiedService {

    private final UserStudiedDataRepository studiedDataRepository;

    public UserStudiedService(UserStudiedDataRepository studiedDataRepository) {
        this.studiedDataRepository = studiedDataRepository;
    }

    // 문제 저장
    public UserStudiedResponseDto saveProblem(UserStudiedRequestDto userStudiedRequestDto) { // Users user 제거
        UserStudiedData userStudiedData = new UserStudiedData(userStudiedRequestDto);

        UserStudiedData savedUserStudiedData = studiedDataRepository.save(userStudiedData);

        UserStudiedResponseDto userStudiedResponseDto = new UserStudiedResponseDto(savedUserStudiedData);

        return userStudiedResponseDto;
    }


    // 특정 사용자의 문제 풀이 기록 조회
    public List<UserStudiedResponseDto> getSolvedProblems(Long userId) {
       return studiedDataRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream().map(UserStudiedResponseDto::new).toList();
    }
}
