package com.thread.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
public class ExecutorServiceController {

	@Autowired
	private UserService userService;

	// It will work without Join
	
	@PostMapping(value = "executorServiceSaveUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> executorServiceSaveUser(@RequestParam("files") MultipartFile[] files) {

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> responseJson = new LinkedHashMap<>();

		log.info("UserController :: executorServiceSaveUser");
		try {
			long startTime = System.currentTimeMillis();
			System.out.println("In..!");
			ExecutorService service = Executors.newFixedThreadPool(10);
			for (MultipartFile file : files) {
				System.out.println("Called..! " + Thread.currentThread().getName());
				service.execute(() -> {
					try {
						userService.saveUserDetails(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
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
	 * 	2022-06-14 18:26:44.715  INFO 24840 --- [nio-9000-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
	   	2022-06-14 18:26:44.715  INFO 24840 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
		2022-06-14 18:26:44.734  INFO 24840 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 18 ms
		2022-06-14 18:26:45.007  INFO 24840 --- [nio-9000-exec-1] c.t.controller.ThreadTestController      : UserController :: executorServiceSaveUser
		In..!
		Called..! http-nio-9000-exec-1
		Called..! http-nio-9000-exec-1
		Called..! http-nio-9000-exec-1
		Called..! http-nio-9000-exec-1
		Out..!
		2022-06-14 18:26:45.015  INFO 24840 --- [pool-1-thread-3] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 18:26:45.015  INFO 24840 --- [nio-9000-exec-1] c.t.controller.ThreadTestController      : END :: saveUserDetailsAsync :: Total Time taken: 6 
		Done..!
		2022-06-14 18:26:45.015  INFO 24840 --- [pool-1-thread-2] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 18:26:45.015  INFO 24840 --- [pool-1-thread-4] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 18:26:45.016  INFO 24840 --- [pool-1-thread-1] com.thread.service.UserServiceImpl       : UserServiceImpl :: saveUserDetails
		2022-06-14 18:26:45.039  INFO 24840 --- [pool-1-thread-2] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: pool-1-thread-2 
		2022-06-14 18:26:45.046  INFO 24840 --- [pool-1-thread-3] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: pool-1-thread-3 
		2022-06-14 18:26:45.049  INFO 24840 --- [pool-1-thread-1] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: pool-1-thread-1 
		2022-06-14 18:26:45.101  INFO 24840 --- [pool-1-thread-4] com.thread.service.UserServiceImpl       : Saving list of users: 1001 and current Thread Name: pool-1-thread-4 
		2022-06-14 18:26:46.175  INFO 24840 --- [pool-1-thread-2] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 1160 
		2022-06-14 18:26:46.218  INFO 24840 --- [pool-1-thread-3] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 1203 
		2022-06-14 18:26:46.213  INFO 24840 --- [pool-1-thread-4] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 1198 
	 *	2022-06-14 18:26:46.219  INFO 24840 --- [pool-1-thread-1] com.thread.service.UserServiceImpl       : saveUserDetails :: Total Time taken: 1203 
	 */

}
