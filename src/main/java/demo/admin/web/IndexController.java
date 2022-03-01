package demo.admin.web;

import demo.admin.config.auth.LoginUser;
import demo.admin.config.auth.dto.SessionUser;
import demo.admin.domain.posts.Posts;
import demo.admin.service.posts.PostsService;
import demo.admin.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @PageableDefault(sort="id", direction=Sort.Direction.DESC)Pageable pageable){
        Page<Posts> Posts = postsService.pageList(pageable);
        BufferedImage img = null;
        String targetImage = new String("btnG_official.png");
        // 로컬 파일 이미지 업로딩
        try {
            File imageFile = new File("C:\\Users\\lg\\IdeaProjects\\demo\\src\\main\\resources\\images\\"+targetImage);
            img = ImageIO.read(imageFile);
            ImageIO.write(img,"png",new File("tmpImage.png"));
            byte[] ImageBytes = Files.readAllBytes(Paths.get("tmpImage.png"));
            Base64.Encoder encoder = Base64.getEncoder();
            String Encoding = "data:image/png;base64," + encoder.encodeToString(ImageBytes);
            System.out.println(img.toString());
            model.addAttribute("Naver_img",Encoding);
        } catch (IOException e) {
            System.out.println("Error");
        }


        System.out.println(Posts.getContent());
        model.addAttribute("posts", postsService.pageList(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        model.addAttribute("hasNext", Posts.hasNext());
        model.addAttribute("hasPrev",Posts.hasPrevious());

        if (user!=null){
            model.addAttribute("userNames",user.getName());
            System.out.println(user.getName());
            model.addAttribute("userNamesLength",880-user.getName().length()*10);
            System.out.println(880-user.getName().length());
        }


        return "index";
    }

    /* (SessionUser)httpSession.getAttribute("user")
        - 앞서 작성된 CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser를 저장하도록 구성
        - 로그인 성공시 httpSession.getAttribute("user")에서 값을 가져올 수 있음
       if(user!=null)
        - 세션에 저장된 값이 있을 때만 model에 userName으로 등록
        - 세션에 저장된 값이 없다면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 됨.
     */

    /* (개선 후 )
        LoginUser SessionUser user
        - 기존에 (User) httpSession.getAttribute("user")로 가져오던 세션 정보값이 개선됨
        - 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 됨.
     */
    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable Long id, @LoginUser SessionUser user, Model model){
        PostsResponseDto dto = postsService.findById(id);
        postsService.updateView(id);
        System.out.println(id);
        model.addAttribute("post",dto);

        if (user!=null){
            model.addAttribute("userNames",user.getName());
            model.addAttribute("userNamesLength",880-user.getName().length()*10);
            System.out.println(880-user.getName().length());
        }

        return "posts-read";
    }

    @GetMapping("posts/save")
    public String postsSave(@LoginUser SessionUser user, Model model){
        if (user!=null){
            model.addAttribute("userNames",user.getName());
            System.out.println(user.getName());
            model.addAttribute("userNamesLength",880-user.getName().length()*10);
            System.out.println(880-user.getName().length());
        }
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, @LoginUser SessionUser user, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        System.out.println("update");
        if (user!=null){
            model.addAttribute("userNames",user.getName());
            model.addAttribute("userNamesLength",880-user.getName().length()*10);
            System.out.println(880-user.getName().length());
        }

        return "posts-update";
    }

    @GetMapping("posts/search")
    public String search(String keyword, Model model, @LoginUser SessionUser user,@PageableDefault(sort="id",direction= Sort.Direction.ASC)Pageable pageable){
        Page<Posts> Posts = postsService.pageList(pageable);
        BufferedImage img = null;
        String targetImage = new String("btnG_official.png");
        // 로컬 파일 이미지 업로딩
        try {
            File imageFile = new File("C:\\Users\\lg\\IdeaProjects\\demo\\src\\main\\resources\\images\\"+targetImage);
            img = ImageIO.read(imageFile);
            ImageIO.write(img,"png",new File("tmpImage.png"));
            byte[] ImageBytes = Files.readAllBytes(Paths.get("tmpImage.png"));
            Base64.Encoder encoder = Base64.getEncoder();
            String Encoding = "data:image/png;base64," + encoder.encodeToString(ImageBytes);
            System.out.println(img.toString());
            model.addAttribute("Naver_img",Encoding);
        } catch (IOException e) {
            System.out.println("Error");
        }


        System.out.println(Posts.getContent());
        model.addAttribute("posts", postsService.pageList(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        model.addAttribute("hasNext", Posts.hasNext());
        model.addAttribute("hasPrev",Posts.hasPrevious());

        if (user!=null){
            model.addAttribute("userNames",user.getName());
            System.out.println(user.getName());
            model.addAttribute("userNamesLength",880-user.getName().length()*10);
            System.out.println(880-user.getName().length());
        }

        Page<Posts> searchList = postsService.search(keyword,pageable);
        model.addAttribute("searchList",searchList);
        return "posts-search";
    }


}
