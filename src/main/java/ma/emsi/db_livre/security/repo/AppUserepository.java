package ma.emsi.db_livre.security.repo;

import ma.emsi.db_livre.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);
}
