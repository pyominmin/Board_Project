package project.board.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.board.dto.BoardDTO;
import project.board.entity.BoardEntity;
import project.board.entity.CommentEntity;
import project.board.repository.BoardRepository;
import project.board.service.BoardService;
import project.board.service.CommentService;
import project.board.service.LoginService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentService commentService;



    public BoardController(BoardService boardService) {
    }

    // 글작성
    @GetMapping("/write")
    public String boardWrite(HttpSession session, Model model) {

        if(session.getAttribute("loginEmail") != null) {
            model.addAttribute("nickname", session.getAttribute("nickname"));
            model.addAttribute("memberNum", session.getAttribute("memberNum"));
            model.addAttribute("isLogin", true);
            return "html/boardWrite";
        } else {
            // 로그인 실패
            model.addAttribute("isLogin", false);
            model.addAttribute("message", "로그인 이후 게시글 작성을 이용하실 수 있습니다.");
            model.addAttribute("searchUrl", "/member/login");
            return "html/message";
        }
    }

    // 글작성 처리
    @PostMapping("/writepro")
    public String boardWritePro(BoardEntity board, Model model) throws Exception {
        boardService.boardWrite(board);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "html/message"; // 또는 필요한 페이지로 리다이렉트
    }

    // 게시글 리스트 처리
    @GetMapping("/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                            HttpSession session) {
        Page<BoardEntity> page;
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            page = boardService.boardList(pageable);
        } else {
            page = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = page.getNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(nowPage + 3, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("searchKeyword", searchKeyword); // 검색 키워드 추가

        // 로그인 상태면 true
        if(session.getAttribute("loginEmail") != null) {
            model.addAttribute("isLogin", true);
        } else {
            model.addAttribute("isLogin", false);
        }

        return "html/boardList";
    }

    // 특정 게시글 불러오기
    @GetMapping("/view/{id}")
    public String boardView(@PathVariable Long id, Model model,
                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                            @RequestParam(value = "page", required = false) Integer page, HttpSession session) {

        BoardDTO board =  boardService.boardView(id);

        // 특정 게시글 가져오기
        model.addAttribute("board", board);
        model.addAttribute("createAt", board.getCreatedAt());
        model.addAttribute("memberNum", session.getAttribute("memberNum"));
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("page", page);

        // 로그인 상태면 true
        if(session.getAttribute("loginEmail") != null) {
            model.addAttribute("isLogin", true);
        } else {
            model.addAttribute("isLogin", false);
        }

        return "html/boardView";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable Long id, BoardEntity board, Model model, HttpSession session) {
        BoardEntity boardTemp = BoardEntity.toBoardEntity(boardService.boardView(id));
        
        // 삭제 권한 확인
        if(session.getAttribute("memberNum") != null && boardTemp.getUserId().equals(session.getAttribute("memberNum").toString())) {
        boardService.boardDelete(id);
        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "html/message";
        } else {
            model.addAttribute("message", "게시글 삭제 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");

            return  "html/message";
        }
    }

    // 게시글 수정
    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable Long id, Model model, HttpSession session) {
           BoardEntity  boardTemp = BoardEntity.toBoardEntity(boardService.boardView(id));
//        System.out.println("로그인한 유저의 번호는 " + sessio   n.getAttribute("memberNum"));
//        System.out.println("게시글을 작성한 유저의 번호는 " + boardTemp.getUser_id());
        
        // 수정 권한 확인
        if(session.getAttribute("memberNum") != null && boardTemp.getUserId().equals(session.getAttribute("memberNum").toString())) {
            model.addAttribute("board", boardService.boardView(id));
            return "html/boardModify";
        } else {
            model.addAttribute("message", "게시글 수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");

            return  "html/message";
        }

    }

    // 게시글 수정 처리
    @PostMapping("/update/{id}")
    public String boardUpdate(@PathVariable Long id, BoardEntity board, Model model) throws Exception {

        BoardEntity boardTemp = BoardEntity.toBoardEntity(boardService.boardView(id));
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.boardWrite(boardTemp);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        
        return  "html/message";
    }
}
