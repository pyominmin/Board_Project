package project.board.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.board.dto.MemberDTO;
import project.board.entity.MemberEntity;
import project.board.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDTO login(MemberDTO memberDTO){
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            //조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다.)
            MemberEntity memberEntity = byMemberEmail.get();
            String encodedPassword = memberEntity.getMemberPassword();
            if(passwordEncoder.matches(memberDTO.getMemberPassword(), encodedPassword)){
                // 비밀번호 일치
                //entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.fromEntity(memberEntity);
                return dto;
            }else{
                //비밀번호 불일치
                return null;
            }
        }else{
            //조회 결과가 없다.(해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }
}