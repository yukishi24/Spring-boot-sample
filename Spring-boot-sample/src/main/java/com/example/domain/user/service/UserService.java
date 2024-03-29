package com.example.domain.user.service;

import java.util.List;
import com.example.domain.user.model.MUser;

public interface UserService {

  // ユーザー登録
  public void signup(MUser user);

  // ユーザー取得
  public List<MUser> getUsers(MUser user);

  // ユーザー取得(1件)
  public MUser getUserOne(String userId);

  // ユーザー更新(一件)
  public void updateOne(String userId, String password, String userName);

  // ユーザー削除(一件)
  public void deleteOne(String userId);

  // ログインユーザー情報取得
  public MUser getLoginUser(String userId);
}
