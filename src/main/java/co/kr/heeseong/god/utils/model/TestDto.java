package co.kr.heeseong.god.utils.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TestDto {

    private Long seq;
    private String memberId;
    private String memberName;

    public TestDto() {
    }

    public TestDto(Long seq, String memberId, String memberName) {
        this.seq = seq;
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
