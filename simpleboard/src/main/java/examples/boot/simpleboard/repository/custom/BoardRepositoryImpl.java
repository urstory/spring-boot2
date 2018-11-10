package examples.boot.simpleboard.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.QBoard;
import examples.boot.simpleboard.domain.QUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    public BoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Board getBoardByDSL(Long id) {
        Query query = entityManager.createQuery("select b from Board b where id = :id")
                .setParameter("id", id);

//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//////
//////        QBoard qBoard = QBoard.board;
//////        Board board = queryFactory.selectFrom(qBoard)
//////                .where(qBoard.id.eq(id))
//////                .fetchOne();



        // "select b from Board b where b.id = :id" - jpql
        return (Board)query.getSingleResult();
    }


    @Override
    public Board getBoardFetchJoinByDSL(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QBoard qBoard = QBoard.board;
        QUser qUser = QUser.user;
        Board board = queryFactory.selectFrom(qBoard).innerJoin(qUser)
                .on(qBoard.user.id.eq(qUser.id))
                .where(qBoard.id.eq(id))
                .fetchOne();

        return board;
    }

    @Override
    public List<Board> getBoards(String title) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QBoard qBoard = QBoard.board;
        JPAQuery<Board> boardJPAQuery = queryFactory.selectFrom(qBoard);
        if(title != null){
            boardJPAQuery.where(qBoard.title.contains(title));
        }
        return boardJPAQuery.fetch();
    }
}
