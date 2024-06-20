package project.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.board.dto.CommentDTO;
import project.board.entity.BoardEntity;
import project.board.entity.CommentEntity;
import project.board.repository.BoardRepository;
import project.board.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        log.info("CommentDTO: {}", commentDTO);

        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        log.info("BoardEntity 존재 여부: {}", optionalBoardEntity.isPresent());

        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toCommentEntity(commentDTO, boardEntity);
            log.info("CommentEntity 생성: {}", commentEntity);

            if (commentEntity.getCommentContents() == null || commentEntity.getCommentContents().trim().isEmpty()) {
                log.error("댓글 내용이 비어 있습니다.");
                throw new IllegalArgumentException("댓글 내용은 비워둘 수 없습니다.");
            }

            return commentRepository.save(commentEntity).getId();
        } else {
            log.error("해당 게시글이 존재하지 않습니다. Board ID: {}", commentDTO.getBoardId());
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        if (boardEntity == null) {
            return new ArrayList<>();
        }
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
