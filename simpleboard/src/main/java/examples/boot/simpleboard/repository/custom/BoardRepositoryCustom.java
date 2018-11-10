package examples.boot.simpleboard.repository.custom;

import examples.boot.simpleboard.domain.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    public Board getBoardByDSL(Long id);
    public Board getBoardFetchJoinByDSL(Long id);
    public List<Board> getBoards(String title);
}
