package com.example;

import java.util.Arrays;

public class Sort {


    static void quickSort(int[] a, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = a[start];
        int i = start;
        int j = end;
        while (i < j) {
            while ((i < j&&a[j]>pivot&&j-->i));
            System.out.println("pivot:"+pivot+" "+Arrays.toString(a));

            while ((i < j && a[i]<pivot)&&i++<j);
            if(a[i]==a[j]){
                i=j;
                break;
            }
            int t=a[i];
            a[i]=a[j];
            a[j]=t;

            System.out.println("pivot:"+pivot+" "+Arrays.toString(a)+" i="+i+" j="+j);
        }

        a[i] = pivot;
        quickSort(a, start, i - 1);
        quickSort(a, i + 1, end);


    }


    static void  dualPivotQuickSort(int []a,int start,int end){
        if(start<end){
            if(a[start]>a[end]){
                int t=a[start];
                a[start]=a[end];
                a[end]=t;
            }
            System.out.println(Arrays.toString(a));
            int pivot1=a[start];
            int pivot2=a[end];
            int less=start;
            int great=end;
            out_loop:
            for(int k=less;++k<great;){

                int ak=a[k];

                //less 坐标表示小于
                //great坐标表示大于
                //k坐标表示小于pivot2大于pivot1
                if(ak<=pivot1){
                    int t=a[k];
                    a[k]=a[++less];
                    a[less]=t;
                }else if(ak>pivot2){
                    while(a[great]>=pivot2){
                        if(great--==k){
                            break out_loop;
                        }
                    }

                    //a[great]<pivot1
                    if(a[great]<pivot1){
                        int t=a[less];
                        a[less]=a[great];
                        a[great]=t;
                        ++less;
                    }else {
                        a[k]=a[great];
                        a[great]=ak;
                    }

                }
            }

            a[end]=a[great];
            a[great]=pivot2;

            a[start]=a[less];
            a[less]=pivot1;
            // <=pivot1
            dualPivotQuickSort(a, start, less-1);
              // pivot1<xx<pivot2
            dualPivotQuickSort(a, less+1 , great-1);
            //>=pivot2
            dualPivotQuickSort(a, great+1 , end);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{65,83,12,18,47,66,59,21,83,15};


        System.out.println(Arrays.toString(array));
        quickSort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));

        int[] ma=new int[]{65,83,12,18,47,66,59,21,83,15};
        System.out.println("dualPivot:"+Arrays.toString(ma));
        dualPivotQuickSort(ma,0,9);
        System.out.println(Arrays.toString(ma));
    }
}
