import BinaryTree.*;

public class Main {
    public static Window window;

    public static BinaryTree tree;

    public static void main(String[] args) {
        tree = new BinaryTree();

        tree.setRoot(new BinaryTreeNode(2));
        tree.getRoot().setLeft(new BinaryTreeNode(4));
        tree.getRoot().setRight(new BinaryTreeNode(12));
        tree.getRoot().getRight().setLeft(new BinaryTreeNode(7));

        window = new Window();
    }
}