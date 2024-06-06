package ra.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ra.jpa.entity.Role;
import ra.jpa.entity.RoleName;
import ra.jpa.entity.User;
import ra.jpa.repository.UserRepository;

import java.util.Date;
import java.util.HashSet;

@SpringBootApplication
public class JpaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
    }
    //    @Bean
    //    public CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder){
    //        return args -> {
    //            Role admin = new Role(null, RoleName.ROLE_ADMIN);
    //            Role user = new Role(null, RoleName.ROLE_USER);
    //            Role man =  new Role(null, RoleName.ROLE_MANAGER);
    //
    //
    //            User user1 = new User(null,"hunghx","hung@gmail.com",passwordEncoder.encode("123456"),"0938874575",new Date(),true,"Hung Hò",false,false,new HashSet<>());
    //            user1.getRoleSet().add(user);
    //            User user2 = new User(null,"admin","admin@gmail.com",passwordEncoder.encode("123456"),"093654575",new Date(),true,"admin",false,false,new HashSet<>());
    //            user2.getRoleSet().add(admin);
    //            user2.getRoleSet().add(man);
    //
    //            userRepository.save(user1);
    //            userRepository.save(user2);
    //        };
    //    }

}
