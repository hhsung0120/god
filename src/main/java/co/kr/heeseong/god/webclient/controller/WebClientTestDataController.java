package co.kr.heeseong.god.webclient.controller;

import co.kr.heeseong.god.common.model.BaseResponse;
import co.kr.heeseong.god.webclient.model.model.AccountUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-client-test")
public class WebClientTestDataController {

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccountUser(@RequestParam(value = "seq", required = false, defaultValue = "0") Long seq) {
        log.info("seq : {}", seq);

        AccountUser user1 = AccountUser.setUserData(1L, "1", "홍길동");
        AccountUser user2 = AccountUser.setUserData(2L, "2", "김길동");

        if (seq == 1) {
            return new ResponseEntity(new BaseResponse(Arrays.asList(user1, user1, user1)), HttpStatus.OK);
        } else if (seq == 2) {
            return new ResponseEntity(new BaseResponse(user2), HttpStatus.OK);
        } else if (seq == 3) {
            // 웹 크라이언트 404파싱해야함
            return new ResponseEntity(new BaseResponse(HttpStatus.NOT_FOUND.value(), "실패"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버오류"), HttpStatus.OK);
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> updateAccountUser(@RequestBody Map<String, String> data) {
        log.info("data : {}", data);
        Long seq = Long.parseLong(data.get("seq"));

        if (seq == 0) {
            return new ResponseEntity(new BaseResponse(500, "실패했습니다.", null), HttpStatus.OK);
        } else if (seq == 1) {
            return new ResponseEntity(new BaseResponse(200, "업데이트 성공.", null), HttpStatus.OK);
        } else if (seq == 2) {
            return new ResponseEntity(new BaseResponse(404, "유저가 없습니다.", seq), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(new BaseResponse(404, "유저가 없습니다.", seq), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
