package co.kr.heeseong.god.webclient.controller;

import co.kr.heeseong.god.test.model.AccountUser;
import co.kr.heeseong.god.webclient.utils.WebClientTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/web-client")
public class WebClientController {

    private final String URL = "http://localhost:8080/web-client-test";

    @GetMapping("/accounts/{seq}")
    public ResponseEntity<?> getAccount(@PathVariable(value = "seq") Long seq) {
        Map<String, String> request = new HashMap<>();
        request.put("seq", String.valueOf(seq));

        Map<String, String> response = WebClientTemplate.getRequest(URL + "/accounts", request, Map.class);

        log.info("data : {}", response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accounts/{seq}")
    public ResponseEntity<?> updateAccount(@PathVariable(value = "seq") Long seq) {
        Map<String, String> request = new HashMap<>();
        request.put("seq", String.valueOf(seq));

        AccountUser response = WebClientTemplate.postRequest(URL + "/accounts/", request, AccountUser.class);
        log.info(response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
