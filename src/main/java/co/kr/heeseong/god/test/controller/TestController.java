package co.kr.heeseong.god.test.controller;

import co.kr.heeseong.god.common.utils.webclient.BaseResponse;
import co.kr.heeseong.god.common.utils.webclient.WebClientTemplate;
import co.kr.heeseong.god.test.model.AccountUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@Controller("/test")
public class TestController {

    @GetMapping("")
    public String test(){
        log.info("테스트 컨트롤러 입니다.");

        ResponseEntity<BaseResponse> baseResponseResponseEntity = WebClientTemplate.execForFile(null, null, null, null);
        System.out.println(baseResponseResponseEntity);

//        .retrieve() .bodyToMono(Employee.class);



//        retrieve() : ResponseEntity를 받아 디코딩
//
//                - exchange() : ClientResponse를 상태값 그리고 헤더와 함께
//
//
//
//        받은 응답은 bodyToFlux, bodyToMono 형태로 가져와 각각 Flux와 Mono 객체로 바꿔줍니다.
//
//                Mono 객체는 0-1개의 결과를 처리하는 객체이고, Flux는 0-N개의 결과를 처리하는 객체입니다.

        return "테스트";
    }

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
