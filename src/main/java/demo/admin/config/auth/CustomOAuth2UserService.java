package demo.admin.config.auth;

import demo.admin.config.auth.dto.OAuthAttributes;
import demo.admin.config.auth.dto.SessionUser;
import demo.admin.domain.user.User;
import demo.admin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        user = user.authenticatedRole();
        httpSession.setAttribute("user", new SessionUser(user));
        System.out.printf("changed role:%s",user.getRole());
        System.out.printf("userName:%s",user.getName());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}

/*
    registrationId
    - 현재 로그인 진행 중인 서비스를 구분하는 코드
    - 지금은 구글만 사용하는 불필요한 값이나, 이후 네이버 로그인 연동시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용됨

    userNameAttributeName
    - OAuth2 로그인 진행시 키가 되는 필드값을 이야기함. Primary Key와 같은 의미
    - 구글의 경우 기본적으로 코드를 지원하나, 네이버, 카카오 등은 기본 지원하지 않음.
        구글의 기본 코드는 sub
    - 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용

    OAuthAttributes
    - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
    - 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용

    SessionUser
    - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
    - 왜 User 클래스를 안쓰고 새로 만들어서 쓰는가?
 */