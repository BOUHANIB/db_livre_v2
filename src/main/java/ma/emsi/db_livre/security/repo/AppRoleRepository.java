package ma.emsi.db_livre.security.repo;

import ma.emsi.db_livre.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
