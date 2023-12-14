import BinaryTree.*;
import Components.CirclePanel;
import Components.ContextMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Window extends JFrame{

    private final JPanel HUD;
    private final JPanel DESC;
    private final JPanel OPTIONS;
    private final JPanel MAIN_WINDOW;

    private JLabel preorder;
    private JLabel inorder;
    private JLabel postorder;

    private final Color ACCENT_COLOR = new Color(114, 204, 255);

    public Window(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1280, 720));
        this.setMinimumSize(new Dimension(1280, 720));
        this.setLayout(new BorderLayout());
        this.pack();

        HUD = new JPanel();
        DESC = new JPanel();
        OPTIONS = new JPanel();
        MAIN_WINDOW = new JPanel();

        HUD.setPreferredSize(new Dimension(100,64));
        HUD.setBackground(Color.RED);
        DESC.setPreferredSize(new Dimension(100,64));
        DESC.setBackground(new Color(17, 25, 38));
        OPTIONS.setPreferredSize(new Dimension(304,100));
        OPTIONS.setBackground(new Color(237, 242, 252));

        resize();

        MAIN_WINDOW.setBackground(Color.WHITE);

        DESC.setLayout(new GridBagLayout());
        OPTIONS.setBorder(BorderFactory.createMatteBorder(0,1,0,0, new Color(241, 244, 250)));

        final int IMAGE_SIZE = 18;

        BufferedImage preOrderImage = null;
        BufferedImage inOrderImage = null;
        BufferedImage postOrderImage = null;

        try {
            preOrderImage = ImageIO.read(new File("res/pre.png"));
            inOrderImage = ImageIO.read(new File("res/in.png"));
            postOrderImage = ImageIO.read(new File("res/post.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        preorder = new JLabel("PreOrder: N/A", new ImageIcon(preOrderImage.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH)), SwingConstants.LEADING);
        inorder = new JLabel("InOrder: N/A", new ImageIcon(inOrderImage.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH)), SwingConstants.LEADING);
        postorder = new JLabel("PostOrder: N/A", new ImageIcon(postOrderImage.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH)), SwingConstants.LEADING);

        preorder.setForeground(Color.WHITE);
        inorder.setForeground(Color.WHITE);
        postorder.setForeground(Color.WHITE);

        DESC.add(preorder);
        DESC.add(Box.createHorizontalStrut(10));
        DESC.add(inorder);
        DESC.add(Box.createHorizontalStrut(10));
        DESC.add(postorder);

        //setup OPTIONS tab

        JButton button1 = new JButton("BST Sort");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.tree.reconstructAsBST();
                updateTree();
            }
        });

        JButton button2 = new JButton("Undo");
        JButton button3 = new JButton("Clear All");

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.tree = new BinaryTree();
                updateTree();
            }
        });

        OPTIONS.setLayout(new BoxLayout(OPTIONS, BoxLayout.Y_AXIS));

        OPTIONS.add(button1);
        OPTIONS.add(button2);
        OPTIONS.add(button3);

        MAIN_WINDOW.setLayout(null);

        updateTree();

        //this.add(HUD, BorderLayout.NORTH);
        this.add(DESC, BorderLayout.SOUTH);
        this.add(OPTIONS, BorderLayout.EAST);
        this.add(MAIN_WINDOW, BorderLayout.CENTER);

        ImageIcon img = new ImageIcon("res/icon.png");

        this.setIconImage(img.getImage());

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void updateTree(){
        preorder.setText(convertListToText(Main.tree.preorder(), "PreOrder: "));
        inorder.setText(convertListToText(Main.tree.inorder(), "InOrder: "));
        postorder.setText(convertListToText(Main.tree.postorder(), "PostOrder: "));

        renderTree(Main.tree, MAIN_WINDOW);
    }

    private String convertListToText(ArrayList<Integer> list, String prefix){
        String returnVal = prefix + list.get(0).toString();

        for(int i = 1; i < list.size(); i++) returnVal += ", " + list.get(i).toString();

        return returnVal;
    }

    private static final int NODE_SIZE = 64; // Size of each node
    private static final int VERTICAL_SPACING = 64; // Vertical spacing between levels
    private static final int HORIZONTAL_SPACING = 30; // Horizontal spacing between nodes

    private void renderTree(BinaryTree tree, JPanel panel) {
        panel.removeAll();
        Map<BinaryTreeNode, Point> nodePositions = new HashMap<>();
        calculatePositions(tree.getRoot(), 0, 0, 0, nodePositions);

        for (Map.Entry<BinaryTreeNode, Point> entry : nodePositions.entrySet()) {
            BinaryTreeNode node = entry.getKey();
            Point p = entry.getValue();

            CirclePanel circle = createNodeCircle(node.getValue());
            circle.setBounds(p.x, p.y, NODE_SIZE, NODE_SIZE);
            panel.add(circle);

            // Check for left child
            if (node.getLeft() != null) {
                Point leftChildPoint = nodePositions.get(node.getLeft());
                drawLine(panel, p.x + NODE_SIZE / 2, p.y + NODE_SIZE, leftChildPoint.x + NODE_SIZE / 2, leftChildPoint.y);
            } else if (node.getLeft() == null && node.getRight() == null) { // Leaf node
                int leftPanelX = Math.max(p.x - NODE_SIZE, 0); // Adjust position to avoid going off-screen
                JPanel addLeftPanel = createAddNodePanel(node, true, panel);
                addLeftPanel.setBounds(leftPanelX, p.y + NODE_SIZE, NODE_SIZE, NODE_SIZE);
                panel.add(addLeftPanel);
            }

            // Check for right child
            if (node.getRight() != null) {
                Point rightChildPoint = nodePositions.get(node.getRight());
                drawLine(panel, p.x + NODE_SIZE / 2, p.y + NODE_SIZE, rightChildPoint.x + NODE_SIZE / 2, rightChildPoint.y);
            } else if (node.getLeft() == null && node.getRight() == null) { // Leaf node
                JPanel addRightPanel = createAddNodePanel(node, false, panel);
                addRightPanel.setBounds(p.x + NODE_SIZE, p.y + NODE_SIZE, NODE_SIZE, NODE_SIZE);
                panel.add(addRightPanel);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createAddNodePanel(BinaryTreeNode parent, boolean isLeftChild, JPanel panel) {
        JPanel addNodePanel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField();
        JButton addButton = new JButton("Add");

        addButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(textField.getText());
                BinaryTreeNode newNode = new BinaryTreeNode(value);

                if (isLeftChild) {
                    parent.setLeft(newNode);
                } else {
                    parent.setRight(newNode);
                }

                renderTree(Main.tree, panel); // Assuming Main.tree is accessible and updatable
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input. Please enter a number.");
            }
        });

        addNodePanel.add(textField, BorderLayout.CENTER);
        addNodePanel.add(addButton, BorderLayout.EAST);
        addNodePanel.setOpaque(false);

        return addNodePanel;
    }

    private CirclePanel createNodeCircle(int value) {
        CirclePanel circle = new CirclePanel();
        circle.setPreferredSize(new Dimension(NODE_SIZE, NODE_SIZE));
        circle.setLayout(new GridBagLayout());
        circle.add(new JLabel(String.valueOf(value)), new GridBagConstraints());
        circle.setOpaque(false);
        return circle;
    }

    private void drawLine(JPanel panel, int x1, int y1, int x2, int y2) {
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);

                // Determine start and end points for the line within the panel
                int startX = (x1 < x2) ? 0 : this.getWidth();
                int startY = 0;
                int endX = (x1 < x2) ? this.getWidth() : 0;
                int endY = this.getHeight();

                g.drawLine(startX, startY, endX, endY);
            }
        };

        int lineX = Math.min(x1, x2);
        int lineY = Math.min(y1, y2);
        int lineWidth = Math.abs(x1 - x2);
        int lineHeight = Math.abs(y1 - y2);

        linePanel.setOpaque(false);
        linePanel.setBounds(lineX, lineY, lineWidth, lineHeight);
        panel.add(linePanel);
    }

    private void calculatePositions(BinaryTreeNode node, int depth, int x, int position, Map<BinaryTreeNode, Point> nodePositions) {
        if (node == null) {
            return;
        }

        int leftWidth = getWidth(node.getLeft());
        int rightWidth = getWidth(node.getRight());

        int currentX = x + leftWidth * (NODE_SIZE + HORIZONTAL_SPACING);
        nodePositions.put(node, new Point(currentX, depth * (NODE_SIZE + VERTICAL_SPACING)));

        calculatePositions(node.getLeft(), depth + 1, x, position * 2, nodePositions);
        calculatePositions(node.getRight(), depth + 1, currentX + NODE_SIZE + HORIZONTAL_SPACING, position * 2 + 1, nodePositions);
    }

    private int getWidth(BinaryTreeNode node) {
        if (node == null) return 0;
        return getWidth(node.getLeft()) + 1 + getWidth(node.getRight());
    }

    private void resize(){
        MAIN_WINDOW.setSize(new Dimension(this.getWidth() - 304,this.getHeight() - 64));
    }
}
