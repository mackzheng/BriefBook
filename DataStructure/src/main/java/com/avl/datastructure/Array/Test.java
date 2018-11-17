package com.avl.datastructure.Array;

public class Test {

    /**
     * 小技巧：直接输入下面
     * psvm ->     public static void main(String[] args) {}
     * sout ->     System.out.printf("");
     **/
    public static void main(String[] args)
    {
//      array();
        arrayT();
    }

    public static void array()
    {
        Array array = new Array(20);
        for (int i=0;i<10;i++)
        {
            array.addLast(i);
        }
        System.out.println(array);

        array.add(1,1000);
        System.out.println(array);

        array.addFirst(1001);
        System.out.println(array);

        array.addLast(1002);
        System.out.println(array);

        array.removeElement(5);
        System.out.println(array);
    }

    public static void arrayT()
    {
        ArrayT<Integer> array = new ArrayT<Integer>(20);
        for (int i=0;i<10;i++)
        {
            array.addLast(i);
        }
        System.out.println(array);

        array.add(1,1000);
        System.out.println(array);

        array.addFirst(1001);
        System.out.println(array);

        array.addLast(1002);
        System.out.println(array);

        array.removeElement(5);
        System.out.println(array);
    }

}
