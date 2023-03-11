package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public String getUser(UserDetailForm form, Model model,
      @PathVariable("userId") String userId) {
    // ユーザー一件取得
    MUser user = userService.getUserOne(userId);
    user.setPassword(null);
    form.setUserName(user.getUserName());
    form.setBirthday(user.getBirthday());
    form.setAge(user.getAge());
    // MUserをformに変換
    form = modelMapper.map(user, UserDetailForm.class);

    // modelに登録
    model.addAttribute("detailFrom", form);

    // ユーザー詳細画面の表示
    return "user/detail";
  }

  // ユーザー更新処理
  @PostMapping(value = "/detail", params = "update")
  public String updateUser(UserDetailForm form, Model model) {

    // ユーザー更新
    userService.updateOne(form.getUserId(), form.getPassword(), form.getUserName());

    // ユーザー一覧のリダイレクト
    return "redirect:/user/list";
  }

  // ユーザー削除(一件)
  @PostMapping(value = "/detai", params = "delete")
  public String deleteUser(UserDetailForm form, Model model) {

    // ユーザー削除
    userService.deleteOne(form.getUserId());

    // ユーザー一覧画面にリダイレクト
    return "redirect:/user/list";
  }
}
