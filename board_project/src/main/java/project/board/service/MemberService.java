package project.board.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.board.dto.MemberDTO;
import project.board.entity.MemberEntity;
import project.board.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public void save(MemberDTO memberDTO, String memberPassword) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberPassword));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public boolean emailExists(String memberEmail) {

        return memberRepository.existsByMemberEmail(memberEmail);
    }

    public void updateMemberInfo(String memberEmail, String memberPno, String memberNickname, String memberGender) {
        // 로그 출력
        log.info("Updating member info for email: {}", memberEmail);
        log.info("New Phone Number: {}", memberPno);
        log.info("New Nickname: {}", memberNickname);
        log.info("New Gender: {}", memberGender);

        // 이메일을 기준으로 회원 정보 조회
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + memberEmail));

        // 전화번호 업데이트
        if (memberPno != null && !memberPno.isEmpty()) {
            member.setMemberPno(memberPno);
        }

        // 닉네임 업데이트
        if (memberNickname != null && !memberNickname.isEmpty()) {
            member.setNickname(memberNickname);
        }

        // 성별 업데이트
        if (memberGender != null && !memberGender.isEmpty()) {
            member.setMemberGender(memberGender);
        }

        // 업데이트된 회원 정보 저장
        memberRepository.save(member);
    }
}

