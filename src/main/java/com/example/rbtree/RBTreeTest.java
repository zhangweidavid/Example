/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月2日
 */

package com.example.rbtree;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月2日 下午8:46:38
 * @version  v 0.1
 */
/**
 * Java 语言: 二叉查找树
 *
 * @author skywang
 * @date 2013/11/07
 */
public class RBTreeTest {

    private static final int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
    private static final boolean mDebugInsert = false;    // "插入"动作的检测开关(false，关闭；true，打开)

    public static void main(String[] args) {
        int i, ilen = a.length;
        RBTree<Integer> tree=new RBTree<Integer>();

        System.out.printf("== 原始数据: ");
        for(i=0; i<ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for(i=0; i<ilen; i++) {
            tree.insert(a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            if (mDebugInsert) {
                System.out.printf("== 添加节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        System.out.printf("== 前序遍历: ");
        tree.preEach();

        System.out.printf("\n== 中序遍历: ");
        tree.middleEach();

        System.out.printf("\n== 后序遍历: ");
        tree.postEach();
        System.out.printf("\n");

    
        System.out.printf("== 树的详细信息: \n");
        tree.print();
        System.out.printf("\n");

       

    }
}
