package com.house.apigateway.service;

import com.google.common.collect.Lists;
import com.house.apigateway.bean.User;
import com.house.apigateway.common.helper.BeanHelper;
import com.house.apigateway.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登录，注册，个人信息服务
 * 
 */
@Service
public class AccountService {

  @Autowired
  private UserMapper userMapper;

  @Value("${domain.name}")
  private String domainName;

  @Autowired
  private FileService fileService;

  public boolean isExist(String email) {

    User user = new User();
    user.setEmail(email);
    List<User> userList = userMapper.getUserList(user);
    if (userList.isEmpty()) {
      return false;
    }
    return true;
  }

  public boolean addAccount(User account) {

    if (account.getAvatarFile() != null) {
      List<String> imags = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
      account.setAvatar(imags.get(0));
    }
    account.setEnableUrl("http://"+domainName+"/accounts/verify");//激活地址
    BeanHelper.setDefaultProp(account, User.class);
    userMapper.addUser(account);
    return true;
  }

  public boolean enable(String key) {
    userMapper.enable(key);
    return true;
  }
}
