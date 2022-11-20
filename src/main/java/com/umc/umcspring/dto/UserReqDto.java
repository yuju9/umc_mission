package com.umc.umcspring.dto;

import com.umc.umcspring.model.User;
import lombok.*;

import java.sql.Timestamp;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
@ToString
/**
 * Req.java: From Client To Server
 * 회원정보 수정 요청(Patch Request)을 하기 위해 서버에 전달할 데이터의 형태
 */
public class UserReqDto {
    private int userIdx;
    private String nickname;
    private String email;
    private String password;

    @Builder
    public UserReqDto(int userIdx, String nickname, String email, String password) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }


    public User toEntity() {
        User user = User.builder()
                .userIdx(userIdx)
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();

        return user;
    }


}
