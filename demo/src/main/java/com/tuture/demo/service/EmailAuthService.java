package com.tuture.demo.service;

import com.tuture.demo.model.dao.EmailAuthDao;
import com.tuture.demo.model.dto.EmailCodeDto;
import com.tuture.demo.model.dto.VerifyEmailCodeResponse;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailAuthDao emailAuthDao;

    private static final char[] rndAllCharacters = new char[]{
            //number
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //uppercase
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            //lowercase
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            //special symbols
            '@', '$', '!', '%', '?', '&', '#', '^', '+', '='
    };

    private final JavaMailSender javaMailSender;

    public String sendEmailAuthMessage(String email) throws Exception {
        String code = createRandomCode();
        MimeMessage message = createMessage(email, code);

        try {
            javaMailSender.send(message); // 메일 발송
        } catch (Exception e) {
            log.debug("[sendSimpleMessage] 회원 가입 중 오류 발생", e);
        }

        return code;
    }

    private MimeMessage createMessage(String email, String code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("Tuture 회원가입 인증 코드");
        String msg = "\n"
                + "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "  <head>\n"
                + "    <meta charset=\"UTF-8\" />\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
                + "    <title>Tuture</title>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <h1>Tuture 회원가입 인증 코드입니다.</h1>\n"
                + "    <h3>아래 6자리 숫자를 회원가입 입력칸에 입력해주세요</h3>\n"
                + "    <div style=\"align-self: center;  border: 5px solid black; width: 50%; height: 10%;\">\n"
                + "      <h1 style=\"text-align: center; font-size: 50px;\">" + code + "</h1>\n"
                + "  </body>\n"
                + "</html>";

        message.setText(msg, "utf-8", "html");
        return message;
    }

    private String createRandomCode() {
        StringBuilder key = new StringBuilder();
        Random randomCode = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((randomCode.nextInt(10)));
        }

        return key.toString();
    }

    @Transactional
    public void saveEmailCode(String email, String code) {
        EmailCodeDto newEmailCode = new EmailCodeDto(email, code);
        emailAuthDao.insertCode(newEmailCode);
    }

    public VerifyEmailCodeResponse verifyEmailCode(String email, String code) {
        EmailCodeDto selectedByEmail = emailAuthDao.selectByEmail(email);
        if (selectedByEmail == null) {
            return VerifyEmailCodeResponse.builder().status(400).msg("입력받은 이메일과 일치하는 코드 정보가 없습니다.").build();
        }
        if (!selectedByEmail.getCode().equals(code)) {
            return VerifyEmailCodeResponse.builder().status(400).msg("코드가 일치하지 않습니다.").build();
        }
        if (isExpiredCode(selectedByEmail.getExpirationDate())) {
            return VerifyEmailCodeResponse.builder().status(400).msg("코드가 만료되었습니다. 이메일 인증 요청을 다시 진행해주세요.").build();
        }
        return VerifyEmailCodeResponse.builder().status(200).msg("인증 성공하였습니다.").build();
    }

    private boolean isExpiredCode(String expirationDate) {
        // 코드가 만료되지 않았으면 true
        // 코드가 만료되었으면 false

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expirationDateTime = LocalDateTime.parse(expirationDate, formatter);

        return currentTime.isAfter(expirationDateTime);
    }

}