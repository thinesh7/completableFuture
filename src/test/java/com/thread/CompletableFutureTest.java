package com.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

public class CompletableFutureTest {

	@Test
	public void completableFutureTest1() throws InterruptedException, ExecutionException {

		// runAsync Method: 1 and supplyAsync Method: 2

		System.out.println("Thread Name : " + Thread.currentThread().getName()); // main

		CompletableFuture<Void> runAsync1 = CompletableFuture.runAsync(() -> {
			System.out.println("runAsync1 Thread Name : " + Thread.currentThread().getName());
			System.out.println("1 Inserting Record to DB");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("1 Insertion Completed..!");
		});
		
		runAsync1.get();

		CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(() -> {
			System.out.println("runAsync2 Thread Name : " + Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-5
			System.out.println("2 Inserting Record to DB");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("2 Insertion Completed..!");
		});
		
		runAsync2.get();

		CompletableFuture<Void> runAsync3 = CompletableFuture.runAsync(() -> {
			System.out.println("runAsync3 Thread Name : " + Thread.currentThread().getName());
			System.out.println("3 Inserting Record to DB");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("3 Insertion Completed..!");
		});
		
		runAsync3.get();
		
		System.out.println("All Done ..! "+Thread.currentThread().getName());

	}
	
	
	/*
	 *  Thread Name : main
		runAsync1 Thread Name : ForkJoinPool.commonPool-worker-3
		1 Inserting Record to DB
		1 Insertion Completed..!
		runAsync2 Thread Name : ForkJoinPool.commonPool-worker-3
		2 Inserting Record to DB
		2 Insertion Completed..!
		runAsync3 Thread Name : ForkJoinPool.commonPool-worker-3
		3 Inserting Record to DB
		3 Insertion Completed..!
		All Done ..! main
	 * 
	 */

}
