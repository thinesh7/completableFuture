package com.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

public class ExecutorServiceTest {

	// Java - 5:
	@Test
	public void executorServiceTest() throws InterruptedException, ExecutionException {

		System.out.println("Thread Name: " + Thread.currentThread().getName());

		ExecutorService service = Executors.newFixedThreadPool(10);

		Future<String> result = service.submit(() -> {
			System.out.println("Thread Name: " + Thread.currentThread().getName());
			System.out.println("In Result");
			Thread.sleep(5000);
			return "Hello..!";
		});

		Future<String> result2 = service.submit(() -> {
			System.out.println("Thread Name: " + Thread.currentThread().getName());
			System.out.println("In Result2");
			Thread.sleep(5000);
			return "Bye..!";
		});

		Future<String> result3 = service.submit(() -> {
			System.out.println("Thread Name: " + Thread.currentThread().getName());
			System.out.println("In Result3");
			Thread.sleep(5000);
			return "Oiii..!";
		});

		System.out.println("Res1 : " + result.get());
		System.out.println("Res2 : " + result2.get());
		System.out.println("Res2 : " + result3.get());

		// All 3 runs at same time ..!

		System.out.println("-- Done --");
		System.out.println();

		/*
		 * Thread Name: main Thread Name: pool-1-thread-1 Thread Name: pool-1-thread-2
		 * Res1 : Hello..! Res2 : Bye..! -- Done --
		 */

		// Multiple Feature cannot be chained together
	}

	@Test
	public void executorServiceTest2() throws InterruptedException {

		System.out.println("Thread Name: " + Thread.currentThread().getName());

		ExecutorService service = Executors.newFixedThreadPool(10);

		service.execute(() -> {
			System.out.println("Thread Name: " + Thread.currentThread().getName());
			System.out.println("In Result");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Out Result");
		});

		service.execute(() -> {
			System.out.println("Thread Name: " + Thread.currentThread().getName());
			System.out.println("In Result 1");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Out Result 1");
		});
		
		System.out.println("DONE");
		Thread.sleep(5000); // Making main thread to wait
	}

}
