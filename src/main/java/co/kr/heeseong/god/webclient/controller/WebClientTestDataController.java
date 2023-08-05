package co.kr.heeseong.god.webclient.controller;

import co.kr.heeseong.god.common.model.BaseResponse;
import co.kr.heeseong.god.test.model.AccountUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-client-test")
public class WebClientTestDataController {

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccountUser(@RequestParam(value = "seq", required = false, defaultValue = "0") Long seq){
        log.info("seq : {}", seq);
        AccountUser user1 = AccountUser.setUserData(1L, "1", "홍길동");
        AccountUser user2 = AccountUser.setUserData(2L, "2", "김길동");

        if(seq == 1){
            return new ResponseEntity(user1, HttpStatus.OK);
        }else if(seq == 2){
            return new ResponseEntity(user2, HttpStatus.OK);
        }else if(seq == 3){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> updateAccountUser(@RequestBody Map<String, String> data){
        log.info("data : {}", data);

//        if(seq == 0){
//            return new ResponseEntity(new BaseResponse(500, "실패했습니다.", null), HttpStatus.OK);
//        }else if(seq > 0){
//            return new ResponseEntity(new BaseResponse(200, "업데이트 성공.", null), HttpStatus.OK);
//        }else {
//            return new ResponseEntity(new BaseResponse(404, "유저가 없습니다.", seq), HttpStatus.OK);
//        }
        return null;
    }

    @GetMapping("/accounts/list")
    public List<AccountUser> getAccountUserList(@RequestParam(value = "searchKeyword", required = false, defaultValue = "")String searchKeyword){
        List<AccountUser> accountUserList = new ArrayList<>();

        AccountUser user1 = AccountUser.setUserData(1L, "1", "홍길동");
        AccountUser user2 = AccountUser.setUserData(2L, "2", "김길동");
        AccountUser user3 = AccountUser.setUserData(3L, "3", "고길동");

        //http://localhost:8080/accounts?searchKeyword=%ED%99%8D%EA%B8%B8%EB%8F%99
        if(StringUtils.hasText(searchKeyword)){
            if("홍길동".equalsIgnoreCase(searchKeyword)){
                accountUserList.add(user1);
                return accountUserList;
            }else{
                return new ArrayList<>();
            }
        }

        accountUserList.add(user1);
        accountUserList.add(user2);
        accountUserList.add(user3);

        return accountUserList;
    }

}
