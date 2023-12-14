package BinaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {
    private BinaryTreeNode root;
    public static Stack<Integer> treeOrder = new Stack<>();

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(BinaryTreeNode root) {
        this.root = root;
        treeOrder.add(root.getValue());
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
        treeOrder.add(root.getValue());
    }

    public BinaryTreeNode getRoot() {
        return root;
    }

    public ArrayList<Integer> preorder() {
        ArrayList<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(BinaryTreeNode node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.getValue());
        preorderTraversal(node.getLeft(), result);
        preorderTraversal(node.getRight(), result);
    }

    public ArrayList<Integer> inorder() {
        ArrayList<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(BinaryTreeNode node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.getLeft(), result);
        result.add(node.getValue());
        inorderTraversal(node.getRight(), result);
    }

    public ArrayList<Integer> postorder() {
        ArrayList<Integer> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result;
    }

    private void postorderTraversal(BinaryTreeNode node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        postorderTraversal(node.getLeft(), result);
        postorderTraversal(node.getRight(), result);
        result.add(node.getValue());
    }
}
