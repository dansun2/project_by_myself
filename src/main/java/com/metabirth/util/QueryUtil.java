package com.metabirth.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// xml 파일에서 쿼리를 읽어와서 관리함
public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<>();

    // XML에서 쿼리를 로드하는 정적 블록
    static {
        loadQueries();
    }
    
    private static void loadQueries() {
        try {
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("queries.xml");
            
            if (inputStream == null) {
                throw new RuntimeException("queries.xml 파일을 찾을 수 없습니다.");
            }

            // DocumentBuilderFactory를 사용하여 DocumentBuilder 인스턴스를 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // InputStream으로부터 XML 문서를 파싱하여 Document 객체 생성
            Document document = builder.parse(inputStream);
            // 문서의 구조를 정규화 (노드의 표준 형태로 변환)
            document.getDocumentElement().normalize();
            // "query" 태그를 가진 모든 노드를 가져옴
            NodeList nodeList = document.getElementsByTagName("query");

            // 각 "query" 노드를 반복하여 처리
            for (int i = 0; i < nodeList.getLength(); i++) {
                // 현재 노드를 Element로 캐스팅
                Element queryElement = (Element) nodeList.item(i);
                // "id" 속성 값을 가져옴
                String id = queryElement.getAttribute("id");
                // 쿼리의 텍스트 내용을 가져와서 공백을 제거
                String sql = queryElement.getTextContent().trim();
                // ID를 키로, SQL 쿼리를 값으로 하여 맵에 저장
                queries.put(id, sql);
            }
        } catch (Exception e) {
            // 예외 발생 시 RuntimeException으로 감싸서 다시 던짐
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }

    // 쿼리ID로 SQL 문자열을 가져옴
    public static String getQuery(String id) {
        return queries.get(id);
    }
}