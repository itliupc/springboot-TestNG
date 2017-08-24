package com.wafer.interfacetestdemo.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wafer.interfacetestdemo.domain.User;
import com.wafer.interfacetestdemo.security.auth.AuthService;
import com.wafer.interfacetestdemo.security.utils.JwtTokenUtil;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

  @Autowired
  private UserDetailsService userDetailsService;
  
  @Autowired
  private AuthService<User> authService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Value("${token.header}")
  private String tokenHeader;

  @Value("${token.head} ")
  private String tokenHead;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader(this.tokenHeader);
    if (authHeader != null && authHeader.startsWith(tokenHead)) {
      final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
      String username = jwtTokenUtil.getUsernameFromToken(authToken);

      logger.info("checking authentication {}.", username);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
        // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
        // 但简单验证的话，可以采用直接验证token是否合法来避免昂贵的数据查询
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(userDetails, null,
                  userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          logger.info("authenticated user {}, setting security context", username);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          
          // 认证通过， 刷新Token ，将Token存放在response中
          String newToken = authService.refresh(authToken);
          response.setHeader(tokenHeader, newToken);
        }
      }
    }

    chain.doFilter(request, response);
  }
}
