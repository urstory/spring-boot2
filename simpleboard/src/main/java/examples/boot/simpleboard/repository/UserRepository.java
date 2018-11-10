package examples.boot.simpleboard.repository;

import examples.boot.simpleboard.base.JpaQueryDslPredicateRepository;
import examples.boot.simpleboard.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// @Repository를 붙이지 않아도 Repository로 등록된다.
public interface UserRepository
        extends JpaQueryDslPredicateRepository<User, Long> {

    public User findUserByEmail(String email);

    @Query("SELECT u FROM User u join fetch u.roles")
    public List<User> getAllUsers();

    @Query("SELECT uc.user FROM UserConnection uc WHERE uc.providerId = :type and uc.providerUserId = :providerUserId")
    public User getSocialUser(@Param("type") String type,@Param("providerUserId") String providerUserId);
}
