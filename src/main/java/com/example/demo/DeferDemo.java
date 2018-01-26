/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月22日
 */

package com.example.demo;

import io.reactivex.Flowable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月22日 下午3:42:36
 * @version v 0.1
 */
public class DeferDemo {
	public static void main(String[] args) {
		FlowableValue v = new FlowableValue();
		Flowable<String> f = v.getFlowable();
		Flowable<String> d = v.getDeferFlowable();
		v.setValue("update");
		//输出的结果是 default
		f.subscribe(System.out::println);
		//输出的结果是update
		d.subscribe(System.out::println);
	}
	public static class FlowableValue {
		private String value = "default";
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Flowable<String> getFlowable() {
			return Flowable.just(value);
		}
		public Flowable<String> getDeferFlowable() {
			return Flowable.defer(() -> Flowable.just(value));
		}
	}
}
