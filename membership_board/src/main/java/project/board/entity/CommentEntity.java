package project.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.board.dto.CommentDTO;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "writer", nullable = false)
    private String commentWriter;

    @Column(name = "comment", nullable = false)
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private BoardEntity boardEntity;

    @Column(name = "commentCreatedTime")
    private LocalDateTime commentCreatedTime;

    public static CommentEntity toCommentEntity(CommentDTO commentDTO, BoardEntity boardEntity){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }

    @PrePersist
    protected void onCreate() {
        this.commentCreatedTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        // BoardEntity의 toString 호출을 피하기 위해 참조하지 않음
        return "CommentEntity{id=" + id + ", content=" + commentContents + "}";
    }
}
