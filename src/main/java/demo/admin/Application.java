package demo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/* Application 클래스는 프로젝트의 메인 클래스
    @SpringBootApplication으로 인해 스프링 부트의 자동 설정,
    스프링 Bean 읽기와 생성을 모두 자동으로 설정
    @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에
    항상 최상단에 위치해야함.
 */
/* main 메소드에서 실행하는 SpringApplication.run으로 인해
내장 WAS(웹 어플리케이션 서버)를 실행.
내장 WAS는 별도로 외부에 별도로 WAS를 두지 않고 내부에서 WAS를 실행하는 것.
스프링 부트는 내장 WAS 사용을 권장. => 언제 어디서나 같은 환경에서 스프링부트를 배포 가능하기 때문

 */

/* @EnableJpaAuditing
    - JPA Auditing 어노테이션을 모두 활성화
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
