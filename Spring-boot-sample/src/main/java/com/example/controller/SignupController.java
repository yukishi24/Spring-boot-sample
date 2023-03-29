package com.example.controller;

import java.util.Locale;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザー登録画面の処理を分けるクラス[Controller]
 * 
 * @author yukishi
 *
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {

  @Autowired
  private UserApplicationService userApplicationService;

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * ユーザー登録画面の表示
   * 
   * @param model
   * @return
   */
  @GetMapping("/signup")
  public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {

    // 性別の取得
    Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
    model.addAttribute("genderMap", genderMap);

    // ユーザー登録画面に遷移
    return "user/signup";

  }

  /**
   * ユーザー登録画面処理
   * 
   * @return
   */
  @PostMapping("/signup")
  public String postSignup(Model model, Locale locale,
      @ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult) {

    // 入力チェック
    if (bindingResult.hasErrors()) {
      // NG:ユーザー登録に戻る
      return getSignup(model, locale, form);
    }
    // ログの出力
    log.info(form.toString());

    // formをMUserクラスに変換
    MUser user = modelMapper.map(form, MUser.class);

    // マッピングされないため手動でマップング
    user.setBirthday(form.birthday());

    // formからMUserに変換時にうまくいかないため、Birthdayだけ別で変換。
    user.setBirthday(form.birthday());
    // ユーザー登録
    userService.signup(user);

    // ログイン画面にリダイレクト
    return "redirect:/login";
  }

  /**
   * DB関連の例外処理
   * 
   * @param e
   * @param model
   * @return
   */
  @ExceptionHandler(DataAccessException.class)
  public String dataAccessExceptionHandler(DataAccessException e, Model model) {

    // 空文字のセット
    model.addAttribute("error", "");

    // メッセージをmodelに登録
    model.addAttribute("message", "SignupControllerで例外が発生しました。");

    // HTTPのエラーコード
    model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

    return "error";
  }

  /**
   * その他の例外
   * 
   * @param e
   * @param model
   * @return
   */
  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception e, Model model) {

    // 空文字のセット
    model.addAttribute("error", "");

    // メッセージの登録
    model.addAttribute("message", "SignupControllerで例外が発生しました。");

    // HTTPのエラコード(500)をmodelに登録。
    model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

    return "error";
  }
}
