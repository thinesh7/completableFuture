package com.thread;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class CompletableFutureTest2 {

	@Test
	void completableFutureTest1() throws InterruptedException, ExecutionException {
		
		System.out.println("Test 1: ");
		
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			System.out.println("F1 - Running "+Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Hello";
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("F2 - Running "+Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Beautiful";
		});
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			System.out.println("F3 - Running "+Thread.currentThread().getName());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "World";
		});

		System.out.println("Combine");
		CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

		System.out.println("Get..!");
		combinedFuture.get();

		assertTrue(future1.isDone());
		assertTrue(future2.isDone());
		assertTrue(future3.isDone());
		
		System.out.println("DONE");
		System.out.println();
	}
	
	/*
	 *  Test 1: 
		F1 - Running ForkJoinPool.commonPool-worker-3
		F2 - Running ForkJoinPool.commonPool-worker-5
		Combine
		F3 - Running ForkJoinPool.commonPool-worker-7
		Get..!
		DONE
	 */

	@Test
	void completableFutureTest2() throws InterruptedException, ExecutionException {

		System.out.println("Test 2: ");
		
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			System.out.println("F1 - Running "+Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Hello";
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("F2 - Running "+Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Beautiful";
		});
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			System.out.println("F3 - Running "+Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "World";
		});

		System.out.println("Start");
		future1.get();
		future2.get();
		future3.get();
		System.out.println("End");
		
		System.out.println("Checking..!");
		System.out.println(future3.isDone());

		System.out.println("Done");
	}
	
	/*
	 *  Test 2:
		F1 - Running ForkJoinPool.commonPool-worker-7
		F2 - Running ForkJoinPool.commonPool-worker-5
		Start
		F3 - Running ForkJoinPool.commonPool-worker-3
		End
		Checking..!
		true
		Done
	 */
}
