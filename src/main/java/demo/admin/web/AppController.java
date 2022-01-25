package demo.admin.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import demo.admin.web.dto.AppResponseDto;
//import org.springframework.stereotype.Controller;

/* RestController
    - 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다
    - @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할
       수 있게 만들어줌.
 */
@RestController
public class AppController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/hello/dto")
    public AppResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new AppResponseDto(name,amount);
    }
    /* RequestParam
        - 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
        - 외부에서 name(@RequestParam("name"))이란 이름으로 넘긴 파라미터를 메소드 파라미터 name(String name)에 저장.
     */
}
