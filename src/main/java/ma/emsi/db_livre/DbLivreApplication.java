package ma.emsi.db_livre;

import ma.emsi.db_livre.entities.Exposant;
import ma.emsi.db_livre.entities.Livre;
import ma.emsi.db_livre.repositories.ExposantRepository;
import ma.emsi.db_livre.repositories.LivreRepository;
import ma.emsi.db_livre.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class DbLivreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbLivreApplication.class, args);
    }


    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!jdbcUserDetailsManager.userExists("user")) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user").password(passwordEncoder.encode("1234")).roles("USER").build()
                );
            }
            if (!jdbcUserDetailsManager.userExists("exposant")) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("exposant").password(passwordEncoder.encode("1234")).roles("USER", "EXPOSANT").build()
                );
            }
            if (!jdbcUserDetailsManager.userExists("admin")) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "EXPOSANT", "ADMIN").build()
                );
            }
            System.out.println("CommandLineRunner commandLineRunner");
        };
    }


    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("EXPOSANT");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user","1234","user1@gmail.com","1234");
            accountService.addNewUser("exposant","1234","exposant1@gmail.com","1234");
            accountService.addNewUser("admin","1234","admin@gmail1.com","1234");

            accountService.addRoleToUser("user","USER");
            accountService.addRoleToUser("exposant","USER");
            accountService.addRoleToUser("exposant","EXPOSANT");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","EXPOSANT");
            accountService.addRoleToUser("admin","ADMIN");
            System.out.println("CommandLineRunnerUserDetails commandLineRunner");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
