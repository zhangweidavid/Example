/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月6日
 */

package rxjava.demo;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.maybe.MaybeOnErrorComplete;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月6日 下午3:53:33
 * @version v 0.1
 */
public class DoOnDemo {
	public static void main(String[] args) {
		Flowable.just("one", "two", "three").doOnCancel(() -> System.out.println("cancel")).take(2)
				.doOnComplete(() -> System.out.println("complete")).doOnNext(s -> System.out.println("next:" + s))
				.subscribe();
		System.out.println("---------------Single-------------------");
		Single.just("item").subscribe(new SingleObserver<String>() {

			@Override
			public void onSubscribe(Disposable paramDisposable) {
				System.out.println("onSubscribe:");
			}

			@Override
			public void onSuccess(String paramT) {
				System.out.println("onSuccess:"+paramT);
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}
		});
		System.out.println("---------------Completable-------------------");
		Completable.fromSingle(Single.just("item")).subscribe(new CompletableObserver() {

			@Override
			public void onSubscribe(Disposable paramDisposable) {
				System.out.println("onSubscribe");
			}

			@Override
			public void onError(Throwable paramThrowable) {
				paramThrowable.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		});
		System.out.println("---------------maybe-------------------");
		Maybe.just("item").subscribe(new MaybeObserver<String>() {

			@Override
			public void onComplete() {
				System.out.println("onComplete()");
			}

			@Override
			public void onError(Throwable paramThrowable) {
				paramThrowable.printStackTrace();
			}

			@Override
			public void onSubscribe(Disposable paramDisposable) {
				System.out.println("onSubscribe()");
			}

			@Override
			public void onSuccess(String value) {
				System.out.println("onSuccess():" + value);
			}
		});
	}
}
