import BinaryTree.*;

import java.util.Stack;

public class Main {
    public static Window window;

    public static BinaryTree tree;

    public static void main(String[] args) {
        tree = new BinaryTree();

        tree.setRoot(new BinaryTreeNode(2));
        tree.getRoot().setLeft(new BinaryTreeNode(4));
        tree.getRoot().setRight(new BinaryTreeNode(12));
        tree.getRoot().getRight().setLeft(new BinaryTreeNode(7));
        tree.getRoot().getRight().setRight(new BinaryTreeNode(4));
        tree.getRoot().getRight().getLeft().setLeft(new BinaryTreeNode(1));
        tree.getRoot().getRight().getLeft().setRight(new BinaryTreeNode(9));
        window = new Window();

        System.out.println(BinaryTree.treeOrder.toString());
    }
}