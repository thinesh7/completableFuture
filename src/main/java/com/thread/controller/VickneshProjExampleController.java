package com.thread.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thread.entity.User;
import com.thread.service.VickneshProjExampleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/api")
@Slf4j
public class VickneshProjExampleController {

	@Autowired
	private VickneshProjExampleService service;

	@PostMapping(value = "/example1/{maxCount}")
	public ResponseEntity<Map<String, Object>> example1(@PathVariable("maxCount") int maxCount) {

		Map<String, Object> resultMap = new LinkedHashMap<>();

		log.info("example1 :: started");
		try {
			List<User> users = new ArrayList<>();
			for (int i = 1; i <= maxCount; i++) {
				users.add(new User("Name - " + i, "Email - " + i, "Gender " + i));
			}

			log.info("runAsync :: started");
			CompletableFuture.runAsync(() -> {
				log.info("SaveData Service1 - {} :: Started {} ", Thread.currentThread().getName(), LocalDateTime.now().toString());
				service.saveDataExample1(users);
				log.info("SaveData Service1 - {} :: End {} ", Thread.currentThread().getName(), LocalDateTime.now().toString());
			});
			log.info("----");
			resultMap.put("status", "success");
			resultMap.put("processedRecord", users.size());
			log.info("-- DONE --");

		} catch (Exception exp) {
			log.error("Exception occured on example1 {} ", exp);
			resultMap.put("status", "failure");
			resultMap.put("description", exp.getMessage());
			return ResponseEntity.internalServerError().body(resultMap);
		}
		return ResponseEntity.ok(resultMap);
	}

	/*
	 *  2022-06-18 16:35:52.765  INFO 28908 --- [nio-9000-exec-9] c.t.c.VickneshProjExampleController      : example1 :: started
		2022-06-18 16:35:52.766  INFO 28908 --- [nio-9000-exec-9] c.t.c.VickneshProjExampleController      : runAsync :: started
		2022-06-18 16:35:52.766  INFO 28908 --- [nio-9000-exec-9] c.t.c.VickneshProjExampleController      : ----
		2022-06-18 16:35:52.766  INFO 28908 --- [nio-9000-exec-9] c.t.c.VickneshProjExampleController      : -- DONE --
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.c.VickneshProjExampleController      : SaveData Service1 - ForkJoinPool.commonPool-worker-7 :: Started 2022-06-18T16:35:52.766306500 
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Service - SaveData1 :: started
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : userList size 10 
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Save1 - started
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 1 userList :: userBatch 100
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 2 userList :: userBatch 100
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-3 - Started - 100 
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 3 userList :: userBatch 100
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-5] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-5 - Started - 100 
		2022-06-18 16:35:52.766  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 4 userList :: userBatch 100
		2022-06-18 16:35:52.777  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 5 userList :: userBatch 100
		2022-06-18 16:35:52.777  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 6 userList :: userBatch 100
		2022-06-18 16:35:52.777  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 7 userList :: userBatch 100
		2022-06-18 16:35:52.779  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 8 userList :: userBatch 100
		2022-06-18 16:35:52.779  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 9 userList :: userBatch 100
		2022-06-18 16:35:52.779  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Batch 10 userList :: userBatch 99
		2022-06-18 16:35:52.779  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : Service - SaveData1 :: Ended 
		2022-06-18 16:35:52.779  INFO 28908 --- [onPool-worker-7] c.t.c.VickneshProjExampleController      : SaveData Service1 - ForkJoinPool.commonPool-worker-7 :: End 2022-06-18T16:35:52.779315400 
		2022-06-18 16:35:52.779  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-7 - Started - 99 
		2022-06-18 16:35:52.780  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-3 - Ended at: 2022-06-18T16:35:52.780315800
		2022-06-18 16:35:52.781  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-3 - Started - 100 
		2022-06-18 16:35:52.802  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-3 - Ended at: 2022-06-18T16:35:52.802314300
		2022-06-18 16:35:52.802  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-3 - Started - 100 
		2022-06-18 16:35:52.809  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-7 - Ended at: 2022-06-18T16:35:52.809329300
		2022-06-18 16:35:52.810  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-7 - Started - 100 
		2022-06-18 16:35:52.817  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-3 - Ended at: 2022-06-18T16:35:52.817313300
		2022-06-18 16:35:52.817  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-3 - Started - 100 
		2022-06-18 16:35:52.783  INFO 28908 --- [onPool-worker-5] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-5 - Ended at: 2022-06-18T16:35:52.782311800
		2022-06-18 16:35:52.817  INFO 28908 --- [onPool-worker-5] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-5 - Started - 100 
		2022-06-18 16:35:52.843  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-7 - Ended at: 2022-06-18T16:35:52.843309100
		2022-06-18 16:35:52.843  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-7 - Started - 100 
		2022-06-18 16:35:52.851  INFO 28908 --- [onPool-worker-5] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-5 - Ended at: 2022-06-18T16:35:52.851312500
		2022-06-18 16:35:52.851  INFO 28908 --- [onPool-worker-5] c.t.service.VickneshProjExampleService   : SaveAll :: Thread ForkJoinPool.commonPool-worker-5 - Started - 100 
		2022-06-18 16:35:52.860  INFO 28908 --- [onPool-worker-7] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-7 - Ended at: 2022-06-18T16:35:52.860313700
		2022-06-18 16:35:52.863  INFO 28908 --- [onPool-worker-5] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-5 - Ended at: 2022-06-18T16:35:52.863314700
		2022-06-18 16:35:52.871  INFO 28908 --- [onPool-worker-3] c.t.service.VickneshProjExampleService   : SaveAll Done :: Thread :: ForkJoinPool.commonPool-worker-3 - Ended at: 2022-06-18T16:35:52.871312600

	 */

	@PostMapping(value = "/example2/{maxCount}")
	public ResponseEntity<Map<String, Object>> example2(@PathVariable("maxCount") int maxCount) {
		Map<String, Object> resultMap = new LinkedHashMap<>();
		log.info("example2 :: started");
		
		try {
			List<User> users = new ArrayList<>();
			for (int i = 1; i <= maxCount; i++) {
				users.add(new User("Name - " + i, "Email - " + i, "Gender " + i));
			}
			log.info("runAsync 2 :: started");
			
			/* CompletableFuture.runAsync(() -> {
				log.info("SaveData Service2 - {} :: Started {} ", Thread.currentThread().getName(), LocalDateTime.now().toString());
				service.saveDataExample2(users);
				log.info("SaveData Service2 - {} :: End {} ", Thread.currentThread().getName(), LocalDateTime.now().toString());
			}); */
			
			CompletableFuture<String> supplyAsync = CompletableFuture.runAsync(() -> {
				log.info("SaveData Service2 - {} :: Started {} ", Thread.currentThread().getName(),
						LocalDateTime.now().toString());
				service.saveDataExample2(users);
				log.info("SaveData Service2 - {} :: End {} ", Thread.currentThread().getName(),
						LocalDateTime.now().toString());
			}).thenApply(x -> {
				System.out.println("-> >> " + x);
				System.out.println("Running Procedure..!");
				System.out.println("Procedure DONE..!");
				return "success";
			});

			resultMap.put("status", supplyAsync.get());
			resultMap.put("processedRecord", users.size());
			log.info("-- DONE --");

		} catch (Exception exp) {
			log.error("Exception occured on example1 {} ", exp);
			resultMap.put("status", "failure");
			resultMap.put("description", exp.getMessage());
			return ResponseEntity.internalServerError().body(resultMap);
		}
		return ResponseEntity.ok(resultMap);
	}

}
