import BinaryTree.*;

import java.util.ArrayList;
import java.util.Stack;

public class Main {
    public static Window window;

    public static BinaryTree tree;

    public static ArrayList<BinaryTree> history = new ArrayList<>();

    public static void main(String[] args) {
        tree = new BinaryTree();
        window = new Window();

        System.out.println(BinaryTree.treeOrder.toString());
    }
}