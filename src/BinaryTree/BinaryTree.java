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

    public void reconstructAsBST() {
        if (treeOrder.isEmpty()) {
            root = null;
            return;
        }

        Queue<Integer> tempQueue = new LinkedList<>();

        while(!treeOrder.isEmpty()) tempQueue.add(treeOrder.poll());

        root = new BinaryTreeNode(tempQueue.poll()); // Set the first element as root

        while (!tempQueue.isEmpty()) {
            insertIntoBST(root, tempQueue.poll());
        }
    }

    private void insertIntoBST(BinaryTreeNode node, int value) {
        if (value < node.getValue()) {
            if (node.getLeft() == null) {
                node.setLeft(new BinaryTreeNode(value));
            } else {
                insertIntoBST(node.getLeft(), value);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new BinaryTreeNode(value));
            } else {
                insertIntoBST(node.getRight(), value);
            }
        }
    }
}
