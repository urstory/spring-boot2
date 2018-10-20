package examples.boot.myshop.repository;

import examples.boot.myshop.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAllByName(String name);
    public List<Board> findAllByTitleContains(String title);

    @Query("select distinct b from Board b join fetch b.category")
    public List<Board> getBoards();
}
