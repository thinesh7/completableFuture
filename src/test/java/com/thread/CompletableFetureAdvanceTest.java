package com.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class CompletableFetureAdvanceTest {

	// Get ALL Employees: thenApply, thenAccept, thenArun
	private List<Employee> getAllUsers() throws InterruptedException {
		List<Employee> employees = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			int id = i;
			id++;
			if (i % 2 == 0) {
				employees.add(new Employee(id, "Name " + id, true, true));
			} else {
				employees.add(new Employee(id, "Name " + id, true, false));
				Thread.sleep(5000);
			}
			
		}
		return employees;
	}

	private Void sendEmail() throws InterruptedException, ExecutionException {

		return CompletableFuture.supplyAsync(() -> {
			try {
				return getAllUsers();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return new ArrayList<Employee>();
			}
		}).thenApplyAsync(employees -> {
			System.out.println("Thread to filter New Joinee " + Thread.currentThread().getName());
			return employees.stream().filter(emp -> {
				System.out.println("Checking isNewJoinee "+ emp.getId() + " Thread Name: "+ Thread.currentThread().getName());
				return emp.isNewJoinee();
			});
		}).thenApplyAsync(employees -> {
			System.out.println("Thread to filter course not completed " + Thread.currentThread().getName());
			return employees.filter(emp -> {
				System.out.println("Checking is Not Completed "+ emp.getId() + " Thread Name: "+ Thread.currentThread().getName());
				return !emp.isCompleted();
			});
		}).thenAcceptAsync(employees -> {
			System.out.println("Thread to Send SMS " + Thread.currentThread().getName());
			employees.forEach(emp -> {
				System.out.println("Sending SMS "+ emp.getId() + " Thread Name: "+ Thread.currentThread().getName());
				System.out.println(emp);
			});
		}).get();
	}

	@Test
	void completableFetureAdvanceTest() throws InterruptedException, ExecutionException {

		long startTime = System.currentTimeMillis();
		sendEmail();
		long endTime = System.currentTimeMillis();

		System.out.println("Time taken " + (endTime - startTime));
	}
	
	/*
	 *  Thread to filter New Joinee ForkJoinPool.commonPool-worker-3
		Thread to filter course not completed ForkJoinPool.commonPool-worker-3
		Thread to Send SMS ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 1 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 1 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=2, name=Name 2, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 3 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 3 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=4, name=Name 4, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 5 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 5 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=6, name=Name 6, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 7 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 7 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=8, name=Name 8, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 9 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 9 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=10, name=Name 10, newJoinee=true, isCompleted=false)
		
		Time taken 10117
	 */
	
	
	
	/* ASYNC:
	 *  Thread to filter New Joinee ForkJoinPool.commonPool-worker-3
		Thread to filter course not completed ForkJoinPool.commonPool-worker-3
		Thread to Send SMS ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 1 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 1 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=2, name=Name 2, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 3 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 3 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=4, name=Name 4, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 5 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 5 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=6, name=Name 6, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 7 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 7 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=8, name=Name 8, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 9 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 9 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=10, name=Name 10, newJoinee=true, isCompleted=false)
		
		Time taken 10087
	 */
	
	/*
	 *  Thread to filter New Joinee ForkJoinPool.commonPool-worker-3
		Thread to filter course not completed ForkJoinPool.commonPool-worker-3
		Thread to Send SMS ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 1 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 1 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 2 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=2, name=Name 2, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 3 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 3 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 4 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=4, name=Name 4, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 5 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 5 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 6 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=6, name=Name 6, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 7 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 7 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 8 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=8, name=Name 8, newJoinee=true, isCompleted=false)
		
		Checking isNewJoinee 9 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 9 Thread Name: ForkJoinPool.commonPool-worker-3
		
		Checking isNewJoinee 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Checking is Not Completed 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Sending SMS 10 Thread Name: ForkJoinPool.commonPool-worker-3
		Employee(id=10, name=Name 10, newJoinee=true, isCompleted=false)
		Time taken 25039
	 */

}
