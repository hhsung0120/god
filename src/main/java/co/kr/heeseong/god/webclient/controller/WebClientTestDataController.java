package co.kr.heeseong.god.webclient.controller;

import co.kr.heeseong.god.test.model.AccountUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-client-test")
public class WebClientTestDataController {

    @GetMapping("/accounts/{seq}")
    public AccountUser getAccountUser(@PathVariable(value = "seq", required = false) Long seq){
        log.info("seq : {}", seq);
        AccountUser user1 = AccountUser.setUserData(1L, "1", "홍길동");
        AccountUser user2 = AccountUser.setUserData(2L, "2", "김길동");

        if(seq == 1){
            return user1;
        }else if(seq == 2){
            return user2;
        }else{
            return null;
        }
    }

    @GetMapping("/accounts")
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

    @GetMapping("/http-request/400")
    public ResponseEntity http400(){
        log.info("http-request 400");
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/http-request/500")
    public ResponseEntity http500(){
        log.info("http-request 500");
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
