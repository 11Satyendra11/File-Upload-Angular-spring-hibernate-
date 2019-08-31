package com.javatpoint.FileUploadExample.service.implementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javatpoint.FileUploadExample.DAO.interfaces.UserDAO;
import com.javatpoint.FileUploadExample.entity.UserDetail;
import com.javatpoint.FileUploadExample.service.interfaces.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	public int saveUser(UserDetail userDetail) {
		return userDAO.saveUser(userDetail);
	}
	
	@Transactional
	public UserDetail getUserDetail(int userId) {
		return userDAO.getUserDetail(userId);
	}

	@Transactional
	public int store(MultipartFile file, int userID, HttpSession session) {
		
		Path rootLocation = Paths.get(session.getServletContext().getRealPath("/resources/images"));
		
		System.out.println("rootLocation  ==  " + rootLocation);
			
		System.out.println("UserID ================ " + userID);
		
		UserDetail userDetail = this.getUserDetail(userID);
		 
		 String nameExtension[] = file.getContentType().split("/");

		 String profileImage = userID + "." + nameExtension[1];
		 	
		 System.out.println("ProfileImage  :: " + profileImage);
		 
		 if(userDetail.getUserId() > 0 )
		 {
			 
			if(userDetail.getProfileImage() == null || userDetail.getProfileImage() == " " || userDetail.getProfileImage() == "" )
			{
				System.out.println("inside image Upload IF when getProfileImage() is null ");
				try
				{
					Files.copy(file.getInputStream(),rootLocation.resolve(profileImage));
					int result = userDAO.updateProfileImage(profileImage, userID);	
					System.out.println("=======result "+ result);
					if(result > 0)
						return result;
					else
						return -5;
				}
				catch(Exception exception)
				{
					System.out.println("error while uploading image catch:: " + exception.getMessage());
					return -5;
				}
			}
			else
			{
				System.out.println("inside else ");
				try
				{
					//Files.delete(rootLocation.resolve(profileImage));
					
					Files.delete(rootLocation.resolve(userDetail.getProfileImage()));
					
					Files.copy(file.getInputStream(),rootLocation.resolve(profileImage));
					int result = userDAO.updateProfileImage(profileImage, userID);	
					if(result > 0)
						return result;
					else
						return -5;
				}
				catch(Exception exception)
				{
					System.out.println("Error while uploading image when image is already Exists :: " + exception.getMessage());
					return -5;
				}
			}
		}
		else {
			return 0;
		}
	}

	

}
