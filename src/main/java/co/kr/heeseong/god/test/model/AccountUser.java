package co.kr.heeseong.god.test.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AccountUser {

    private Long seq;
    private String userId;
    private String userName;

    @Builder
    private AccountUser(Long seq, String userId, String userName) {
        this.seq = seq;
        this.userId = userId;
        this.userName = userName;
    }

    public static AccountUser setUserData(Long seq, String userId, String userName){
        return AccountUser.builder()
                .seq(seq)
                .userId(userId)
                .userName(userName)
                .build();
    }


}
