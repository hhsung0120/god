# [god 프로젝트]

#개요   

## study 목적 및 소스 레퍼런스를 만들기 위한 프로젝트

---

##프로젝트 구성

#### IntelliJ IDE
#### JAVA11
#### SpringBoot 2.7.14
#### Gradle 8.1.1
#### MacBook Pro 13 m2 (그냥 쓰고 싶었음..)

---

### 패키지 구조
> /src/main
> > /java/co/kr
> > > /heeseong/god
> > > > 각 기능별 도메인

### API
| NO |         기능          |           설명         |
|:--:|:-------------------:|:--------------------:|
| 1  |     web client      |  http 통신 샘플 및 사용 가이드 |
| 2  | object convert util | 오브젝트 < -> json 변환 유틸 |


## [web client]
### 1. 의존성 주입(build.gradle)
```java
//web client
implementation 'org.springframework.boot:spring-boot-starter-webflux'
```

### 2. utils 패키지 복사
```java
package co.kr.heeseong.god.webclient.utils;
```

### 3. 사용 가이드
```java
패키지 위치 co.kr.heeseong.god.webclient
        
WebClientTemplate.getRequest(URL + "/accounts", request, Map.class); [요청 주소, 요청 데이터, 리턴 타입]
WebClientTemplate.postRequest(URL + "/accounts", request, Map.class); [요청 주소, 요청 데이터, 리턴 타입]
// 컨텐츠 타입 및 헤더를 넣을 수 있는 함수도 있으니 참고 바랍니다.
// 기본 컨텐츠 타입은 MediaType.APPLICATION_JSON 입니다.
```

--- 

## [object convert util]
### 1. utils 패키지 복사
```java
package co.kr.heeseong.god.objectconverter.utils;
```

### 2. 사용 가이드
```java
package co.kr.heeseong.god.objectconverter.main;

// object to json
String objectToJson = ObjectConverter.objectToJson(dto);

// list to json
List<TestDto> objectList = Arrays.asList(testDto1, testDto2, testDto3);

// map to json
String mapToJson = ObjectConverter.mapToJson(map);

// list 형의 json 을 List로 변환,
List<TestDto> dtoList = ObjectConverter.jsonToList(listJson);

// object to map
Map<String, Object> objectToMap = ObjectConverter.objectToMap(testDto4);

// json to map
Map<String, String> jsonToMap = ObjectConverter.jsonToMap(testJson);

// map to object
TestDto mapToObject = ObjectConverter.mapToObject(testMap, TestDto.class);

// json to object
TestDto jsonToObject = ObjectConverter.jsonToObject(testJson2, TestDto.class);

```

[//]: # ()
[//]: # (# MD 파일 가이드)

[//]: # (### [표])

[//]: # (| 제목    |     내용 |   설명   |)

[//]: # (|:------|-------:|:------:|)

[//]: # (| 좌측 정렬 | 오른쪽 정렬 | 가운데 정렬 |)

[//]: # ()
[//]: # (# 큰글씨)

[//]: # (## 작은글씨)

[//]: # (### 작은글씨)

[//]: # ()
[//]: # ()
[//]: # (헤더)

[//]: # (=============)

[//]: # ()
[//]: # (부제목)

[//]: # (-------------)

[//]: # ()
[//]: # ()
[//]: # (```java)

[//]: # (코드 블럭)

[//]: # ()
[//]: # (```)

[//]: # ()
[//]: # (줄바꿈 스페이스 3번)