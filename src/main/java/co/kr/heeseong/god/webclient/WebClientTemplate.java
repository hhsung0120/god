package co.kr.heeseong.god.webclient;

import co.kr.heeseong.god.common.model.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Log4j2
public class WebClientTemplate {

    private static final int TIMEOUT_SEC = 10;
    private static WebClient webClient;
aeworigjaeiorjgioaejrgoieajrogjoeairjgioaejrg
    public WebClientTemplate(WebClient.Builder builder) {
        webClient = builder.build();
    }

    public static ResponseEntity<BaseResponse> execForFile(HttpMethod method, URI uri, MultipartBodyBuilder builder, String jwtToken) {
        log.info("=== REQUEST - START ===");
        ResponseEntity<BaseResponse> response = webClient
                .method(method)
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> clientResponse.createException()
                                .flatMap(e -> Mono.error(new RuntimeException())))
                .toEntity(BaseResponse.class)
                .timeout(Duration.ofSeconds(TIMEOUT_SEC))
                .doOnRequest(e -> log.info("--- REQUEST : URL[{}], PARAM[{}]", uri, builder.build()))
                .doOnSuccess(res -> log.info("--- SUCCESS : URL[{}], RESPONSE[{}]", uri, res))
                .doOnError(Throwable::printStackTrace)
                .block();
        log.info("=== REQUEST - END ===");
        return response;
    }
}

// 받는 쪽
//    @PutMapping("/logo")
//    public ResponseEntity<BaseResponse> uploadCompanyLogo(@RequestPart MultipartFile companyLogo,
//                                                          @CompanyUser CompanyUserVo userVo) {
//    }

//호출
//
//    URI uri = UriComponentsBuilder.fromHttpUrl(BuiltnersApiUrl.Company.MEMBER_LOGO)
//            .encode().build().toUri();
//
//    MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        builder.part("companyLogo", companyLogo.getResource());
//
//                ResponseEntity<BaseResponse> response = webClientTemplate.execForFile(HttpMethod.PUT, uri, builder, token.getValue());


////파일 받을 때 중간에 보면
//ResponseEntity<Resource> response = webClient
//        .method(HttpMethod.GET)
//        .uri(uri)
//        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
//        .retrieve()
//        .onStatus(httpStatusCode -> httpStatusCode.equals(HttpStatus.UNAUTHORIZED),
//                clientResponse -> clientResponse.createException()
//                        .flatMap(e -> Mono.error(new JWTTokenExpiredException())))
//        .toEntity(Resource.class)
//        .timeout(Duration.ofSeconds(TIMEOUT_SEC))
//        .doOnRequest(e -> log.info("--- REQUEST : URL[{}]", uri))
//        .doOnSuccess(res -> log.info("--- SUCCESS : URL[{}], RESPONSE[{}]", uri, res))
//        .doOnError(err -> {
//            log.error("REQUEST FAIL - CAUSE[{}]", err.getMessage());
//            err.printStackTrace();
//        })
//        .block();

// Resource.class 파일 받는 부분
//    Resource resource = awsFileService.download(place, uuid, token);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
//                .body(resource);