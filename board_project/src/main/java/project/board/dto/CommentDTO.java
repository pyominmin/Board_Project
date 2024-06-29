package project.board.dto;

import lombok.Data;
import project.board.entity.BoardEntity;
import project.board.entity.CommentEntity;
import project.board.entity.MemberEntity;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long boardId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;

    public static CommentDTO fromEntity(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(commentEntity.getContent());

        // Set BoardEntity
        BoardEntity boardEntity = commentEntity.getBoard();
        if (boardEntity != null) {
            commentDTO.setBoardId(boardEntity.getId());
        }

        // Set MemberEntity
        MemberEntity memberEntity = commentEntity.getMember();
            if (memberEntity != null) {
                commentDTO.setMemberId(memberEntity.getNum());
            }

        commentDTO.setCreatedAt(commentEntity.getCreatedAt());

        return commentDTO;
    }
}
