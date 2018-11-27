package com.avl.arithmetic.Sort;


import java.util.ArrayList;
import java.util.List;

/**
 * https://www.cnblogs.com/0201zcr/p/4763806.html
 * https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 * https://www.cnblogs.com/developerY/p/3166462.html
 */
public class ArraySort {

    //================================================================
    /**
     * 冒泡排序
     *
     * @param datas
     */
    public static void bubbleSort(int[] datas) {
        if (datas == null) return;

        int temp = 0;
        int size = datas.length;

        for (int i = 0; i < size - 1; i++) {

            for (int j = 0; j < size - 1 - i; j++) {

                if (datas[j] > datas[j + 1]) {
                    temp = datas[j];
                    datas[j] = datas[j + 1];
                    datas[j + 1] = temp;
                }
            }
        }
    }


    //================================================================

    /**
     * 快速排序 第一个为中间值，然后，比中间值大的放在左边，小的放在右边，然后递归。
     *
     * @param datas
     * @param start
     * @param end
     */
    public static void quickSort(int[] datas, int start, int end) {
        if (datas == null) return;

        if (start < end) {
            int baseNum = datas[start];
            int midNum;
            int i = start;
            int j = end;

            while (i <= j) {
                while (i < end && datas[i] < baseNum) {
                    i++;
                }

                while (j > start && datas[j] > baseNum) {
                    j--;
                }

                if (i <= j) {
                    midNum = datas[i];
                    datas[i] = datas[j];
                    datas[j] = midNum;
                    i++;
                    j--;
                }

                if (j > start) {
                    quickSort(datas, start, j);
                }

                if (i < end) {
                    quickSort(datas, i, end);
                }
            }
        }
    }

    //================================================================
    /**
     * 选择排序  每次把最小的放在最前面。
     *
     * @param datas
     */
    public static void selectSort(int[] datas) {
        int length = datas.length;

        for (int i = 0; i < length; i++) {
            int temp = datas[i];

            int index = i;

            for (int j = i + 1; j < length; j++) {
                if (datas[j] < temp) {
                    temp = datas[j];
                    index = j;
                }
            }

            //index 是每次最小的索引。 后面交换索引。

            datas[index] = datas[i];

            datas[i] = temp;
        }
    }

    //================================================================

    //插入排序
    public static void insertSort(int[] datas) {
        int length = datas.length;
        int insertNum;
        for (int i = 1; i < length; i++) {
            insertNum = datas[i];
            int j = i - 1;
            while (j >= 0 && datas[j] > insertNum) {
                datas[j + 1] = datas[j];
                j--;
            }
            datas[j + 1] = insertNum;
        }
    }


    //================================================================

    public static void mergeSort(int[] datas, int low, int hight) {
        int mid = (low + hight) / 2;
        if (low < hight) {
            mergeSort(datas, low, mid);
            mergeSort(datas, mid + 1, hight);
            merge(datas, low, mid, hight);
        }
    }


    private static void merge(int[] datas, int low, int mid, int hight) {
        int[] temp = new int[hight - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= hight) {
            if (datas[i] < datas[j]) {
                temp[k++] = datas[i++];
            } else {
                temp[k++] = datas[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = datas[i++];
        }

        while (j <= hight) {
            temp[k++] = datas[j++];
        }

        for (int m = 0; m < temp.length; m++) {
            datas[low + m] = temp[m];
        }
    }

    //================================================================

    public static void heapSort(int[] datas)
    {
        createHeap(datas);

        for (int i=datas.length-1;i>1;--i)
        {
            int temp =datas[1];
            datas[1] = datas[i];
            datas[i] = temp;
            HeapAdjust(datas,1,i-1);
        }
    }

    private static void createHeap(int[] datas)
    {
        int length = datas.length-1;

        for (int i=length/2;i>0;i--)
        {
            HeapAdjust(datas,i,length);
        }
    }

    private static void HeapAdjust(int[] datas,int cur,int last)
    {
        int temp = datas[cur];

        for (int j=2*cur; j<=last; j *=2)
        {
            if(j<last && datas[j]< datas[j+1])
            {
                ++j;
            }

            if (temp >= datas[j])
            {
                break;
            }
            datas[cur] = datas[j];
            cur = j;
        }
        datas[cur] = temp;
    }

    //================================================================

    private static void sheelSort(int[] datas)
    {
        if(datas == null) return;

        int len = datas.length;

        while (len!=0)
        {
            len = len/2;

            for (int i=0;i<len;i++)
            {
                for (int j=i+len;j<datas.length;j+=len)
                {
                    int k=j-len;
                    int temp = datas[j];


                    while (k>=0&& temp <datas[k])
                    {
                        datas[k+len] = datas[k];
                        k-=len;
                    }

                    datas[k+len]=temp;
                }
            }
        }
    }

    //================================================================
    //基数排序
    public static void baseSort(int[] datas)
    {
        int max = datas[0];

        //确定排序趟数
        for (int i=1;i<datas.length;i++)
        {
            if(datas[i]>max)
            {
                max = datas[i];
            }
        }

        int time = 0;
        while(max>0)
        {
            max/=10;
            time++;
        }

        List<ArrayList<Integer>> queue = new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            ArrayList<Integer>  queueItem = new ArrayList<>();
            queue.add(queueItem);
        }


        for (int i=0;i<time;i++)
        {

            //将所有的数据入桶
            for (int j=0;j<datas.length;j++)
            {
                int x= (int) (datas[j] % Math.pow(10,i+1)/(int) Math.pow(10,i));
                ArrayList<Integer> queue2 = queue.get(x);
                queue2.add(datas[j]);
                queue.set(x,queue2);
            }

            int count =0;

            for (int k=0;k<10;k++)
            {
                while (queue.get(k).size()>0)
                {
                    ArrayList<Integer>  queue3 = queue.get(k);
                    datas[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
    }


    //=============================9.计数排序===================================
    private static int[] countSort(int[] datas,int k)
    {
        int[] C = new int[k+1];
        int length = datas.length;
        int sum = 0;
        int[] B = new int[length];

        for (int i=0;i<length;i++)
        {
            C[datas[i]]+=1;
        }

        for (int i=0;i<k+1;i++)
        {
            sum+=C[i];
            C[i] = sum;
        }

        for (int i=length-1;i>=0;i--)
        {
            B[C[datas[i]]-1] = datas[i];
            C[datas[i]]--;
        }
        return B;
    }


    //================================================================

    public static void main(String[] args) {
        int[] a = {3, 4, 8, 2, 1, 6};
        //1.冒泡排序
        //bubbleSort(a);
        //print(a);

        //2.快速排序
        //quickSort(a,0,a.length-1);
        //print(a);

        //3.选择排序
        //selectSort(a);
        //print(a);

        //4.插入排序
        //insertSort(a);
        //print(a);

        //5.归并排序
        //mergeSort(a,0,a.length-1);
        //print(a);

        //6.堆排序
        //int[] b = {0,3, 4, 8, 2, 1, 6};
        //heapSort(b);
        //print(b);

        //7.希尔排序
        //sheelSort(a);
        //print(a);


        //8.基数排序
        //int[] c = {3, 4, 8, 2, 1, 6,100,98,66,1000};
        //baseSort(c);
        //print(c);

        //9.计数排序
        int[] d = countSort(a, 10);
        print(d);

    }

    private static void print(int[] a)
    {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println("");
    }

}
