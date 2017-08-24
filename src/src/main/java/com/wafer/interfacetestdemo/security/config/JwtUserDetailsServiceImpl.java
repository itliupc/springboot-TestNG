package com.wafer.interfacetestdemo.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.domain.User;
import com.wafer.interfacetestdemo.service.UserService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // 获取User数据库中对象
    User user = userService.findByUserName(username);

    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    } else {
      return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
          true, true, true, true, mapToGrantedAuthorities(Arrays.asList(user.getUserAuthority())));
    }
  }

  /**
   * 整合Roles
   * 
   * @param authorities
   * @return
   */
  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Integer> authorities) {
    List<GrantedAuthority> grants = new ArrayList<GrantedAuthority>();
    if(null != authorities && authorities.size()>0){
      for(int authoritie: authorities){
        if(0 == authoritie){
          grants.add(new SimpleGrantedAuthority("ADMIN"));
        }else{
          grants.add(new SimpleGrantedAuthority("USER"));
        }
      }
    }
//    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return grants;
  }
}
