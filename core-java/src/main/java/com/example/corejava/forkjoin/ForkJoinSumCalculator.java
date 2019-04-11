/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package com.example.corejava.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月10日 下午9:11:41
 * @version v 0.1
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

	/**  */
	private static final long serialVersionUID = -3260566537129408291L;
	private final long[] numbers;
	private final int start;
	private final int end;
	private static final long THRESHOLD = 10 * 1000;

	/**
	 * @param numbers
	 */
	public ForkJoinSumCalculator(long[] numbers) {
		super();
		this.numbers = numbers;
		this.start = 0;
		this.end = numbers.length;
	}

	/**
	 * @param numbers
	 * @param start
	 * @param end
	 */
	public ForkJoinSumCalculator(long[] numbers, int start, int end) {
		super();
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	/**
	 * @see java.util.concurrent.RecursiveTask#compute()
	 */
	@Override
	protected Long compute() {
		int length = end - start;
		if (length <= THRESHOLD) {
			return computeSequentially();
		}
		ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
		leftTask.fork();
		ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, length);
		Long rightResult = rightTask.compute();
		Long leftResult = leftTask.join();
		return rightResult + leftResult;
	}

	/**
	 * 
	 * @return
	 * @author wei.zw
	 */
	private Long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			//logger.error("", e);
		}
		return sum;
	}

	
	/**
	 * 
	 * @param args
	 * @author wei.zw
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		long r = new ForkJoinPool()
				.invoke(new ForkJoinSumCalculator(LongStream.rangeClosed(1, 10 * 1000 * 1000*10).toArray()));
		TimeUnit.MINUTES.sleep(10);
	}
}
