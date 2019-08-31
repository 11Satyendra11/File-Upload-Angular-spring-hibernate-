package com.javatpoint.FileUploadExample.service.interfaces;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.javatpoint.FileUploadExample.entity.UserDetail;

public interface UserService {
	
	public int saveUser(UserDetail userDetail);
	
	public UserDetail getUserDetail(int userId);
	
	public int store(MultipartFile file, int userID ,  HttpSession session);
	
	

}
