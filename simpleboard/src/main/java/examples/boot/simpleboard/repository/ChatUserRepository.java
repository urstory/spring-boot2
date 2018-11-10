package examples.boot.simpleboard.repository;

import examples.boot.simpleboard.base.JpaQueryDslPredicateRepository;
import examples.boot.simpleboard.domain.ChatUser;

public interface ChatUserRepository
        extends JpaQueryDslPredicateRepository<ChatUser, Long> {
}
