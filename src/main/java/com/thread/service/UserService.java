package com.thread.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.thread.entity.User;

public interface UserService {
	
	List<User> saveUserDetails(MultipartFile file) throws Exception;
	
	List<User> findAllUsers();
	
	CompletableFuture<List<User>> saveUserDetailsAsync(MultipartFile file) throws Exception;
	
	CompletableFuture<List<User>> findAllUsersAsync();
	
	CompletableFuture<List<User>> saveUserDetailsAsyncImp(MultipartFile file) throws Exception;
	
}
