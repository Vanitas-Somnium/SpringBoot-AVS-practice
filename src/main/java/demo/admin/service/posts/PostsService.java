package demo.admin.service.posts;

import demo.admin.domain.posts.Posts;
import demo.admin.domain.posts.PostsRepository;
import demo.admin.web.dto.PostsListResponseDto;
import demo.admin.web.dto.PostsResponseDto;
import demo.admin.web.dto.PostsSaveRequestDto;
import demo.admin.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

    @Transactional
    public Page<Posts> search(String keyword, Pageable pageable){
        Page<Posts> postsList = postsRepository.findByTitleContaining(keyword,pageable);
        System.out.println(postsList);
        return postsList;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public Page<Posts> pageList(Pageable pageable){
        return postsRepository.findAll(pageable);
    }

    @Transactional
    public int updateView(Long id){
        return postsRepository.updateView(id);
    }
    /* findAllDesc
        트랜젝션 어노테이션(@Transactional)에 옵션이 하나 추가됨.
        (readOnly = true)를 주면 트랜젝션 범위는 유지하되, 조회 기능만 남겨두어
        조회 속도가 개선되기때문에 등록, 수정, 삭제 기능이 없는 서비스 메소드에서 사용 추천.

        .map(PostsListResponseDto::new) == .map(posts -> new PostsListResponseDto(posts))
        : postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드

     */

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }
}
