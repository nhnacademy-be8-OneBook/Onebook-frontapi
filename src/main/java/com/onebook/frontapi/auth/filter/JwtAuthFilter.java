package com.onebook.frontapi.auth.filter;

import com.onebook.frontapi.auth.dto.MemberInfoResponse;
import com.onebook.frontapi.feign.auth.AuthFeignClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthFeignClient authFeignClient;

    // JWT 기반 인증: Authorization 쿠키에 있는 jwt 토큰 가져다가 검증함.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        // 쿠키가 없으면 여기서 걸려서 홈페이지에 쿠키 없이 접근하면 login 페이지로 이동함.
        // -> 해결: 쿠키가 없으면 JWT 필터 태우지 않고, 그냥 넘김.
        if (Objects.isNull(cookies) || Arrays.stream(cookies).noneMatch(e -> e.getName().equals("Authorization"))) {
            filterChain.doFilter(request, response);
            return;
        }

        for (Cookie c : cookies) {

            if (c.getName().equals("Authorization")) {

                MemberInfoResponse memberInfoResponse = authFeignClient.getInfoByAuthorization("Bearer " + c.getValue());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        memberInfoResponse.loginId(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + memberInfoResponse.role()))
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);

    }
}
//    public boolean isAuthorization(Cookie[] cookies) {
//        for(Cookie c : cookies) {
//            if(c.getName().equals("Authorization")) {
//                return false;
//            }
//        }
//
//        return true;
//    }
