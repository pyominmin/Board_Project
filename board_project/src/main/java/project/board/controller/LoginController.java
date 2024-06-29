package project.board.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.board.dto.MemberDTO;
import project.board.entity.MemberEntity;
import project.board.repository.MemberRepository;
import project.board.service.LoginService;
import project.board.service.MemberService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/member/login")
    public String loginForm() {
        return "html/loginForm";
    }


    // 로그인 처리
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        // Fetch the MemberEntity from the database using the email
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        session.setAttribute("memberPassword", memberDTO.getMemberPassword());
        MemberDTO loginResult = loginService.login(memberDTO);

        if (loginResult != null) {
            // 로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("nickname", loginResult.getNickname());
            session.setAttribute("memberNum", loginResult.getMemberNum());

            log.info("Login Success");
            log.info(loginResult.getMemberEmail());

            model.addAttribute("message", "로그인되었습니다.");
            model.addAttribute("searchUrl", "/board/list");

            return "html/message";

        } else {
            // 로그인 실패
            model.addAttribute("message", "로그인 실패하였습니다.");
            model.addAttribute("searchUrl", "/member/login");
            return "html/message";
        }
    }

    //    로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {

        if (session != null) {
            session.invalidate();
        }
        model.addAttribute("message", "로그아웃 되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "html/message";
    }
}