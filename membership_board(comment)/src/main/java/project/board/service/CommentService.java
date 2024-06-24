package project.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.dto.CommentDTO;
import project.board.entity.BoardEntity;
import project.board.entity.CommentEntity;
import project.board.entity.MemberEntity;
import project.board.repository.BoardRepository;
import project.board.repository.CommentRepository;
import project.board.repository.MemberRepository;
import project.board.repository.mybatis.CommentMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentMapper commentMapper;

    public void addComment(CommentEntity commentEntity) {
        commentMapper.insertComment(commentEntity);
    }

    public List<CommentEntity> getCommentsByPostId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public CommentEntity addComment(Long boardId, Long memberId, String content) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + boardId));

        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + memberId));

        CommentEntity comment = new CommentEntity();
        comment.setBoard(board);
        comment.setMember(member);
        comment.setContent(content);
//        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }


    public List<CommentDTO> getCommentsByBoardId(Long boardId) {
        List<CommentEntity> commentEntities = commentRepository.findByBoardId(boardId);
        return commentEntities.stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
