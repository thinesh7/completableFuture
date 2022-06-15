package com.thread;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class Tester {

	@Test
	void test1() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Hello";
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Beautiful";
		});
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "World";
		});

		CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

		combinedFuture.get();

		assertTrue(future1.isDone());
		assertTrue(future2.isDone());
		assertTrue(future3.isDone());
	}

	@Test
	public void test2() throws InterruptedException, ExecutionException {

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
		/*
		 * assertFalse(future1.isDone());
		assertFalse(future2.isDone());
		assertFalse(future3.isDone()); */
	}

}
