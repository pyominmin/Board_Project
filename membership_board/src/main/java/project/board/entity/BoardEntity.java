package project.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.board.dto.BoardDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private  String user_id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "num", nullable = false)
//    private MemberEntity user_id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filepath")
    private String filepath;

    @Column(name = "view_count")
    private int viewCount;

//    @Column(name = "like_count")
//    private int likeCount;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch =FetchType.EAGER)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static BoardEntity toBoardEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setUser_id(boardEntity.getUser_id());
        boardEntity.setTitle(boardEntity.getTitle());
        boardEntity.setContent(boardEntity.getContent());
        boardEntity.setFilename(boardEntity.getFilename());
        boardEntity.setFilepath(boardEntity.getFilepath());
        boardEntity.setViewCount(boardEntity.getViewCount());
        return boardEntity;
    }

    @Override
    public String toString() {
        // BoardEntity의 toString 호출을 피하기 위해 참조하지 않음
        return "CommentEntity{id=" + id + ", content=" + content + "}";
    }

}
