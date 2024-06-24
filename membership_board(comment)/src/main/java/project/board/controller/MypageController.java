package project.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.board.entity.MemberEntity;
import project.board.repository.MemberRepository;
import project.board.service.MemberService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/myPage")
    public String mypage(HttpSession session, Model model) {
        String memberEmail = (String) session.getAttribute("loginEmail");
        if (memberEmail != null) {
            // 서비스 계층을 통해 이메일로 회원 정보 조회
            Optional<MemberEntity> memberInfo = memberRepository.findByMemberEmail(memberEmail);

            log.info("memberInfo: {}", memberInfo);
            log.info("memberEmail: {}", memberEmail);


            // 모델에 이름과 전화번호 추가하기
            model.addAttribute("memberEmail", memberEmail);
            model.addAttribute("memberName", memberInfo.get().getMemberName());
            model.addAttribute("memberPhoneNumber", memberInfo.get().getMemberPno());
            model.addAttribute("memberNickname", memberInfo.get().getNickname());
            model.addAttribute("memberYear", memberInfo.get().getMemberGender());

        } else {
            // 에러 처리나 기본값 설정 필요
            model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
        }

        return "html/profile";
    }

    @PostMapping("/member/change")
    public String updateMemberInfo(HttpSession session,
                                   @RequestParam(required = false) String memberPno,
                                   @RequestParam(required = false) String memberNickname,
                                   @RequestParam(required = false) String memberGender,
                                   Model model) {
        String memberEmail = (String) session.getAttribute("loginEmail");

        // 로그 출력
        log.info("memberEmail: {}", memberEmail);
        log.info("memberPno: {}", memberPno);
        log.info("memberNickname: {}", memberNickname);
        log.info("memberGender: {}", memberGender);

        if (memberEmail != null) {
            try {
                // 회원 정보 업데이트
                memberService.updateMemberInfo(memberEmail, memberPno, memberNickname, memberGender);
                model.addAttribute("message", "회원 정보가 성공적으로 업데이트되었습니다.");
                model.addAttribute("searchUrl", "/member/myPage");


                // 업데이트된 회원 정보를 다시 조회합니다.
                Optional<MemberEntity> updatedMemberInfo = memberRepository.findByMemberEmail(memberEmail);

                if (updatedMemberInfo.isPresent()) {
                    // 업데이트된 정보를 모델에 추가합니다.
                    model.addAttribute("memberEmail", memberEmail);
                    model.addAttribute("memberName", updatedMemberInfo.get().getMemberName());
                    model.addAttribute("memberPhoneNumber", updatedMemberInfo.get().getMemberPno());
                    model.addAttribute("memberNickname", updatedMemberInfo.get().getNickname());
                    model.addAttribute("memberYear", updatedMemberInfo.get().getMemberGender());
                } else {
                    model.addAttribute("error", "업데이트된 회원 정보를 찾을 수 없습니다.");
                }

            } catch (IllegalArgumentException e) {
                model.addAttribute("error", e.getMessage());
                log.error("Error updating member info: {}", e.getMessage());
            } catch (Exception e) {
                model.addAttribute("error", "회원 정보를 업데이트하는 중 오류가 발생했습니다.");
                log.error("Unexpected error: ", e);
            }
        } else {
            model.addAttribute("error", "로그인 상태를 확인할 수 없습니다.");
        }

        return "html/message"; // 업데이트된 정보를 포함하여 뷰로 이동
    }
}
