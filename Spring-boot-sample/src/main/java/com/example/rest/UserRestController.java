package com.example.rest;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;
import com.example.form.UserDetailForm;
import com.example.form.UserListForm;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MessageSource messageSource;

	/**
	 * ユーザーを検索
	 * 
	 * @param form
	 * @return
	 */
	@GetMapping("/get/list")
	public List<MUser> getUserList(UserListForm form) {

		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);

		//ユーザー一覧取得
		List<MUser> userList = userService.getUsers(user);
		return userList;
	}

	@PostMapping("/signup/rest")
	public RestResult postSignup(@Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult,
			Locale locale) {
		//入力チェック
		if (bindingResult.hasErrors()) {
			//チェック結果:NG
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, f -> messageSource.getMessage(f, locale)));
			//エラー結果の返却
			return new RestResult(90, errors);
		}
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		//ユーザー登録
		userService.signup(user);
		//結果の返却
		return new RestResult(0, null);
	}

	/**ユーザー更新
	 * @param form
	 * @return
	 */
	@PutMapping("/update")
	public int updateUser(UserDetailForm form) {
		//		ユーザー更新
		userService.updateOne(form.getUserId(), form.getPassword(), form.getUserName());
		return 0;
	}

	/**ユーザー削除
	 * @param form
	 * @return
	 */
	@DeleteMapping("/delete")
	public int deleteUser(UserDetailForm form) {
		//		ユーザー削除
		userService.deleteOne(form.getUserId());
		return 0;
	}
}
