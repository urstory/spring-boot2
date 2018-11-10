package examples.boot.simpleboard.repository;

import examples.boot.simpleboard.base.JpaQueryDslPredicateRepository;
import examples.boot.simpleboard.domain.UserConnection;

// @Repository를 붙이지 않아도 Repository로 등록된다.
public interface UserConnectionRepository
        extends JpaQueryDslPredicateRepository<UserConnection, Long> {

}
