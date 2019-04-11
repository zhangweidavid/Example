/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package com.example.corejava;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午8:01:24
 * @version v 0.1
 */
public class Mutex implements Lock, java.io.Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	private static class Sync extends AbstractQueuedSynchronizer {

		/**
		 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#tryAcquire(int)
		 */
		@Override
		protected boolean tryAcquire(int acquires) {
			assert acquires == 1;
			if (compareAndSetState(0, 1)) {
				// 设置当前线程获取排它锁
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		/**
		 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#tryRelease(int)
		 */
		@Override
		protected boolean tryRelease(int releases) {
			assert releases == 1; // 限定为1个量
			if (getState() == 0)// 既然来释放，那肯定就是已占有状态了。只是为了保险，多层判断！
				throw new IllegalMonitorStateException();
			// 清空当前排它锁线程
			setExclusiveOwnerThread(null);
			setState(0);// 释放资源，放弃占有状态
			return true;
		}

		/**
		 * 判断是否处于锁定状态
		 * 
		 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#isHeldExclusively()
		 */
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

	}

	private final Sync sync = new Sync();

	/**
	 * @see java.util.concurrent.locks.Lock#lock()
	 */
	@Override
	public void lock() {
		sync.acquire(1);
	}

	/**
	 * @see java.util.concurrent.locks.Lock#lockInterruptibly()
	 */
	@Override
	public void lockInterruptibly() throws InterruptedException {
	}

	/**
	 * @see java.util.concurrent.locks.Lock#tryLock()
	 */
	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	/**
	 * @see java.util.concurrent.locks.Lock#tryLock(long,
	 *      java.util.concurrent.TimeUnit)
	 */
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	/**
	 * @see java.util.concurrent.locks.Lock#unlock()
	 */
	@Override
	public void unlock() {
		sync.release(1);
	}

	/**
	 * @see java.util.concurrent.locks.Lock#newCondition()
	 */
	@Override
	public Condition newCondition() {
		return null;
	}

}
