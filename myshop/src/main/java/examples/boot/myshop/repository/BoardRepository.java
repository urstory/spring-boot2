package examples.boot.myshop.repository;

import examples.boot.myshop.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
