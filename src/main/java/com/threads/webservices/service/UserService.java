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

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<UserResponse>

    public User createRequest(UserCreationRequest request){

        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .nickname(request.getNickname().isEmpty() ? request.getUsername() : request.getNickname())
                .dob(LocalDate.now().minusYears(18))
                .imageUrl(request.getImageUrl().isEmpty() ? "" : request.getImageUrl())
                .biography(request.getBiography().isEmpty()? "" : request.getBiography())
                .build();


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

    public void initialUsers() {
        List<UserCreationRequest> userCreationRequests = new ArrayList<>();

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Margeaux Mar")
                .username("mmar0")
                .password("user")
                .imageUrl("http://dummyimage.com/191x100.png/5fa2dd/ffffff")
                .biography("Bitten by cat, sequela")
                .nickname("Mar")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Stacee Benoit")
                .username("sbenoit1")
                .password("user")
                .imageUrl("http://dummyimage.com/185x100.png/dddddd/000000")
                .biography("Unspecified fracture of unspecified toe(s), subsequent encounter for fracture with delayed healing")
                .nickname("Benoit")
                .build());


        userCreationRequests.add(UserCreationRequest.builder()
                .name("Vinnie Rennocks")
                .username("vrennocks2")
                .password("user")
                .imageUrl("http://dummyimage.com/126x100.png/ff4444/ffffff")
                .biography("Unspecified obstruction of Eustachian tube, bilateral")
                .nickname("Rennocks")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Tootsie Straughan")
                .username("tstraughan3")
                .password("user")
                .imageUrl("http://dummyimage.com/139x100.png/cc0000/ffffff")
                .biography("Pathological fracture, right finger(s), subsequent encounter for fracture with delayed healing")
                .nickname("Straughan")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Juliann Reedie")
                .username("jreedie4")
                .password("user")
                .imageUrl("http://dummyimage.com/137x100.png/dddddd/000000")
                .biography("Nondisplaced oblique fracture of shaft of right tibia, subsequent encounter for closed fracture with malunion")
                .nickname("Reedie")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Aubrie Korba")
                .username("akorba5")
                .password("user")
                .imageUrl("http://dummyimage.com/176x100.png/ff4444/ffffff")
                .biography("Other injury due to other accident on board (nonpowered) inflatable craft")
                .nickname("Korba")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Skelly Watting")
                .username("swatting6")
                .password("user")
                .imageUrl("http://dummyimage.com/210x100.png/cc0000/ffffff")
                .biography("Complete traumatic amputation of two or more right lesser toes")
                .nickname("Watting")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Shela Shill")
                .username("sshill7")
                .password("user")
                .imageUrl("http://dummyimage.com/193x100.png/ff4444/ffffff")
                .biography("Other shellfish poisoning, undetermined, subsequent encounter")
                .nickname("Shill")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Eudora Canas")
                .username("ecanas8")
                .password("user")
                .imageUrl("http://dummyimage.com/110x100.png/cc0000/ffffff")
                .biography("Malignant neoplasm of unspecified part of right adrenal gland")
                .nickname("Canas")
                .build());

        userCreationRequests.add(UserCreationRequest.builder()
                .name("Bernadina Densham")
                .username("bdensham9")
                .password("user")
                .imageUrl("http://dummyimage.com/245x100.png/ff4444/ffffff")
                .biography("Unspecified physeal fracture of phalanx of right toe, subsequent encounter for fracture with routine healing")
                .nickname("Densham")
                .build());

        int i = 1;
        for(UserCreationRequest user : userCreationRequests) {

            User userCreate = createRequest(user);

            System.setProperty("INITIAL_USER_ID" + i, userCreate.getId());
            i++;
        }
    }
}
