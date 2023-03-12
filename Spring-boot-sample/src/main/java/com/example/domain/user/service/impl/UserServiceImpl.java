package com.example.domain.user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper mapper;

  // ユーザー登録
  @Override
  public void signup(MUser user) {
    user.setDepartmentId(1);// 部署
    user.setRole("ROLE_GENERAL");// ロール
    mapper.insertOne(user);
  }

  // ユーザーの取得
  @Override
  public List<MUser> getUsers(MUser user) {
    return mapper.findMany(user);
  }

  // ユーザー取得(一件)
  @Override
  public MUser getUserOne(String userId) {
    return mapper.findOne(userId);
  }

  // ユーザー更新(一件)
  @Override
  public void updateOne(String userId, String password, String userName) {
    mapper.updateOne(userId, password, userName);
  }

  // ユーザー削除(一件)
  @Override
  public void deleteOne(String userId) {
    int count = mapper.deleteOne(userId);
  }
}
