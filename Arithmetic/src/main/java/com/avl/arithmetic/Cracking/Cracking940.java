package com.avl.arithmetic.Cracking;

public class Cacking940 {

   static class TreeNode
    {
        public TreeNode() {
        }

        TreeNode leftNode;
        TreeNode rightNode;
    }

    public static int getHeight(TreeNode  root)
    {
        if(root == null) return 0;

        return Math.max(getHeight(root.leftNode),getHeight(root.rightNode))+1;
    }

    public static boolean isBalanced(TreeNode root)
    {
        if(root == null) return true;

        int  height = getHeight(root.leftNode) - getHeight(root.rightNode);

        if(Math.abs(height)>1)
        {
            return false;
        }else
        {
            return isBalanced(root.leftNode) && isBalanced(root.rightNode);
        }
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.leftNode = new TreeNode();
        root.rightNode = new TreeNode();

        System.out.println(getHeight(root));

    }

}
