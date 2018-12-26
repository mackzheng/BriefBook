package com.avl.arithmetic.Cracking;


/**
 * 923:实现一个算法，删除单向链表中间的某个节点，假定你只能够访问该节点
 */
public class Cracking923 {


    /**
     * 把下一个节点拷贝过来，删除下一个节点
     * @param n
     * @return
     */
    public static boolean deleteNode(LinkedListNode n)
    {
        if(n==null || n.next==null) return false;
        LinkedListNode next = n.next;
        n.value = next.value;
        n.next = next.next;
        return true;
    }

    public static void main(String[] args)
    {

    }

}
