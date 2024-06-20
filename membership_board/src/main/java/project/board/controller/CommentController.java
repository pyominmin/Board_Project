package project.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.board.dto.CommentDTO;
import project.board.service.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:8080") // 특정 컨트롤러에 대해 CORS 설정
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute CommentDTO commentDTO) {
        log.info("commentDTO = " + commentDTO);

        // Null 또는 빈 값 확인
        if (commentDTO.getCommentContents() == null || commentDTO.getCommentContents().trim().isEmpty()) {
            log.error("댓글 내용이 비어 있습니다.");
            return new ResponseEntity<>("댓글 내용이 비어 있습니다.", HttpStatus.BAD_REQUEST);
        }

        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam Long boardId) {
        log.info("게시글 ID로 댓글 불러오기: boardId = " + boardId);

        // 게시글 ID에 해당하는 댓글 목록 불러오기
        List<CommentDTO> commentDTOList = commentService.findAll(boardId);
        if (commentDTOList != null && !commentDTOList.isEmpty()) {
            // 각각의 DTO에서 포맷된 시간을 설정
            List<Map<String, Object>> formattedComments = commentDTOList.stream().map(comment -> {
                Map<String, Object> formattedComment = new HashMap<>();
                formattedComment.put("commentWriter", comment.getCommentWriter());
                formattedComment.put("commentContents", comment.getCommentContents());
                formattedComment.put("commentCreatedTime", comment.getFormattedCommentCreatedTime());
                return formattedComment;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(formattedComments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글에 댓글이 없습니다.", HttpStatus.NO_CONTENT);
        }
    }
}
