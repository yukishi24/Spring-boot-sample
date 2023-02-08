package com.example.form;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 新規ユーザー登録画面用のバインドレコード
 * @author yukishi
 *
 */
public record SignupForm(
    String userId,
    String password,
    String userName,
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    Date birthday,
    Integer age,
    Integer gender
    ) {

}
