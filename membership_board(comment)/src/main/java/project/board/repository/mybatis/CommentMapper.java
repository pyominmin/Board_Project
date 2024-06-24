package project.board.repository.mybatis;

import org.apache.ibatis.annotations.*;
import project.board.entity.CommentEntity;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(CommentEntity commentEntity);

    void updateComment(CommentEntity comment);

    void deleteComment(CommentEntity comment);

    void deleteCommentById(int id);

    void deleteCommentByPostId(int postId);

}
