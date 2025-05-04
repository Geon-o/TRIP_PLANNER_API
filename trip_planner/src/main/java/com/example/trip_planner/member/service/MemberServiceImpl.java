package com.example.trip_planner.member.service;

import com.example.trip_planner.config.mail.service.MailService;
import com.example.trip_planner.member.entity.Member;
import com.example.trip_planner.member.repository.MemberRepository;
import com.example.trip_planner.member.request.signIn.SignInRequest;
import com.example.trip_planner.member.request.signIn.SignInResponse;
import com.example.trip_planner.member.request.signUp.EmailAuthRequest;
import com.example.trip_planner.member.request.signUp.CheckAuthTokenRequest;
import com.example.trip_planner.member.request.signUp.SignUpRequest;
import com.example.trip_planner.member.util.AuthTokenIssuance;
import com.example.trip_planner.member.util.EmailVerification;
import com.example.trip_planner.config.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl {

    private final RedisService redisService;
    private final AuthTokenIssuance tokenIssuance;
    private final MailService mailService;
    private final EmailVerification emailVerification;
    private final MemberRepository memberRepository;

    /**
     * 토큰 발급 후 레디스 등록 및 이메일 발송
     *
     * @param request
     */
    public void registerVerificationNo(EmailAuthRequest request) {

        try {
            emailVerification.emailVerification(request.getEmail());

            String token = tokenIssuance.issueToken();
            redisService.setKeyAndValue(request.getEmail(), token, request.getDeadlineTime());
            sendTokenToEmail(request.getEmail(), token);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("email is not valid");
        }
    }

    public void sendTokenToEmail(String email, String token) {
        try {
            mailService.sendMail(email, token);

        } catch (Exception e) {
            log.info("메일 전송 실패!");
        }

    }

    /**
     * 이메일로 발송된 인증번호 확인하는 로직
     *
     * @param request
     * @return
     */
    public boolean checkVerificationNo(CheckAuthTokenRequest request) {
        String token = String.valueOf(redisService.getValueByKey(request.getEmail()));

        if (token.equals(request.getAuthToken())) {
            redisService.deleteByKey(request.getEmail());
            return true;
        }

        return false;
    }

    /**
     * 사용자 Id 중복체크 로직
     *
     * @param userId
     * @return
     */
    public boolean checkDuplicateUserId(String userId) {
        return memberRepository.findByUserId(userId).isEmpty();
    }

    public void signUp(SignUpRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = Member.builder()
                .email(request.getEmail())
                .userId(request.getUserId())
                .password(request.getPassword())
                .build();

        member.hashPassword(passwordEncoder);
        memberRepository.save(member);
    }

    public SignInResponse signIn(SignInRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<Member> maybeMember = memberRepository.findByUserId(request.getUserId());

        if (maybeMember.isPresent()) {

            Member member = maybeMember.get();
            if (!maybeMember.get().checkPassword(request.getPassword(), passwordEncoder)) {
                throw new RuntimeException("패스워드가 일치하지 않습니다.");
            }

            UUID authToken = UUID.randomUUID();

            redisService.deleteByKey(authToken.toString());
            redisService.setKeyAndValue(authToken.toString(), String.valueOf(member.getId()), 1440);

            var signInResponse = SignInResponse.builder()
                    .authToken(authToken.toString())
                    .userId(member.getUserId())
                    .build();

            return signInResponse;
        }
        throw new RuntimeException("가입된 사용자가 아닙니다.");
    }
}
