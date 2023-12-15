package BinaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    private BinaryTreeNode root;
    public static Queue<Integer> treeOrder = new LinkedList<>();

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

    public void createBinarySearchTree() {
        root = null;

        Queue<Integer> tempQueue = new LinkedList<>();

        while(!treeOrder.isEmpty()) tempQueue.add(treeOrder.poll());

        while (!tempQueue.isEmpty()) {
            insertIntoBST(tempQueue.poll());
        }
    }

    private void insertIntoBST(int value) {
        root = insertRecursive(root, value);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode node, int value) {
        if (node == null) {
            return new BinaryTreeNode(value);
        }

        if (value < node.getValue()) {
            node.setLeft(insertRecursive(node.getLeft(), value));
        } else if (value > node.getValue()) {
            node.setRight(insertRecursive(node.getRight(), value));
        }

        return node;
    }
}
