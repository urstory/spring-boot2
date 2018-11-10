package examples.boot.simpleboard.service;

import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.ImageFile;
import examples.boot.simpleboard.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    public Board getBoard(Long id);
    public Long getBoardCount();
    public Long getBoardCountByUserName(String userName);
    public Board addBoard(Board board);
    public Page<Board> getBoardList(int page);
    public Page<Board> getBoardList(int page, String searchType, String searchStr);
    public ImageFile addImageFile(ImageFile imageFile);
    public ImageFile getImageFile(Long imageFileId);
    public void deleteBaord(Long id);
    public void updateBoard(Board board);
}
