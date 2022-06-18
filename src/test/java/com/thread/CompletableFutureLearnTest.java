package com.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

class CompletableFutureLearnTest {

	// @Test
	void threadTest() {
		Runnable job = () -> {
			System.out.println("runAsync " + Thread.currentThread().getName());
			System.out.println("Going to Sleep");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Wake up from sleep..!");
			System.out.println("runAsync " + Thread.currentThread().getName());
		};

		Thread thread = new Thread(job);
		System.out.println("Main Thread " + Thread.currentThread().getName());
		thread.start();
		System.out.println("Main Thread End");
		System.out.println("Done");

		/*
		 * Main Thread main 
		 * Main Thread End 
		 * Done
		 * runAsync Thread-0 
		 * Going to Sleep
		 */
	}

	// @Test
	void completableFetureRunAsyncTest() {

		System.out.println("Main Thread " + Thread.currentThread().getName());

		CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
			System.out.println("runAsync " + Thread.currentThread().getName());
			System.out.println("Going to Sleep");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Wake up from sleep..!");
			System.out.println("runAsync " + Thread.currentThread().getName());
		});

		// runAsync.get();

		System.out.println("Main Thread End");

		/*
		 * Main Thread main 
		 * Main Thread End 
		 * runAsync ForkJoinPool.commonPool-worker-3
		 * Going to Sleep
		 */
	}

	// @Test
	void completableFetureSupplyAsyncTest() throws InterruptedException, ExecutionException {
		
		System.out.println("Main Thread " + Thread.currentThread().getName());

		CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
			System.out.println("runAsync " + Thread.currentThread().getName());
			System.out.println("Going to Sleep");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Wake up from sleep..!");
			return "SupplyAsync Result..!";
		});
		
		System.out.println("--DONE--");
		System.out.println("Main Sleep Start");
		// Thread.sleep(5000);
		for(int i=0; i<100000;i++) {
			
		}
		System.out.println("Main Sleep End");
		
		System.out.println("Yet to get: ");
		System.out.println("SupplyAsync Res: " + supplyAsync.get());
		
		System.out.println("DONE..!");
		
		/* Thread.sleep(5000);-> main
		 *  Main Thread main
			--DONE--
			Main Sleep Start
			runAsync ForkJoinPool.commonPool-worker-3
			Going to Sleep
			Wake up from sleep..!
			Main Sleep End
			Yet to get: 
			SupplyAsync Res: SupplyAsync Result..!
			DONE..!
		 */
		
		/* loop
		 * Main Thread main
			--DONE--
			Main Sleep Start
			runAsync ForkJoinPool.commonPool-worker-3
			Going to Sleep
			Main Sleep End
			Yet to get: 
			Wake up from sleep..!
			SupplyAsync Res: SupplyAsync Result..!
			DONE..!
		 */
	}
	
	@Test
	void completableFetureRunAsyncTest2() throws InterruptedException, ExecutionException {
		
		System.out.println("Main Thread " + Thread.currentThread().getName());

		CompletableFuture<Void> runAsync = CompletableFuture.runAsync(()->{
			System.out.println("runAsync " + Thread.currentThread().getName());
			System.out.println("Going to Sleep");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Wake up from sleep..!");
		});
			
		
		System.out.println("--DONE--");
		System.out.println("Main Sleep Start");
		Thread.sleep(5000);
		System.out.println("Main Sleep End");
		System.out.println("Yet to get: ");
		System.out.println("SupplyAsync Res: " + runAsync.get());
		System.out.println("DONE..!");
	}
	
	/*
	 *  Main Thread main
		--DONE--
		Main Sleep Start
		runAsync ForkJoinPool.commonPool-worker-3
		Going to Sleep
		Wake up from sleep..!
		Main Sleep End
		Yet to get: 
		SupplyAsync Res: null
		DONE..!
	 */

}
