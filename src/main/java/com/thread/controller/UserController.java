package com.thread.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.thread.entity.User;
import com.thread.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/api")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	// Normal Approach:

	@PostMapping(value = "saveUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public ResponseEntity<Map<String, Object>> saveUserDetails(@RequestParam("files") MultipartFile[] files) {

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> responseJson = new LinkedHashMap<>();

		log.info("UserController :: saveUserDetails");
		try {
			long startTime = System.currentTimeMillis();
			for (MultipartFile file : files) {
				System.out.println("Called..! " + Thread.currentThread().getName());
				userService.saveUserDetails(file);
			}
			long endTime = System.currentTimeMillis();
			log.info("END :: saveUserDetails :: Total Time taken: {} ", endTime - startTime);
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

	// Async Approach:

	@PostMapping(value = "saveUserAsync", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public ResponseEntity<Map<String, Object>> saveUserDetailsAsync(@RequestParam("files") MultipartFile[] files) {

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> responseJson = new LinkedHashMap<>();

		log.info("UserController :: saveUserDetailsAsync");
		try {
			long startTime = System.currentTimeMillis();
			for (MultipartFile file : files) {
				System.out.println("Called..! " + Thread.currentThread().getName());
				userService.saveUserDetailsAsync(file);
			}
			long endTime = System.currentTimeMillis();
			log.info("END :: saveUserDetailsAsync :: Total Time taken: {} ", endTime - startTime);
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

	// Async Approach with Join -> Imp: -> Will wait until all get completes ..!

	@PostMapping(value = "saveUserAsyncImp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> saveUserDetailsAsyncImp(@RequestParam("files") MultipartFile[] files) {

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> responseJson = new LinkedHashMap<>();

		log.info("UserController :: saveUserDetailsAsyncImp");
		try {
			long startTime = System.currentTimeMillis();

			List<CompletableFuture<?>> completableFutureList = new ArrayList<>();

			for (MultipartFile file : files) {
				System.out.println("Called..! " + Thread.currentThread().getName());
				CompletableFuture<List<User>> result = userService.saveUserDetailsAsyncImp(file);
				completableFutureList.add(result);
			}

			CompletableFuture<?>[] resArray = completableFutureList.toArray(new CompletableFuture[] {});
			CompletableFuture.allOf(resArray).join();
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

	// Normal Approach:
	@GetMapping(value = "/users", produces = "application/json")
	public ResponseEntity<List<User>> getAllUsers() {

		log.info("UserController :: getAllUsers");

		System.out.println("1 - Started");
		List<User> users1 = userService.findAllUsers();
		System.out.println("2 - Started");
		List<User> users2 = userService.findAllUsers();
		System.out.println("3 - Started");
		List<User> users3 = userService.findAllUsers();

		log.info("UserController :: getAllUsers = Users1, Users2, Users3..!");

		users1.addAll(users2);
		users1.addAll(users3);

		System.out.println("Done..!");
		return ResponseEntity.status(HttpStatus.OK).body(users1);
	}

	// Async Approach:
	@GetMapping(value = "/getUsersByThreadAsync", produces = "application/json")
	public ResponseEntity<List<User>> getAllUsersAsync() throws InterruptedException, ExecutionException {

		log.info("UserController :: getAllUsersAsync");

		System.out.println("1 - Started");
		CompletableFuture<List<User>> users1 = userService.findAllUsersAsync();
		System.out.println("2 - Started");
		CompletableFuture<List<User>> users2 = userService.findAllUsersAsync();
		System.out.println("3 - Started");
		CompletableFuture<List<User>> users3 = userService.findAllUsersAsync();

		log.info("UserController :: getAllUsersAsync = Users1, Users2, Users3..!");

		System.out.println("Done..!");

		users1.get().addAll(users2.get());
		users1.get().addAll(users3.get());

		return ResponseEntity.status(HttpStatus.OK).body(users1.get());
	}

	// Async Approach with Join:
	@GetMapping(value = "/getUsersByThreadAsyncImp", produces = "application/json")
	public ResponseEntity<List<User>> getAllUsersAsyncImp() throws InterruptedException, ExecutionException {

		log.info("UserController :: getAllUsersAsyncImp");

		System.out.println("1 - Started");
		CompletableFuture<List<User>> users1 = userService.findAllUsersAsync();
		System.out.println("2 - Started");
		CompletableFuture<List<User>> users2 = userService.findAllUsersAsync();
		System.out.println("3 - Started");
		CompletableFuture<List<User>> users3 = userService.findAllUsersAsync();

		log.info("UserController :: getAllUsersAsync = Users1, Users2, Users3..!");

		// Join
		CompletableFuture.allOf(users1, users2, users3).join();

		System.out.println("Done..!");

		users1.get().addAll(users2.get());
		users1.get().addAll(users3.get());

		return ResponseEntity.status(HttpStatus.OK).body(users1.get());
	}

	// Async Approach:
	@GetMapping(value = "/usersAsync", produces = "application/json")
	public CompletableFuture<ResponseEntity<?>> findAllUsersAsync() {
		return userService.findAllUsersAsync().thenApply(ResponseEntity::ok);
	}
}
