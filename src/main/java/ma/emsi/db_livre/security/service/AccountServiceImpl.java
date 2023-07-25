package ma.emsi.db_livre.security.service;

import lombok.AllArgsConstructor;
import ma.emsi.db_livre.security.entities.AppRole;
import ma.emsi.db_livre.security.entities.AppUser;
import ma.emsi.db_livre.security.repo.AppRoleRepository;
import ma.emsi.db_livre.security.repo.AppUserepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AppUserepository appUseRepository;
    private AppRoleRepository appRoleRepository;

    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUseRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("this user already exist");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
        appUser = AppUser.builder().
                userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser savedAppUser = appUseRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole!=null)  throw new RuntimeException("This role already exist");
        appRole=AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUseRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUseRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUser(String username) {
        return appUseRepository.findByUsername(username);
    }
}
