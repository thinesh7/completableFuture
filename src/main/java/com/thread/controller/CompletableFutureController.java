package com.thread.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.thread.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/api")
@Slf4j
public class CompletableFutureController {

	@Autowired
	private UserService userService;

	// It will work with Join -> Imp
	@PostMapping(value = "completableFutureSaveUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> completableFutureSaveUser(@RequestParam("files") MultipartFile[] files) {

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> responseJson = new LinkedHashMap<>();

		log.info("UserController :: completableFutureSaveUser");
		try {
			long startTime = System.currentTimeMillis();
			
			System.out.println("In..!");

			List<CompletableFuture<?>> comFtrList = new ArrayList<>();
			CompletableFuture<Void> runAsync;

			for (MultipartFile file : files) {
				System.out.println("Called..! " + Thread.currentThread().getName());
				runAsync = CompletableFuture.runAsync(() -> {
					try {
						userService.saveUserDetails(file);
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				comFtrList.add(runAsync);
			}
			
			System.out.println("Out..!");

			CompletableFuture<?>[] comFtrArray = comFtrList.toArray(new CompletableFuture[] {});
			CompletableFuture.allOf(comFtrArray).join();
			
			long endTime = System.currentTimeMillis();
			log.info("END :: saveUserDetailsAsync :: Total Time taken: {} ", endTime - startTime);
			System.out.println("Done..!");

			responseJson.put("status", "success");
			response = ResponseEntity.ok(responseJson);
		} catch (Exception e) {
			log.error("Failed to save" + e.getMessage());
			responseJson.put("status", "Failed");
			responseJson.put("failed descritpion", e.getMessage());
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseJson);
		}
		return response;
	}
	
	/*
	 *  2022-06-14 19:11:54.011  INFO 23984 --- [nio-9000-exec-3] c.t.c.CompletableFutureController        : UserController :: completableFutureSaveUser
		In..!
		Called..! http-nio-9000-exec-3
		Called..! http-nio-9000-exec-3
		Called..! http-nio-9000-exec-3
		Called..! http-nio-9000-exec-3
		Out..!
		2022-06-14 19:11:54.011  INFO 23984 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:11:54.011  INFO 23984 --- [onPool-worker-5] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:11:54.011  INFO 23984 --- [onPool-worker-7] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:11:54.022  INFO 23984 --- [onPool-worker-7] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-7 
		2022-06-14 19:11:54.023  INFO 23984 --- [onPool-worker-5] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-5 
		2022-06-14 19:11:54.028  INFO 23984 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-3 
		2022-06-14 19:11:54.159  INFO 23984 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 148 
		2022-06-14 19:11:54.159  INFO 23984 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:11:54.182  INFO 23984 --- [onPool-worker-5] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 171 
		2022-06-14 19:11:54.184  INFO 23984 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-3 
		2022-06-14 19:11:54.240  INFO 23984 --- [onPool-worker-7] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 229 
		2022-06-14 19:11:54.272  INFO 23984 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 113 
		2022-06-14 19:11:54.272  INFO 23984 --- [nio-9000-exec-3] c.t.c.CompletableFutureController        : END :: saveUserDetailsAsync :: Total Time taken: 261 
	 *  Done..!
	 */

	
	
	// Sample for -> WithoutJoin -> waste
	
	@PostMapping(value = "completableFutureSaveUserWithoutJoin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> completableFutureSaveUserWithoutJoin(@RequestParam("files") MultipartFile[] files) {

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> responseJson = new LinkedHashMap<>();

		log.info("UserController :: completableFutureSaveUserWithoutJoin");
		try {
			long startTime = System.currentTimeMillis();
			
			System.out.println("In..!");

			for (MultipartFile file : files) {
				System.out.println("Called..! " + Thread.currentThread().getName());
				CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
					try {
						userService.saveUserDetails(file);
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				runAsync.get(); // But Don't do this
			}
			
			System.out.println("Out..!");
			
			long endTime = System.currentTimeMillis();
			log.info("END :: saveUserDetailsAsync :: Total Time taken: {} ", endTime - startTime);
			System.out.println("Done..!");

			responseJson.put("status", "success");
			response = ResponseEntity.ok(responseJson);
		} catch (Exception e) {
			log.error("Failed to save" + e.getMessage());
			responseJson.put("status", "Failed");
			responseJson.put("failed descritpion", e.getMessage());
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseJson);
		}
		return response;
	}
	
	/*
	 *  In..!
		Called..! http-nio-9000-exec-2
		2022-06-14 19:29:17.808  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:29:17.825  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-3 
		2022-06-14 19:29:18.607  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 799 
		Called..! http-nio-9000-exec-2
		2022-06-14 19:29:20.617  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:29:20.627  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-3 
		2022-06-14 19:29:20.959  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 342 
		Called..! http-nio-9000-exec-2
		2022-06-14 19:29:22.963  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:29:22.970  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-3 
		2022-06-14 19:29:23.042  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 79 
		Called..! http-nio-9000-exec-2
		2022-06-14 19:29:25.045  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 19:29:25.053  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: ForkJoinPool.commonPool-worker-3 
		2022-06-14 19:29:25.111  INFO 13340 --- [onPool-worker-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 65 
		Out..!
		2022-06-14 19:29:27.123  INFO 13340 --- [nio-9000-exec-2] c.t.c.CompletableFutureController        : END :: saveUserDetailsAsync :: Total Time taken: 9323 
		Done..!
	 */
}
