package com.thread.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thread.entity.User;
import com.thread.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VickneshProjExampleService {

	@Autowired
	private UserRepository userRepository;

	public void saveDataExample1(List<User> users) {
		log.info("Service - SaveData1 :: started");

		List<List<User>> userList = new ArrayList<>();
		List<User> tempUser = new ArrayList<>();
		int userAddCount = 0;

		for (User user : users) {
			if (userAddCount == 100) {
				userList.add(tempUser);
				tempUser = new ArrayList<>();
				userAddCount = 0;
			}
			tempUser.add(user);
			userAddCount++;
		}

		if (!tempUser.isEmpty()) {
			userList.add(tempUser);
		}

		log.info("userList size {} ", userList.size());
		log.info("Save1 - started");

		int count = 1;
		for (List<User> userBatch : userList) {
			log.info("Batch {} userList :: userBatch {}", count, userBatch.size());
			CompletableFuture.runAsync(() -> {
				log.info("SaveAll :: Thread {} - Started - {} ", Thread.currentThread().getName(), userBatch.size());
				userRepository.saveAll(userBatch);
				log.info("SaveAll Done :: Thread :: {} - Ended at: {}", Thread.currentThread().getName(),
						LocalDateTime.now().toString());
			});
			count++;
		}
		log.info("Service - SaveData1 :: Ended ");
	}

	public void saveDataExample2(List<User> users) {
		log.info("Service - SaveData2 :: started");

		List<List<User>> userList = new ArrayList<>();
		List<User> tempUser = new ArrayList<>();
		int userAddCount = 0;

		for (User user : users) {
			if (userAddCount == 100) {
				userList.add(tempUser);
				tempUser = new ArrayList<>();
				userAddCount = 0;
			}
			tempUser.add(user);
			userAddCount++;
		}

		if (!tempUser.isEmpty()) {
			userList.add(tempUser);
		}

		log.info("userList size {} ", userList.size());
		log.info("Save1 - started");

		List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
		int count = 1;
		for (List<User> userBatch : userList) {
			log.info("Batch {} userList :: userBatch {}", count, userBatch.size());
			CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
				log.info("SaveAll :: Thread {} - Started {} ", Thread.currentThread().getName(), userBatch.size());
				userRepository.saveAll(userBatch);
				log.info("SaveAll Done :: Thread :: {} - Ended at: {}", Thread.currentThread().getName(),
						LocalDateTime.now().toString());
			});
			completableFutures.add(runAsync);
			count++;
		}
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[] {})).join();
		System.out.println("-------------- Service - SaveData 2 :: Ended -----------");
	}

}
