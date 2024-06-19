package project.board.security;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
//                        .requestMatchers("/**").permitAll() // 모든 경로에 대한 접근 허용
//                )
//                .formLogin((formLogin) -> formLogin
//                        .loginPage("/member/login") // 커스텀 로그인 페이지 설정
//                        .defaultSuccessUrl("/") // 로그인 성공 후 리디렉션할 URL 설정
//                );
//        return http.build();
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 인스턴스를 반환하여 비밀번호를 암호화하는 데 사용
//    }
//}
