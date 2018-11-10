package examples.boot.simpleboard.repository;

import examples.boot.simpleboard.base.JpaQueryDslPredicateRepository;
import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.repository.custom.BoardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository
        extends JpaQueryDslPredicateRepository<Board, Long>, BoardRepositoryCustom {
    @Query("SELECT COUNT(b) FROM Board b")
    public Long countAll();

    @Query("SELECT COUNT(b) FROM Board b WHERE b.user.name = :name")
    public Long countAllByUserName(@Param("name") String name);

    public Long countByTitleContains(String title);

    @Query("SELECT COUNT(b) FROM Board b WHERE b.content like concat('%',:keyword,'%')")
    public Long countByContentContains(@Param("keyword") String keyword);

    public Page<Board> findAllBy(Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.user.name = :name")
    public Page<Board> findAllByUserName(@Param("name") String name, Pageable pageable);

    public Page<Board> findAllByTitleContains(String title, Pageable pageable);

    public Page<Board> findAllByContentContains(String content, Pageable pageable);
}