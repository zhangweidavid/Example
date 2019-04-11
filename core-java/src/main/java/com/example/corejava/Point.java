/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月8日
 */

package com.example.corejava;

import java.util.concurrent.locks.StampedLock;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月8日 下午12:49:23
 * @version v 0.1
 */
public class Point {
	private double x, y;
	private final StampedLock stampedLock = new StampedLock();

	// an exclusively locked method
	void move(double deltaX, double deltaY) {
		/**
		 * stampedLock调用writeLock和unlockWrite时候都会导致stampedLock的stamp值的变化
		 * 即每次+1，直到加到最大值，然后从0重新开始
		 */
		long stamp = stampedLock.writeLock(); // 写锁
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			stampedLock.unlockWrite(stamp);// 释放写锁
		}
	}

	double distanceFromOrigin() { // A read-only method
		/**
		 * tryOptimisticRead是一个乐观的读，使用这种锁的读不阻塞写 每次读的时候得到一个当前的stamp值（类似时间戳的作用）
		 */
		long stamp = stampedLock.tryOptimisticRead();

		// 这里就是读操作，读取x和y，因为读取x时，y可能被写了新的值，所以下面需要判断
		double currentX = x, currentY = y;

		/**
		 * 如果读取的时候发生了写，则stampedLock的stamp属性值会变化，此时需要重读，
		 * 再重读的时候需要加读锁（并且重读时使用的应当是悲观的读锁，即阻塞写的读锁）
		 * 当然重读的时候还可以使用tryOptimisticRead，此时需要结合循环了，即类似CAS方式 读锁又重新返回一个stampe值
		 */
		if (!stampedLock.validate(stamp)) {
			stamp = stampedLock.readLock(); // 读锁
			try {
				currentX = x;
				currentY = y;
			} finally {
				stampedLock.unlockRead(stamp);// 释放读锁
			}
		}
		// 读锁验证成功后才执行计算，即读的时候没有发生写
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}

}
