package demo.admin.domain.posts;

import demo.admin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/* 주요 Annotation을 클래스에 가깝게

    @Entity는 JPA의 어노테이션, @Getter와 @NoArgsConstructor는
        롬복의 어노테이션.
    롬복은 코드를 단순화시켜주나 필수 어노테이션은 아님.
    => 주요 어노테이션인 @Entity를 클래스에 가깝게, 나머지는 위로.
    ==> 코틀린등의 새 언어 전환으로 롬복이 더이상 필요없을 경우 쉽게 삭제.

 */

/*
    @Entity
        - 테이블과 링크될 클래스임을 나타냄
        - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로
            테이블 이름을 칭함.
            ex) SalesManager.java => sales_manager table
        ※ Entity의 PK는 Auto_increment를 추천. 복합키로 PK를 잡을경우
          다른 테이블에서 복합키 전부를 갖고있거나 중간테이블을 하나 더 둬야하는 상황,
          인덱스에 좋은 영향 x, PK 전체를 수정해야하는 등의 문제 발생.
          => 주민번호, 복합키등은 유니크 키로 별도 추가를 권장.
    @Id
        - 해당 테이블의 PK필드를 나타냄
    @GenenratedValue
        - PK의 생성 규칙을 나타냄
        - 스프링부트 2.0에서는 GenerationTYPE.IDENTITY 옵션을 추가해야하만 auto_increment가 됨
    @Column
        - 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됨.
        - 사용하는 이유? => 기본값 외에 추가 변경이 필요한 옵션이 있으면 사용
        - 문자열의 경우 VARCHAR(255)가 기본값 -> 500 사이즈 변경/ 타입을 TEXT로 변환 등...

    //Lombok Annotation
    @NoArgsConstructor
        - 기본 생성자 자동 추가
        - publicPosts(){}와 같은 효과
    @Getter
        - 클래스 내 모든 필드의 Getter 메소드 자동생성
    @Builder
        - 해당 클래스의 빌더 패턴 클래스를 생성
        - 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함.

    ※Getter만 있고 Setter가 없다?
    => 해당 클래스의 인스턴스 값들이 언제 어디서 변하는지 코드상으로 명확하게 구분 불가능.
    ==> Entity 클래스에서는 "절대로 Setter 메소드를 만들지 않음"
    ==> 필드의 값 변경이 필요하면 명확히 목적과 의도를 나타낼 수 있도록 메소드를 추가해야함.
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
