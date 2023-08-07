package co.kr.heeseong.god.webclient.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Log4j2
@Component
public class WebClientTemplate {

    private static final int TIMEOUT_SEC = 10;

    private static WebClient webClient;
    public WebClientTemplate(WebClient.Builder builder) {
        webClient = builder.build();
    }

    public static <T> T getRequest(String uri, Map<String, String> requestData, Class<T> responseType) {
        return getRequest(uri, new HashMap<>(), requestData, responseType);
    }

    public static <T> T getRequest(String uri, Map<String, String> headerInfo, Map<String, String> requestData, Class<T> responseType) {
        return webClientGetRequest(uri, headerInfo, requestData, responseType);
    }

    private static <T> T webClientGetRequest(String uri, Map<String, String> headerInfo, Map<String, String> requestData, Class<T> responseType) {
        log.info("==================== webClient get start ====================");

        T response = null;
        try {
            response = webClient
                    .get()
                    .uri(uri + "?" + setQueryParam(requestData)) // uriBuilder 와 MultiValueMap 으로 처리가 가능하나.. 어째서인지 로컬에서 주소가 씹힘, 인코딩 이슈 또는 baseUrl 이슈인듯
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
            log.error("기호에 맞게 처리");
        }

        log.info("==================== webClient get end ====================");
        return response;
    }

    public static <T> T postRequest(String uri, Object requestData, Class<T> responseType) {
        return postRequest(uri, new HashMap<>(), requestData, responseType);
    }

    public static <T> T postRequest(String uri, Map<String, String> headerInfo, Object requestData, Class<T> responseType) {
        return postRequest(uri, headerInfo, MediaType.APPLICATION_JSON, requestData, responseType);
    }

    public static <T> T postRequest(String uri, MediaType mediaType, Object requestData, Class<T> responseType) {
        return postRequest(uri, new HashMap<>(), mediaType, requestData, responseType);
    }

    public static <T> T postRequest(String uri, Map<String, String> headerInfo, MediaType mediaType, Object requestData, Class<T> responseType) {
        return webClientPostRequest(uri, headerInfo, mediaType, requestData, responseType);
    }

    private static <T> T webClientPostRequest(String uri, Map<String, String> headerInfo, MediaType mediaType, Object requestData, Class<T> responseType) {
        log.info("==================== webClient post start ====================");

        T response = null;
        try {
            response = webClient
                    .post()
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

        log.info("==================== webClient post end ====================");
        return response;
    }

    private static String setQueryParam(Map<String, String> requestData) {
        String queryParam = "";
        Set<String> keys = requestData.keySet();
        for (String key : keys) {
            queryParam = queryParam + key + "=" + requestData.get(key) + "&";
        }

        return queryParam.substring(0, queryParam.length() - 1);
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
