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

    public int countLeaves() {
        if (this.left == null && this.right == null) return 1;

        int leftLeaves = (this.left == null) ? 0 : this.left.countLeaves();
        int rightLeaves = (this.right == null) ? 0 : this.right.countLeaves();
        return leftLeaves + rightLeaves;
    }
}