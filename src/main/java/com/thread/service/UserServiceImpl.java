package com.thread.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.thread.entity.User;
import com.thread.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> saveUserDetails(MultipartFile file) throws Exception {
		log.info("UserServiceImpl :: saveUserDetails");
		long startTime = System.currentTimeMillis();
		List<User> users = parseCsvFile(file);
		log.info("Saving list of users: {} and current Thread Name: {} ", users.size(), Thread.currentThread().getName());
		users = userRepository.saveAll(users);
		long endTime = System.currentTimeMillis();
		log.info("saveUserDetails :: Total Time taken: {} ", endTime - startTime);
		return users;
	}
	
	@Override
	public List<User> findAllUsers() {
		log.info("Get list of user by " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return userRepository.findAll();
	}

	@Override
	@Async
	public CompletableFuture<List<User>> saveUserDetailsAsync(MultipartFile file) throws Exception {
		log.info("UserServiceImpl :: saveUserDetailsAsync");
		long startTime = System.currentTimeMillis();
		List<User> users = parseCsvFile(file);
		log.info("Saving list of users: {} and current Thread Name: {} ", users.size(), Thread.currentThread().getName());
		users = userRepository.saveAll(users);
		long endTime = System.currentTimeMillis();
		log.info("saveUserDetailsAsync :: Total Time taken: {} ", endTime - startTime);
		return CompletableFuture.completedFuture(users);
	}

	@Async
	public CompletableFuture<List<User>> saveUserDetailsAsyncImp(MultipartFile file) throws Exception {
		log.info("UserServiceImpl :: saveUserDetailsAsync");
		long startTime = System.currentTimeMillis();
		List<User> users = parseCsvFile(file);
		log.info("Saving list of users: {} and current Thread Name: {} ", users.size(), Thread.currentThread().getName());
		users = userRepository.saveAll(users);
		long endTime = System.currentTimeMillis();
		log.info("saveUserDetailsAsync :: Total Time taken: {} ", endTime - startTime);
		return CompletableFuture.completedFuture(users);
	}
	
	@Override
	@Async
	public CompletableFuture<List<User>> findAllUsersAsync() {
		log.info("Get list of user by " + Thread.currentThread().getName());
		List<User> users = userRepository.findAll();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(users);
	}

	private List<User> parseCsvFile(MultipartFile file) throws Exception {
		final List<User> users = new ArrayList<>();

		try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] dataArray = line.split(",");
				User user = new User();
				user.setName(dataArray[0]);
				user.setEmail(dataArray[1]);
				user.setGender(dataArray[2]);
				users.add(user);
			}
			return users;

		} catch (IOException e) {
			log.error("Failed to parse CSV file {}", e.getMessage());
			throw new Exception("Failed to parse CSV file");
		}
	}
}
