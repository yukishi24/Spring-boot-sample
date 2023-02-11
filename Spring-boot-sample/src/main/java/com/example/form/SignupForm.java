package com.example.form;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 新規ユーザー登録画面用のバインドレコード
 * 
 * @author yukishi
 *
 */
public record SignupForm(

    // ユーザーID
    @NotBlank 
    @Email 
    String userId,

    // パスワード
    @NotBlank @Length(min = 4, max = 100) 
    @Pattern(regexp = "^[a-zA-Z0-9]+$") 
    String password,

    // ユーザー名
    @NotBlank 
    String userName,

    // 誕生日
    @DateTimeFormat(pattern = "yyyy/MM/dd") 
    @NotNull 
    Date birthday,

    // 年齢
    @Min(20) @Max(100) 
    Integer age,

    // 性別
    @NotNull 
    Integer gender
    ) {

}
