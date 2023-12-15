package BinaryTree;

public class BinaryTreeNode {
    int value;
    BinaryTreeNode left;
    BinaryTreeNode right;

    public BinaryTreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
        BinaryTree.treeOrder.add(left.getValue());
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
        BinaryTree.treeOrder.add(right.getValue());
    }

    public BinaryTreeNode clone() {
        BinaryTreeNode newNode = new BinaryTreeNode(this.value);

        if (this.left != null) {
            newNode.left = this.left.clone();
        }
        if (this.right != null) {
            newNode.right = this.right.clone();
        }

        return newNode;
    }
}