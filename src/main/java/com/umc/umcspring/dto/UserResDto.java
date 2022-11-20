package com.umc.umcspring.dto;


import com.umc.umcspring.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter // 해당 클래스에 대한 접근자 생성
@AllArgsConstructor
@Setter// 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성
/**
 * Res.java: From Server To Client
 * 하나 또는 복수개의 회원정보 조회 요청(Get Request)의 결과(Respone)를 보여주는 데이터의 형태
 *
 * GetUserRes는 클라이언트한테 response줄 때 DTO고
 * User 클래스는 스프링에서 사용하는 Objec이다.
 */
public class UserResDto {
    private int userIdx;
    private String nickname;
    private String email;
    private String password;

    //repository 를 통해 조회한 entity 를 dto 로 변환 용도
    public UserResDto(User user) {
        this.userIdx = user.getUserIdx();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

}
