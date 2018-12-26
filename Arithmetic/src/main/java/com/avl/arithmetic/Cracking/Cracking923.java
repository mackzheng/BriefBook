package com.avl.arithmetic.Cracking;


/**
 * 实现一个算法，找出单向链表中倒数第K个节点
 */
public class Cracking922 {

    //递归法
    public static int nthToLast(LinkedListNode head, int k) {
        if (head == null) return 0;

        int i = nthToLast(head.next, k) + 1;

        if (i == k) {
            System.out.println("pre=" + i + " " + k);

            System.out.println(head.value);
            return k;
        }
        return i;
    }


    //迭代法
    public LinkedListNode nthToLast2(LinkedListNode head, int k) {
        if (k <= 0) return null;
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;

        for (int i = 0; i < k - 1; i++) {
            if(p2 == null) return null;
            p2 = p2.next;
        }

        if(p2 == null)  return null;

        while (p2.next !=null)
        {
            p1 = p1.next;
            p2 = p2.next;
        }

        return p2;
    }


    public static void main(String[] args) {
        LinkedListNode node = LinkedListNode.createListNode();

        LinkedListNode cur = node;
        while (cur != null) {
            System.out.println(cur.value);
            cur = cur.next;
        }

        System.out.println(nthToLast(node, 9));
    }

}
