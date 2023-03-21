package com.example.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

  /**
   * DBの例外処理
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
   * その他例外処理
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
