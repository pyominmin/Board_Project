package project.board.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.board.entity.BoardEntity;
import project.board.repository.BoardRepository;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired // Autowired를 사용하면 BoardApplication의 @Bean이 자동적으로 읽어준다.
    private BoardRepository boardRepository;

    // 게시글 저장
    public void boardWrite(BoardEntity board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid.toString() + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<BoardEntity> boardList(Pageable pageable) {return boardRepository.findAll(pageable);}

    // 게시글 검색 처리
    public Page<BoardEntity> boardSearchList(String searchKeyword, Pageable pageable) {return boardRepository.findByTitleContaining(searchKeyword, pageable);}

    // 특정 게시글 불러오기
    public BoardEntity boardView(Long id) {return boardRepository.findById(id).get();}

    // 특정 게시글 삭제
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    // 조회수 증가
    @Transactional
    public void viewCount(Long id) {
        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);
    }

    // 좋아요 증가
//    @Transactional
//    public void likeCount(Long id) {
//        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
//        board.setLikeCount(board.getLikeCount() + 1);
//        boardRepository.save(board);
//    }
}
