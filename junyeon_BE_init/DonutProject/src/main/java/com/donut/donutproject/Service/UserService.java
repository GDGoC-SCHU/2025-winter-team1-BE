package com.donut.donutproject.Service;
import com.donut.donutproject.Dto.AddUserRequest;
import com.donut.donutproject.Entity.Users;
import com.donut.donutproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest request) {
        return userRepository.save(Users.builder()
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build()).getId();
    }

    public Users findByUserId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}