package co.kr.heeseong.god.webclient.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-client")
public class WebClientController {

    @GetMapping("/get")
    public String webClientGetTest(){
        log.info("테스트 컨트롤러 입니다.");

       // ResponseEntity<BaseResponse> baseResponseResponseEntity = WebClientTemplate.execForFile(null, null, null, null);
        //baseResponseResponseEntity.getBody();
      //  System.out.println(baseResponseResponseEntity);

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

}
