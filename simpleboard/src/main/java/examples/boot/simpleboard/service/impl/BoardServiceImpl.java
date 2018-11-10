package examples.boot.simpleboard.service.impl;

import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.ImageFile;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.repository.BoardRepository;
import examples.boot.simpleboard.repository.ImageFileRepository;
import examples.boot.simpleboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ImageFileRepository imageFileRepository;

    public BoardServiceImpl() {
        System.out.println("------------------------------------------");
        System.out.println("BoardServiceImpl()");
        System.out.println("------------------------------------------");
    }

    @Override
    @Transactional
    public Board getBoard(Long id) {
        Board board = boardRepository.getOne(id);
        board.setReadCount(board.getReadCount() + 1);
        return board;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBoardCount() {
        return boardRepository.countAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBoardCountByUserName(String userName) {
        return boardRepository.countAllByUserName(userName);
    }

    @Override
    @Transactional
    public Board addBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Board> getBoardList(int page) {
        return getBoardList(page, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Board> getBoardList(int page, String searchType, String searchStr) {
        Page<Board> list = null;

        if(searchType != null)
            searchType = searchType.toUpperCase();

        PageRequest pageRequest = PageRequest.of(page - 1, 10,new Sort(Sort.Direction.DESC, "regdate"));
        if("TITLE".equals(searchType)){
            list = boardRepository.findAllByTitleContains(searchStr, pageRequest);
        }else if("NAME".equals(searchType)){
            list = boardRepository.findAllByUserName(searchStr, pageRequest);
        }else if("CONTENT".equals(searchType)){
            list = boardRepository.findAllByContentContains(searchStr, pageRequest);
        }else{
            list = boardRepository.findAllBy(pageRequest);
        }
        return list;
    }

    @Override
    @Transactional
    public ImageFile addImageFile(ImageFile imageFile) {
        return imageFileRepository.save(imageFile);
    }

    @Override
    @Transactional(readOnly = true)
    public ImageFile getImageFile(Long imageFileId) {
        return imageFileRepository.getOne(imageFileId);
    }

    @Override
    @Transactional
    public void deleteBaord(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateBoard(Board board) {
        boardRepository.save(board);
    }
}
