package ma.emsi.db_livre.security.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import ma.emsi.db_livre.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private AccountService accountService;
    private EntityManager entityManager; // Inject EntityManager

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = findByUsernameWithRoles(username);

        if(appUser == null) throw new UsernameNotFoundException(String.format("User %s not found",username));

        String[] roles = appUser.getRoles().stream().map(u -> u.getRole()).toArray(String[]::new);
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
        return userDetails;
    }

    private AppUser findByUsernameWithRoles(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUser> query = cb.createQuery(AppUser.class);
        Root<AppUser> root = query.from(AppUser.class);
        root.fetch("roles", JoinType.LEFT); // Fetch the roles eagerly
        query.select(root).where(cb.equal(root.get("username"), username));
        TypedQuery<AppUser> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }
}
