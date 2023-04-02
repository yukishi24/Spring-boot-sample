package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;

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
