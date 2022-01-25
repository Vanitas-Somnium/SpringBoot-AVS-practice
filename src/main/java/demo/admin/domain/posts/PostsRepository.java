package demo.admin.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/* Repository
    - 인터페이스로 생성
    - JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드 자동 생성
    - @Repository 추가할 필요 x, Entity 클래스와 기본 Entity Repository는 함께 위치해야함.
        둘은 밀접한 관계로 Entity는 기본 Repository없이는 제대로 역할 수행 불가.
    - 나중에 Entity와 기본 Repository는 함께 움직여야하므로 도메인 패키지에서 함께 관리.
 */
public interface PostsRepository extends JpaRepository<Posts, Long>{
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}

/* @Query
    규모가 잇는 프로젝트에서의 데이터 조회는 FK의 조인, 복잡한 조건등으로 인해
    이런 Entity 클래스만으로 처리하기 어려워 조회용 프레임워크를 추가로 사용함.
    대표적으로 querydsl, jooq, MyBatis등이 있음.
    조회는 위 3가지 프레임워크 중 하나를 통해 조회, 등록/수정/삭제 등은 SpringData.Jpa를 통해 진행.
    querydsl을 추천.

    Querydsl을 추천하는 이유
    1. 타입 안정성이 보장됨.
    단순한 문자열로 쿼리를 생성하는 것이 아니라, 메소드를 기반으로 쿼리를 생성.
    오타나 존재하지 않는 칼럼명을 명시할 경우 IDE에서 자동으로 검출됨.

    2. 국내 많은 회사에서 사용중
    JPA를 적극적으로 사용하는 회사는 Querydsl을 적극적으로 사용.

    3. 레퍼런스가 많음.
    국내 자료가 많음.
 */
