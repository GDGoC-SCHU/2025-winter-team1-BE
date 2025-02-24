package com.donut.donutproject.Service;

import com.donut.donutproject.Dto.ProblemRequestDto;
import com.donut.donutproject.Dto.UserStudiedResponseDto;
import com.donut.donutproject.Entity.UserStudiedData;
import com.donut.donutproject.Repository.UserStudiedDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStudiedService {

    private final UserStudiedDataRepository studiedDataRepository;

    public UserStudiedService(UserStudiedDataRepository studiedDataRepository) {
        this.studiedDataRepository = studiedDataRepository;
    }

    public List<UserStudiedResponseDto> getSolvedProblems(String username) {
        return studiedDataRepository.findAllByUsernameOrderByModifiedAtDesc(username)
                .stream()
                .map(UserStudiedResponseDto::new)
                .collect(Collectors.toList());
    }
}