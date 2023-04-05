"use strict";
//画面ロードの処理
jQuery(function($) {
	//登録ボタンを押した時の処理
	$("#btn-signup").click(function(event) {
		//ユーザー登録
		signupUser();
	});
});

//ユーザー登録処理
function signupUser() {
	//バリテーショ結果をクリア
	removeValidResult();
	//フォームの値を取得
	var formData = $("#signup-form").serializeArray();
	//ajax通信
	$.ajax({
		type: "POST",
		cache: false,
		url: "/user/signup/rest",
		data: formData,
		dataType: "json",
	}).done(function(data) {
		//ajax成功時の処理
		console.log(data);

		if (data.result === 90) {
			//validationエラー時の処理
			$.each(data.errors, function(key, value) {
				reflectValidResult(key, value);
			});
		} else if (data.result === 0) {
			alert("ユーザーを登録しました。");
			//ログイン画面にリダイレクト
			window.location.href = "/login";
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
		//ajax失敗時の処理
		alert("ユーザー登録に失敗しました。");
	}).always(function() {
		//常に実行する処理
	});
}

function removeValidResult() {
	$(".is-invalid").removeClass("is-invalid");
	$(".invalid-feedback").remove();
	$(".text-danger").remove();
}

function reflectValidResult(key, value) {
	if (key === "gender") {
		$("input[name=" + key + "]").addClass("is-invalid");
		$("input[name=" + key + "]")
			.parent()
			.parent()
			.append('<div class="text-danger">' + value + "</div>");
	} else {
		$("input[id=" + key + "]").addClass("is-invalid");
		$("input[id=" + key + "]").after(
			'<div class="invalid-feedback">' + value + "</div>"
		);
	}
}