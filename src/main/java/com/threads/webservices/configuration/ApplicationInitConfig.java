package com.threads.webservices.configuration;

import com.threads.webservices.dto.request.UserCreationRequest;
import com.threads.webservices.entity.User;
import com.threads.webservices.enums.Role;
import com.threads.webservices.repository.UserRepository;
import com.threads.webservices.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    private final Flyway flyway;

    @Value("${spring.flyway.locations}")
    String[] flywayLocations;

    @Autowired
    DataSource dataSource;

    @Bean
    ApplicationRunner applicationRunner(UserService userService, UserRepository userRepository){
        // Kiem tra va them user Admin neu chua them
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it!");
            }

            if(userRepository.findByUsername("user").isEmpty()){
                var roles = new CopyOnWriteArraySet<String>();
                roles.add(Role.USER.name());

                User user = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("USER user has been created with default password: admin, please change it!");


            List<UserCreationRequest> userCreationRequests = new ArrayList<>();

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Margeaux Mar")
                    .username("mmar0")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Bitten by cat, sequela")
                    .nickname("Mar")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Stacee Benoit")
                    .username("sbenoit1")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Unspecified fracture of unspecified toe(s), subsequent encounter for fracture with delayed healing")
                    .nickname("Benoit")
                    .build());


            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Vinnie Rennocks")
                    .username("vrennocks2")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Unspecified obstruction of Eustachian tube, bilateral")
                    .nickname("Rennocks")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Tootsie Straughan")
                    .username("tstraughan3")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Pathological fracture, right finger(s), subsequent encounter for fracture with delayed healing")
                    .nickname("Straughan")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Juliann Reedie")
                    .username("jreedie4")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Nondisplaced oblique fracture of shaft of right tibia, subsequent encounter for closed fracture with malunion")
                    .nickname("Reedie")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Aubrie Korba")
                    .username("akorba5")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Other injury due to other accident on board (nonpowered) inflatable craft")
                    .nickname("Korba")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Skelly Watting")
                    .username("swatting6")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Complete traumatic amputation of two or more right lesser toes")
                    .nickname("Watting")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Shela Shill")
                    .username("sshill7")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Other shellfish poisoning, undetermined, subsequent encounter")
                    .nickname("Shill")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Eudora Canas")
                    .username("ecanas8")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Malignant neoplasm of unspecified part of right adrenal gland")
                    .nickname("Canas")
                    .build());

            userCreationRequests.add(UserCreationRequest.builder()
                    .name("Bernadina Densham")
                    .username("bdensham9")
                    .password("user")
                    .imageUrl("f2ecf790-ac64-4ec8-bc44-a03a701b8f3e_173996873185200.jpg")
                    .biography("Unspecified physeal fracture of phalanx of right toe, subsequent encounter for fracture with routine healing")
                    .nickname("Densham")
                    .build());

            List<User> users = new ArrayList<>();
            for(UserCreationRequest userRequest : userCreationRequests) {
                User userCreate = userService.createRequest(userRequest);
                users.add(userCreate);
            }

            Flyway myFlyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations(flywayLocations)
                    .baselineOnMigrate(true)//default baseline is V1
                    .baselineVersion("0")
                    .placeholders(
                            Map.of(
                                    "initial_user_id1", users.get(0).getId(),
                                    "initial_user_id2", users.get(1).getId(),
                                    "initial_user_id3", users.get(2).getId(),
                                    "initial_user_id4", users.get(3).getId(),
                                    "initial_user_id5", users.get(4).getId(),
                                    "initial_user_id6", users.get(5).getId(),
                                    "initial_user_id7", users.get(6).getId(),
                                    "initial_user_id8", users.get(7).getId(),
                                    "initial_user_id9", users.get(8).getId(),
                                    "initial_user_id10", users.get(9).getId()
                            )
                    )
                    .load();
            myFlyway.migrate();
            } else {
                flyway.migrate();
            }
        };
    }
}
