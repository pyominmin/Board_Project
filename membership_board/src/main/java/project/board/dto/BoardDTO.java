package project.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.board.entity.BoardEntity;

@Getter
@Setter
@NoArgsConstructor //기본생성자를 자동으로 만들어준다
@ToString
public class BoardDTO {
    private Long id;
    private String user_id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
    private int viewCount;

    public static BoardDTO fromEntity(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setUser_id(boardEntity.getUser_id());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContent(boardEntity.getContent());
        boardDTO.setFilename(boardEntity.getFilename());
        boardDTO.setFilepath(boardEntity.getFilepath());
        boardDTO.setViewCount(boardEntity.getViewCount());
        return boardDTO;
    }
}
