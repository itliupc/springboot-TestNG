package com.wafer.interfacetestdemo.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.domain.Dept;
import com.wafer.interfacetestdemo.domain.DeptUser;
import com.wafer.interfacetestdemo.domain.User;
import com.wafer.interfacetestdemo.security.auth.AuthService;
import com.wafer.interfacetestdemo.service.DeptService;
import com.wafer.interfacetestdemo.service.DeptUserService;
import com.wafer.interfacetestdemo.service.UserService;
import com.wafer.interfacetestdemo.vo.AccountVo;
import com.wafer.interfacetestdemo.vo.LoginUserVo;
import com.wafer.interfacetestdemo.vo.ResponseResult;
import com.wafer.interfacetestdemo.vo.UserVo;

@RestController
@RequestMapping(Constant.CONTROLLER_PATH)
@Transactional
public class UserController {

  @Autowired
  UserService userService;
  
  @Autowired
  DeptUserService deptUserService;
  
  @Autowired
  DeptService deptService;
  
  @Autowired
  AuthService<User> authService;

  Logger logger = LoggerFactory.getLogger(UserController.class);

  /***
   * 用户登陆
   * @param vc 获取用户名和密码
   * @return Token，用户数据交互的凭证
   */
  @RequestMapping(value = Constant.LOGIN, method = RequestMethod.POST)
  public ResponseResult login(@RequestBody AccountVo vc) {

    logger.debug("login request param is {}.", vc);
    String account = vc.getAccount();
    String password = vc.getPassword();
    
    // 登陆：验证密码，生成Token
    String token  = authService.login(account, password);
    
    User user = userService.findByUserName(account);
    user.setLatestLoginTime(new Date());
    userService.updateUserbyUserId(user);
    
    Dept dept = deptService.getDeptByUserId(user.getUserId());
    
    LoginUserVo loginUser = new LoginUserVo();
    loginUser.setToken(token);
    loginUser.setUser(user);
    loginUser.setDept(dept);
    
    return ResponseResult.success(loginUser);
  }  
  
  /***
   * 用户登出
   * @return 登出成功 or 失败
   */
  @RequestMapping(value = Constant.LOGOUT, method = RequestMethod.POST)
  public ResponseResult logout(@RequestBody AccountVo vc) {

    // 登出，设置token不可用
    boolean logout  = authService.logout(vc.getAccount());
    
    return ResponseResult.success(String.valueOf(logout));
  }

  /**
   * 查询user信息
   * @return 封装的user list信息
   */
  @RequestMapping(value = Constant.USERS, method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public ResponseResult userList(){
    List<UserVo> userList = userService.getUserVoList();
    return ResponseResult.success(userList);
  }
  
  /**
   * 新建user
   * @param user基本信息
   * @return 封装的user信息
   */
  @RequestMapping(value = Constant.USER, method = RequestMethod.POST)
  public ResponseResult userCreate(@RequestBody UserVo userInfo) {
    User userForCompareName = userService.findByUserName(userInfo.getUserName());
    User userForCompareEmail = userService.getUserbyEmail(userInfo.getEmail());
    if (null != userForCompareName) {
      return ResponseResult.failure(Constant.USER_NAME_DUPLICATE);
    } else if (null != userForCompareEmail) {
      return ResponseResult.failure(Constant.EMAIL_DUPLICATE);
    } else {
      User user = new User();
      user.setEmail(userInfo.getEmail());
      user.setUserName(userInfo.getUserName());
      user.setCreateTime(new Date());
      user.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
      // 暂定设置所有的角色为user
      user.setUserAuthority(1);
      userService.userSave(user);

      DeptUser deptUser = new DeptUser();
      deptUser.setDeptId(userInfo.getDeptId());
      deptUser.setUserId(user.getUserId());
      deptUser.setCreateTime(new Date());
      deptUserService.deptUserSave(deptUser);

      List<UserVo> userList = userService.getUserVoList();
      return ResponseResult.success(userList);
    }
  }
  
  /**
   * 根据userId删除user信息，实际是将user进行伪删除，更新status字段状态即可
   * @param userId
   * @return 封装的user信息
   */
  @RequestMapping(value = Constant.USER_DELETE, method = RequestMethod.DELETE)
  public ResponseResult userDelete(@PathVariable long userId){
    
    userService.updateUserStatusByUserId(userId);
    
    deptUserService.removeDeptUserByUserId(userId);
    
    List<UserVo> userList = userService.getUserVoList();
    return ResponseResult.success(userList);
  }
  
  /**
   * 更新user
   * @param user基本信息
   * @return 封装的user信息
   */
  @RequestMapping(value = Constant.USER, method = RequestMethod.PUT)
  public ResponseResult userModify(@RequestBody UserVo user) {
    User userForCompareName =
        userService.getOtherUserbyUserName(user.getUserName(), user.getUserId());
    User userForCompareEmail = userService.getOtherUserbyEmail(user.getEmail(), user.getUserId());
    if (null != userForCompareName) {
      return ResponseResult.failure(Constant.USER_NAME_DUPLICATE);
    } else if (null != userForCompareEmail) {
      return ResponseResult.failure(Constant.EMAIL_DUPLICATE);
    } else {
      User userInfo = userService.getUserbyUserId(user.getUserId());

      if (null != user.getEmail()) {
        userInfo.setEmail(user.getEmail());
      }
      if (null != user.getUserName()) {
        userInfo.setUserName(user.getUserName());
      }
      userInfo.setUpdateTime(new Date());
      userService.userSave(userInfo);

      DeptUser deptUser = deptUserService.getDeptUserByUserId(user.getUserId());
      if (null == deptUser) {
        deptUser = new DeptUser();
        deptUser.setDeptId(user.getDeptId());
        deptUser.setUserId(user.getUserId());
        deptUser.setCreateTime(new Date());
        deptUserService.deptUserSave(deptUser);
      } else if (deptUser.getDeptId() != user.getDeptId()) {
        deptUser.setDeptId(user.getDeptId());
        deptUserService.deptUserSave(deptUser);
      }

      List<UserVo> userList = userService.getUserVoList();
      return ResponseResult.success(userList);
    }
  }
}

