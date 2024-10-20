package com.threads.webservices.service;

import com.threads.webservices.dto.request.UserCreationRequest;
import com.threads.webservices.dto.request.UserUpdateRequest;
import com.threads.webservices.dto.response.UserResponse;
import com.threads.webservices.entity.User;
import com.threads.webservices.enums.Role;
import com.threads.webservices.exception.AppException;
import com.threads.webservices.exception.ErrorCode;
import com.threads.webservices.mapper.UserMapper;
import com.threads.webservices.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createRequest(UserCreationRequest request){

        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        //Get thong tin username bang context holder tu jwt o bearer token
        //tuc la thong tin cua user dang dang nhap co jwt

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return UserResponse.fromUser(user);

    }

    @PreAuthorize("hasRole('ADMIN')")// neu la ADMIN cho phep goi method nguoc lai khong goi method
    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        return userRepository.findAll().stream().map(UserResponse::fromUser).toList();
    }

    // goi method truoc roi kiem tra, neu id va jwt la user dang dang nhap thi return ket qua
    // du dung hay sai thi method van duoc goi
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id){
        log.info("In method get user by id");
        return UserResponse.fromUser(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found!"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserResponse.fromUser(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
