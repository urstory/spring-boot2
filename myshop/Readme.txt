
Spring Data JPA 를 추가를 하면?

아래와 같은 starter와 jdbc 드라이버를 추가하면, 자동으로 DataSource와
PlatformTransactionManager에 대한 설정이 추가된다.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
			<dependency>
    			<groupId>com.h2database</groupId>
    			<artifactId>h2</artifactId>
    			<scope>runtime</scope>
    		</dependency>

- DataSource (interface - 커넥션 풀)
    - Spring Boot 2에서는 HikariCP
- PlatformTransactionManager (트랜잭션 관리자)
    - Spring 트랜잭션관리를 AOP로 지원을 한다.
- JDBC 드라이버
    - h2 : 인메모리DBMS ----> MySQL JDBC드라이버로 변환
    - web 환경도 같이 같이 사용할 경우엔 http://localhost:8080/h2-console 클라이언트 접속을 할 수 있다.
    - driver class name : org.h2.Driver
    - JDBC URL : jdbc:h2:~/test
    - Username : sa
    - password :


DataSource ----> 커넥션풀 ---------- DBMS
                 DBMS와 미리 여러개의 커넥션을 연결하고 있다.

DataSource를 사용한다는 것은 Connection을 DataSource에게 요청한다.
DataSource에게 얻은 Connection의 close()메소드는 Connection을 끊는것이 아니라,
커넥션풀에 되돌려주는 것.


