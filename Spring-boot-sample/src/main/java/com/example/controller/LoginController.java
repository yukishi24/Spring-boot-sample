package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
