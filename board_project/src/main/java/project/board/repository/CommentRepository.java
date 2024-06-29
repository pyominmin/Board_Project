package project.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.board.entity.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long boardId);
}
