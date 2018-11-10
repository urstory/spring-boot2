package examples.boot.simpleboard.repository;

import examples.boot.simpleboard.base.JpaQueryDslPredicateRepository;
import examples.boot.simpleboard.domain.ChatRoom;
import examples.boot.simpleboard.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository
        extends JpaQueryDslPredicateRepository<ChatRoom, Long> {
}
