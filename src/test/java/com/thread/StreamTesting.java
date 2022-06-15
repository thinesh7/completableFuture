package com.thread;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StreamTesting {

	private List<Employee> getAllUsers() throws InterruptedException {
		List<Employee> employees = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			int id = i;
			id++;
			if (i % 2 == 0) {
				employees.add(new Employee(id, "Name " + id, true, true));
			} else {
				employees.add(new Employee(id, "Name " + id, true, false));
			}

		}
		return employees;
	}

	@Test
	void streamTest() throws InterruptedException {
		getAllUsers().stream()
				.filter(x -> {
					System.out.println("Filter isNewJoinee");
					return x.isNewJoinee();
				})
				.filter(x -> {
					System.out.println("Filter isCompleted");
					return x.isCompleted();
				})
				.forEach(x -> {
					System.out.println("Print");
					System.out.println(x);
				});
	}
	
	/*
	 *  Filter isNewJoinee
		Filter isCompleted
		Print
		Employee(id=1, name=Name 1, newJoinee=true, isCompleted=true)
		
		Filter isNewJoinee
		Filter isCompleted
		
		Filter isNewJoinee
		Filter isCompleted
		Print
		Employee(id=3, name=Name 3, newJoinee=true, isCompleted=true)
		
		Filter isNewJoinee
		Filter isCompleted
		
		Filter isNewJoinee
		Filter isCompleted
		Print
		Employee(id=5, name=Name 5, newJoinee=true, isCompleted=true)
		
		Filter isNewJoinee
		Filter isCompleted
		
		Filter isNewJoinee
		Filter isCompleted
		Print
		Employee(id=7, name=Name 7, newJoinee=true, isCompleted=true)
		
		Filter isNewJoinee
		Filter isCompleted
		
		Filter isNewJoinee
		Filter isCompleted
		Print
		Employee(id=9, name=Name 9, newJoinee=true, isCompleted=true)
		
		Filter isNewJoinee
		Filter isCompleted
	 */

}
