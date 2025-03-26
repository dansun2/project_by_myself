package com.metabirth.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static final HikariDataSource dataSource;

    // 스태틱블럭-static 변수들이 메모리에 올라가기 전에 블럭 안의 소스코드를 실행시켜서 static 변수를 초기화함
    static {
        try {
            /*
             * properties
             * 키 - 값을 쌍으로 저장하는 방식이다.
             * 주로 설정 정보나 구성 데이터를 관리하는데 유용하게 사용된다.
             * */
            Properties props = new Properties();
            /*
             * properties.load 외부 파일을 읽어오는 역할을 수행
             * JDBCConnection.class.GetClassLoader() : 클래스를 메모리에 로드하는 역할을 수행하며 이를 통해 설정 파일에 접근이 가능하다.
             * getResourceAsStream ("config") : 매개변수로 전달된 파일을 스트림으로 가져오는 역할을 수행한다.
             * */
            props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));
            HikariConfig config = new HikariConfig();

            /* DB 접속을 위한 설정 정보 */
            config.setJdbcUrl(props.getProperty("db.url")); // db.url이 key고 value를 가져옴
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // 최대 커넥션 객체는 10개로 제한
            config.setMaximumPoolSize(10);

            // 최소 커넥션
            config.setMinimumIdle(5);

            // 유휴 상태면 커넥션 닫기. 즉, 30초 동안 아무런 요청이 없으면 커넥션을 닫는다
            config.setIdleTimeout(30000);

            // 30분 후 커넥션을 새로 생성한다. -> 연결 스트리밍이 길어지면 연결에 불안정이 생길 수 있음?
            config.setMaxLifetime(1800000);

            // 최대 2초 대기 후 타임 아웃 -> 실패로 인식하고 연결 요청을 다시 함.
            config.setConnectionTimeout(2000);

            dataSource = new HikariDataSource(config);

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    // 커넥션 풀에서 연결되어 있는 객체를 꺼내오는 메서드
    public static Connection getConnection() throws SQLException {
        /*
         * DB에는 세션이라는 것이 존재한다.
         * 이러한 세션은 DB에 연결한 시점으로 생성되며
         * 이를 기점으로 트랜잭션 임시 데이터, 캐싱 등의 데이터를 관리하게 된다.
         *
         * 여기서 지금 우리가 사용하는 hikari의 경우 DB의 커넥션 객체를 몇개 생성하고
         * 다른 사용자에게 빌려주고 반환받고 다시 빌려주는 형식으로 동작된다.
         * 이러한 과정에서 세션이 중볻되는 문제가 발생할 수 있다고 생각될 수 있으나.
         * 다시 빌려주는 과정에서 JDBC 내부에서 세션을 새롭게 생성하기 때문에 신경쓰지 않아도 된다.
         * */
        // connection pool에서 connection 객체를 꺼내옴
        return dataSource.getConnection();
    }

    // hikaricp 전체 커넥션 풀을 종료하는 메서드
    // application -> 전체 종료 -> connection pool 더 이상 사용 불가.
    public static void close(){
        if(dataSource != null){
            dataSource.close();
        }
    }

    public static void printConnectionPoolStatus() {
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("[hikariCp 커넥션 풀 상태 ]");
        System.out.println("총 커넥션 수 (total Connections) : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 (Active Connections) : " + poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 수 (IDLE Connections) : " + poolMXBean.getIdleConnections());
        System.out.println("대기 중인 커넥션 요청 수 (Pending Threads) : " + poolMXBean.getThreadsAwaitingConnection());
    }
}
