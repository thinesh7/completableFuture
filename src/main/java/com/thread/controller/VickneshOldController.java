package com.thread.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thread.entity.User;
import com.thread.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/api")
@Slf4j
public class VickneshOldController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/example/{maxCount}")
	public ResponseEntity<Map<String, Object>> example(@PathVariable("maxCount") int maxCount) {

		Map<String, Object> resultMap = new LinkedHashMap<>();

		log.info("example :: started");

		try {
			List<User> users = new ArrayList<>();
			for (int i = 1; i <= maxCount; i++) {
				users.add(new User("Name - " + i, "Email - " + i, "Gender " + i));
			}
			
			System.out.println("Save Data Started");

			userRepository.saveAll(users);
			
			System.out.println("Save Data DONE");
			
			System.out.println("PLSQL ..!");
			
			resultMap.put("status", "success");
			resultMap.put("processedRecord", users.size());
			
			log.info("example :: End");
		} catch (Exception exp) {
			log.error("Exception occured on example1 {} ", exp);
			resultMap.put("status", "failure");
			resultMap.put("description", exp.getMessage());
			return ResponseEntity.internalServerError().body(resultMap);
		}
		return ResponseEntity.ok(resultMap);
	}
}
