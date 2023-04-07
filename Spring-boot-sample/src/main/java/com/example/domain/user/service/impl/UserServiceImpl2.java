package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserRepository;

@Service
@Primary
public class UserServiceImpl2 implements UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * ユーザー登録
	 */
	@Transactional
	@Override
	public void signup(MUser user) {
		//存在チェック
		boolean exists = repository.existsById(user.getUserId());
		if (exists) {
			throw new DataAccessException("ユーザ-が既に存在しています。") {
			};
		}
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");

		//パスワード暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		//insert
		repository.save(user);
	}

	/**
	 * ユーザ取得
	 */
	@Override
	public List<MUser> getUsers(MUser user) {
		return repository.findAll();
	}

	/**
	 * ユーザー一件取得
	 */
	@Override
	public MUser getUserOne(String userId) {
		return repository.findById(userId).orElse(null);
	}

	/**
	 * ユーザー更新
	 */
	@Transactional
	@Override
	public void updateOne(String userId, String password, String userName) {
		String encryptPassword = encoder.encode(password);
		repository.updateUser(userId, password, userName);
	}

	/**
	 * ユーザー削除
	 */
	@Transactional
	@Override
	public void deleteOne(String userId) {
		repository.deleteById(userId);
	}

	/**
	 * ログインユーザの取得
	 */
	@Override
	public MUser getLoginUser(String userId) {
		return repository.findLoginUser(userId);
	}
}
