package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Login画面への遷移コントローラー
 * 
 * @author yukishi
 *
 */
@Controller
public class LoginController {

  /**
   * ログイン画面への遷移
   * 
   * @return
   */
  @GetMapping("/login")
  public String getLogin() {
    return "login/login";
  }

  /**
   * ユーザー一覧画面にリダイレクト
   * 
   * @return
   */
  @PostMapping("/login")
  public String postLogin() {
    return "redirect:/user/list";
  }
}
