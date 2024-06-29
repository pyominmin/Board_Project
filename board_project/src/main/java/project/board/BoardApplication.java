package project.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import project.board.repository.BoardRepository;

@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // Spring Security 자동 구성 비활성화
public class BoardApplication{  //implements CommandLineRunner


	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
//
//	// UserRepository를 자동 주입 받습니다.
//	@Autowired
//	private MemberRepository memberRepository;
//
//	// CommandLineRunner의 run 메서드를 오버라이드하여 애플리케이션 시작 시 실행되는 로직을 추가합니다.
//	@Override
//	public void run(String... args) throws Exception {
//		for (int i = 1; i <= 100; i++) {
//			MemberEntity user = new MemberEntity();
//			user.setMemberEmail("sonny" + i + "@example.com");
//			user.setMemberPassword("12345678");
//			user.setMemberName("손흥민" + i);
//			user.setBirthDateFormatted("2024-06-19");
//			user.setMemberGender("남자");
//			user.setMemberPno("01012345678");
//			user.setNickname("손흥민");
//
//			BoardEntity board = new BoardEntity();
//			board.setId((long) i);
//			board.setUser_id(String.valueOf(i));
//			board.setContent("안녕하세요 내용~~");
//			board.setTitle("제목");
//
//			boardRepository.save(board); // 사용자 정보를 데이터베이스에 저장합니다.
//			memberRepository.save(user); // 사용자 정보를 데이터베이스에 저장합니다.
//		}
//
//		System.out.println("100 users inserted successfully!");
//	}

}