package co.kr.heeseong.god.webclient.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class WebClientTemplate {

    private static final int TIMEOUT_SEC = 10;
    private static WebClient webClient;

    // 타임 아웃 시간 설정
    // get 요청 보내기 -> 원하는 객체로 응답 받기
    // post 요청 보내기 -> 원하는 객체로 응답 받기
    // get 요청 보내기 -> 원하는 List<객체>로 응답받기
    // post 요청 보내기 -> 원하는 List<객체>로 응답받기
    // 파일 가져오기 ?
    // 이거 추가
    // 컨텐츠 타입도.. 처리 가능해야 할듯.. ?
    //헤더 설정

    public WebClientTemplate(WebClient.Builder builder) {
        webClient = builder.build();
    }

    public static <T> T getRequest(String uri, Map<String, String> requestData, Class<T> responseType) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.setAll(requestData);

        return webClientGetRequest(HttpMethod.GET, uri, new HashMap<>(), request, responseType);
    }

    public static <T> T webClientGetRequest(String uri, Map<String, String> headerInfo, Map<String, String> requestData, Class<T> responseType) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.setAll(requestData);

        return webClientGetRequest(HttpMethod.GET, uri, headerInfo, request, responseType);
    }

    private static <T> T webClientGetRequest(HttpMethod httpMethod, String uri, Map<String, String> headerInfo, MultiValueMap<String, String> requestData, Class<T> responseType) {
        log.info("==================== webClient start ====================");

        T response = null;
        try {
            response = webClient
                    .get()
                    .uri(uriBuilder ->
                            uriBuilder.path(uri)
                                    .queryParams(requestData)
                                    .build())
                    .headers(httpHeaders -> {
                        for (String key : headerInfo.keySet()) {
                            httpHeaders.add(key, headerInfo.get(key));
                        }
                    })
                    .retrieve()
                    .bodyToMono(responseType)
                    .doOnSuccess(res -> {
                        log.info("--- success : request uri [{}], response [{}]", uri, res);
                    })
                    .doOnError(throwable -> {
                        log.error("--- exception : request uri [{}], response [{}]", uri, throwable.getStackTrace());
                    })
                    .timeout(Duration.ofSeconds(TIMEOUT_SEC))
                    .block();
        } catch (Exception e) {

        }

        log.info("==================== webClient end ====================");
        return response;
    }

    public static <T> T postRequest(String uri, Map<String, String> headerInfo, MediaType mediaType, Object requestData, Class<T> responseType) {
        return webClientPostRequest(HttpMethod.POST, uri, headerInfo, mediaType, requestData, responseType);
    }

    private static <T> T webClientPostRequest(HttpMethod httpMethod, String uri, Map<String, String> headerInfo, MediaType mediaType, Object requestData, Class<T> responseType) {
        log.info("==================== webClient start ====================");

        T response = null;
        try {
            response = webClient
                    .method(httpMethod)
                    .uri(uri)
                    .headers(httpHeaders -> {
                        for (String key : headerInfo.keySet()) {
                            httpHeaders.add(key, headerInfo.get(key));
                        }
                    })
                    .contentType(mediaType)
                    .bodyValue(requestData)
                    .retrieve()
                    .bodyToMono(responseType)
                    .doOnSuccess(res -> {
                        log.info("--- success : request uri [{}], response [{}]", uri, res);
                    })
                    .doOnError(throwable -> {
                        log.error("--- exception : request uri [{}], response [{}]", uri, throwable.getStackTrace());
                    })
                    .timeout(Duration.ofSeconds(TIMEOUT_SEC))
                    .block();
        } catch (Exception e) {

        }

        log.info("==================== webClient end ====================");
        return response;
    }
}
//
//    public static ResponseEntity<?> execForFile(HttpMethod method, URI uri, MultipartBodyBuilder builder, String jwtToken) {
//        log.info("=== REQUEST - START ===");
//        ResponseEntity<String> response = webClient
//                .method(method)
//                .uri(uri)
//                .header(HttpHeaders.CONTENT_TYPE, "Bearer " + jwtToken)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(BodyInserters.fromMultipartData(builder.build()))
//                .retrieve()
//                .onStatus(httpStatusCode -> httpStatusCode.equals(HttpStatus.UNAUTHORIZED),
//                        clientResponse -> clientResponse.createException()
//                                .flatMap(e -> Mono.error(new RuntimeException())))
//                .toEntity(String.class)
//                .timeout(Duration.ofSeconds(TIMEOUT_SEC))
//                .doOnRequest(e -> log.info("--- REQUEST : URL[{}], PARAM[{}]", uri, builder.build()))
//                .doOnSuccess(res -> log.info("--- SUCCESS : URL[{}], RESPONSE[{}]", uri, res))
//                .doOnError(Throwable::printStackTrace)
//                .block();
//        log.info("=== REQUEST - END ===");
//        return response;
//    }
//
//
//// 받는 쪽
////    @PutMapping("/logo")
////    public ResponseEntity<BaseResponse> uploadCompanyLogo(@RequestPart MultipartFile companyLogo,
////                                                          @CompanyUser CompanyUserVo userVo) {
////    }
//
////호출
////
////    URI uri = UriComponentsBuilder.fromHttpUrl(BuiltnersApiUrl.Company.MEMBER_LOGO)
////            .encode().build().toUri();
////
////    MultipartBodyBuilder builder = new MultipartBodyBuilder();
////        builder.part("companyLogo", companyLogo.getResource());
////
////                ResponseEntity<BaseResponse> response = webClientTemplate.execForFile(HttpMethod.PUT, uri, builder, token.getValue());
//
//
//////파일 받을 때 중간에 보면
////ResponseEntity<Resource> response = webClient
////        .method(HttpMethod.GET)
////        .uri(uri)
////        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
////        .retrieve()
////        .onStatus(httpStatusCode -> httpStatusCode.equals(HttpStatus.UNAUTHORIZED),
////                clientResponse -> clientResponse.createException()
////                        .flatMap(e -> Mono.error(new JWTTokenExpiredException())))
////        .toEntity(Resource.class)
////        .timeout(Duration.ofSeconds(TIMEOUT_SEC))
////        .doOnRequest(e -> log.info("--- REQUEST : URL[{}]", uri))
////        .doOnSuccess(res -> log.info("--- SUCCESS : URL[{}], RESPONSE[{}]", uri, res))
////        .doOnError(err -> {
////            log.error("REQUEST FAIL - CAUSE[{}]", err.getMessage());
////            err.printStackTrace();
////        })
////        .block();
//
//// Resource.class 파일 받는 부분
////    Resource resource = awsFileService.download(place, uuid, token);
////        return ResponseEntity.ok()
////                .contentType(MediaType.APPLICATION_OCTET_STREAM)
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
////                .body(resource);
//
//
//
//// ResponseEntity<BaseResponse> baseResponseResponseEntity = WebClientTemplate.execForFile(null, null, null, null);
////baseResponseResponseEntity.getBody();
////  System.out.println(baseResponseResponseEntity);
//
////        .retrieve() .bodyToMono(Employee.class);
//
//
//
////        retrieve() : ResponseEntity를 받아 디코딩
////
////                - exchange() : ClientResponse를 상태값 그리고 헤더와 함께
////
////
////
////        받은 응답은 bodyToFlux, bodyToMono 형태로 가져와 각각 Flux와 Mono 객체로 바꿔줍니다.
////
////                Mono 객체는 0-1개의 결과를 처리하는 객체이고, Flux는 0-N개의 결과를 처리하는 객체입니다.
