package project.board.dto;

import jakarta.persistence.GeneratedValue;
import lombok.*;
import project.board.entity.BoardEntity;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String userId;  // 사용자 ID, 엔티티와 달리 DTO에서는 String 타입으로 처리할 수 있습니다.
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String filename;
    private String filepath;
    private int viewCount;

    public static BoardDTO fromEntity(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setUserId(boardEntity.getUserId());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContent(boardEntity.getContent());
        boardDTO.setFilename(boardEntity.getFilename());
        boardDTO.setFilepath(boardEntity.getFilepath());
        boardDTO.setViewCount(boardEntity.getViewCount());
        return boardDTO;
    }
}
