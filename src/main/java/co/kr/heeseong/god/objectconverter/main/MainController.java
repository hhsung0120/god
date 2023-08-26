package co.kr.heeseong.god.objectconverter.main;

import co.kr.heeseong.god.objectconverter.model.TestDto;
import co.kr.heeseong.god.objectconverter.utils.ObjectConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    public static void main(String[] args) {

        /* ########################################################################################### */
        // object to json
        TestDto dto = new TestDto(1L, "heeseong", "heeseong");
        String objectToJson = ObjectConverter.objectToJson(dto);
        System.out.println("objectToJson = " + objectToJson);

        /* ########################################################################################### */
        // list to json
        TestDto testDto1 = new TestDto(1L, "dhzpdl1", "anfqud1");
        TestDto testDto2 = new TestDto(2L, "dhzpdl2", "anfqud2");
        TestDto testDto3 = new TestDto(3L, "dhzpdl2", "anfqud3");
        List<TestDto> objectList = Arrays.asList(testDto1, testDto2, testDto3);
        String listToJson = ObjectConverter.listToJson(objectList);
        System.out.println("listToJson = " + listToJson);

        /* ########################################################################################### */
        // map to json
        Map<String, String> map = new HashMap<>();
        map.put("seq", "1");
        map.put("memberId", "hshan");
        map.put("memberName", "heeseong");
        String mapToJson = ObjectConverter.mapToJson(map);
        System.out.println("mapToJson = " + mapToJson);

        /* ########################################################################################### */
        // list 형의 json 을 List로 변환, 변수 이름 일치 해야함
        String listJson = "[{\"seq\":1,\"memberId\":\"dhzpdl1\",\"memberName\":\"anfqud1\"},{\"seq\":2,\"memberId\":\"dhzpdl2\",\"memberName\":\"anfqud2\"},{\"seq\":3,\"memberId\":\"dhzpdl2\",\"memberName\":\"anfqud3\"}]";
        List<TestDto> dtoList = ObjectConverter.jsonToList(listJson);
        System.out.println("dtoList = " + dtoList);

        /* ########################################################################################### */
        // object to map
        TestDto testDto4 = new TestDto(1L, "dhzpdl1", "anfqud1");
        Map<String, Object> objectToMap = ObjectConverter.objectToMap(testDto4);
        System.out.println("objectToMap = " + objectToMap);

        /* ########################################################################################### */
        // json to map
        String testJson = "{\"memberName\":\"heeseong\",\"seq\":\"1\",\"memberId\":\"hshan\"}";
        Map<String, String> jsonToMap = ObjectConverter.jsonToMap(testJson);
        System.out.println("jsonToMap = " + jsonToMap);

        /* ########################################################################################### */
        // map to object
        // key를 가지고 리플렉션이 일어나서 객체의 필드명과 key값이 일치 해야함
        Map<String, String> testMap = new HashMap<>();
        testMap.put("seq", "12");
        testMap.put("memberId", "hshantest");
        testMap.put("memberName", "heeseong2222");
        TestDto mapToObject = ObjectConverter.mapToObject(testMap, TestDto.class);
        System.out.println("mapToObject = " + mapToObject);

        /* ########################################################################################### */
        // json to object
        String testJson2 = "{\"memberName\":\"heeseong\",\"seq\":\"1\",\"memberId\":\"hshan\"}";
        TestDto jsonToObject = ObjectConverter.jsonToObject(testJson2, TestDto.class);
        System.out.println("jsonToObject = " + jsonToObject);
    }
}
