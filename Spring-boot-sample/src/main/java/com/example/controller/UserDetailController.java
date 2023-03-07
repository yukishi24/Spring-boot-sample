package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

@Controller
@RequestMapping("/user")
public class UserDetailController {

  @Autowired
  private UserService userService;
  @Autowired
  private ModelMapper modelMapper;

  // ユーザー詳細を表示
  @GetMapping("/detail/{userId:.+}")
  public String getUser(UserDetailForm detailForm, Model model,
      @PathVariable("userId") String userId) {
    // ユーザー一件取得
    MUser user = userService.getUserOne(userId);
    System.out.println(user.getBirthday());
    user.setPassword(null);

    // MUserをformに変換
    detailForm = modelMapper.map(user, UserDetailForm.class);

    // modelに登録
    model.addAttribute("detailFrom", detailForm);

    // ユーザー詳細画面の表示
    return "user/detail";
  }
}
