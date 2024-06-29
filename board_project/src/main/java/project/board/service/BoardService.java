package project.board.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.board.dto.BoardDTO;
import project.board.entity.BoardEntity;
import project.board.entity.CommentEntity;
import project.board.repository.BoardRepository;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired // Autowired를 사용하면 BoardApplication의 @Bean이 자동적으로 읽어준다.
    private BoardRepository boardRepository;

    // 게시글 저장
    public void boardWrite(BoardEntity board) throws Exception {

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<BoardEntity> boardList(Pageable pageable) {return boardRepository.findAll(pageable);}

    // 게시글 검색 처리
    public Page<BoardEntity> boardSearchList(String searchKeyword, Pageable pageable) {return boardRepository.findByTitleContaining(searchKeyword, pageable);}

    // 특정 게시글 불러오기
    @Cacheable(value = "boards", key = "#id")
    // 특정 게시글 불러오기
    public BoardDTO boardView(Long id) {
        // 게시글 조회
        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        return BoardDTO.fromEntity(board);
    }
    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // RedisTemplate 의존성 주입

    // 캐시에서 게시글 가져오기
    public Object getCachedBoard(Long id) {
        String key = "boards::" + id;
        // 적절한 Redis 연산을 사용하여 데이터를 가져옵니다.
        // 예를 들어, 값이 String으로 저장된 경우:
        return redisTemplate.opsForValue().get(key);
        // 또는, 값이 Hash로 저장된 경우:
        // return redisTemplate.opsForHash().entries(key);
    }

    @CacheEvict(value = "boards", key = "#id")
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    @CachePut(value = "boards", key = "#board.id")
    public void boardUpdate(BoardEntity board) {
        boardRepository.save(board);
    }

    // 댓글 저장
    @Transactional
    public void addComment(BoardEntity board, CommentEntity comment) {
        board.getComments().add(comment);
        comment.setBoard(board);
        boardRepository.save(board);
    }

    // 게시물 찾기
    public BoardEntity getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

}
