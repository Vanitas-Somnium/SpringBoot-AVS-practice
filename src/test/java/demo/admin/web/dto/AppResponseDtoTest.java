package demo.admin.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppResponseDtoTest {

    @Test
    public void lombok_performance_test() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        AppResponseDto dto = new AppResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
        /* assertThat
            - assertj라는 테스트 검증 라이브러리의 검증 메소드
            - 검증하고 싶은 대상을 메소드 인자로 받는다.
            - 메소드 체이닝이 지원되어 isEqualTo와 같이 메소드를 이어서 사용할 수 있음

           isEqualTo
            - assertj의 동등 비교 메소드
            - assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공.

           ※ Junit에 비교하여 assertj의 장점
           - CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않음
                - Junit의 assertThat을 쓰게 되면 is()와 같이 CoreMatchers 라이브러리가 필요
           - 자동완성이 좀 더 확실하게 지원됨
                - IDE에서는 CoreMatchers와 같은 Matcher 라이브러리의 자동완성 지원이 약함
         */
    }
}
