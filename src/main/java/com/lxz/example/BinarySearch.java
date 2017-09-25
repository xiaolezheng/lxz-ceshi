package com.lxz.example;


import com.lxz.util.JsonUtil;

/**
 * Created by xiaolezheng on 17/9/24.
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int value = binary(a, 10);
        System.out.println(value);


        int[] array = {2, 1, 5, 4, 6, 3};
        bubbleSort(array);


        int[] array1 = {2, 1, 5, 6, 3, 4, 1};
        InsertSort(array1);
        System.out.println(JsonUtil.encode(array1));

        System.out.println(fib(5));
        System.out.println(fib3(5));

        new Test().test();

        System.out.println("--------------------------------");
        System.out.println(1 << 3);
        System.out.println(34 % 32);
    }

    public static int binary(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (target == array[middle]) {
                return middle;
            }

            if (target > middle) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }

        return -1;
    }


    public static void bubbleSort(int[] array) {
        int temp;
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j + 1] > array[j]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void InsertSort(int[] arr) {
        int i, j;
        int n = arr.length;
        int target;

        //假定第一个元素被放到了正确的位置上
        //这样，仅需遍历1 - n-1
        for (i = 1; i < n; i++) {
            j = i;
            target = arr[i];

            while (j > 0 && target < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            arr[j] = target;
        }
    }

    public static int fib(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static long fib2(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }

        int res = 0;
        int a = 1;
        int b = 1;
        for (int i = 3; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }


    public static int fib3(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }

        int a = 1, b = 1, res = 0;
        for (int i = 3; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
            System.out.println(i + "->" + res);
        }

        return res;
    }


    public static class Test {
        int numSize = 100;

        int arraySize = (int) Math.ceil((double) numSize / 32);

        private int array[] = new int[arraySize];

        /**
         * @param
         */
        public void test() {

            //也可以使用bitset
            Test test = new Test();
            test.initBitMap();
            int sortArray[] = new int[]{1, 4, 32, 2, 6, 9};
            for (int i = 0; i < sortArray.length; i++) {
                test.set1(sortArray[i]);
            }
            for (int i = 0; i < test.numSize; i++) {
                if (test.get(i) != 0) {
                    System.out.print((i) + " ");
                }
            }

        }

        public void initBitMap() {
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
        }

        public void set1(int pos) {
            array[pos >> 5] = array[pos >> 5] | (1 << (31 - pos % 32)); //给相应位置1

        }

        public int get(int pos) {
            return array[pos >> 5] & (1 << (31 - pos % 32));
        }
    }
}
