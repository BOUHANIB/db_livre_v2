package ma.emsi.db_livre.security.service;

import ma.emsi.db_livre.security.entities.AppRole;
import ma.emsi.db_livre.security.entities.AppUser;

public interface AccountService {

    AppUser addNewUser(String username, String password,String email,String confirmPassword);
    AppRole addNewRole(String role);

    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);

    AppUser loadUserByUser(String username);
}
