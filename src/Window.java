import BinaryTree.*;
import Components.CirclePanel;
import Components.ContextMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        JButton button2 = new JButton("Undo");
        JButton button3 = new JButton("Clear All");

        OPTIONS.setLayout(new BoxLayout(OPTIONS, BoxLayout.Y_AXIS));

        OPTIONS.add(button1);
        OPTIONS.add(button2);
        OPTIONS.add(button3);

        MAIN_WINDOW.setLayout(null);

        renderTree(Main.tree, MAIN_WINDOW);

        //this.add(HUD, BorderLayout.NORTH);
        this.add(DESC, BorderLayout.SOUTH);
        this.add(OPTIONS, BorderLayout.EAST);
        this.add(MAIN_WINDOW, BorderLayout.CENTER);

        ImageIcon img = new ImageIcon("res/icon.png");

        this.setIconImage(img.getImage());

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initializeTree(){
        CirclePanel root = new CirclePanel();
        root.setPreferredSize(new Dimension(64, 64));
        root.setBackground(Color.WHITE);

        root.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        root.add(new JLabel("Root"), gbc);
        root.setComponentPopupMenu(new ContextMenu(new String[]{"Edit", "Delete"}));

        JPanel line = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(0, 0, 128, 128);
            }
        };

        line.setPreferredSize(new Dimension(128, 128));
        line.setBackground(Color.BLUE);

        MAIN_WINDOW.add(root);
        MAIN_WINDOW.add(line);

        Insets insets = MAIN_WINDOW.getInsets();
        Dimension size = root.getPreferredSize();

        root.setBounds((MAIN_WINDOW.getWidth() - size.width) / 2 + insets.left, 24 + insets.top, size.width, size.height);
    }

    private static final int NODE_SIZE = 64; // Size of each node
    private static final int VERTICAL_SPACING = 64; // Vertical spacing between levels
    private static final int HORIZONTAL_SPACING = 30; // Horizontal spacing between nodes

    public void renderTree(BinaryTree tree, JPanel mainPanel) {
        if (tree.getRoot() == null) {
            return;
        }

        int treeWidth = tree.getMaxWidth();
        int level = 0;

        // Use a recursive method to render each level
        renderNode(tree.getRoot(), level, 0, treeWidth, mainPanel);
    }

    private void renderNode(BinaryTreeNode node, int level, int position, int width, JPanel panel) {
        if (node == null) {
            return;
        }

        // Calculate the horizontal position of the node
        int x = position * (NODE_SIZE + HORIZONTAL_SPACING) + (width - NODE_SIZE) / 2 + panel.getWidth() / 2;
        int y = level * (NODE_SIZE + VERTICAL_SPACING);

        // Draw the node
        CirclePanel nodePanel = createNodePanel(node.getValue());
        nodePanel.setBounds(x, y, NODE_SIZE, NODE_SIZE);
        panel.add(nodePanel);

        // Draw lines to children if they exist
        if (node.getLeft() != null) {
            drawLine(panel, x, y, x - HORIZONTAL_SPACING, y + VERTICAL_SPACING);
            renderNode(node.getLeft(), level + 1, position * 2, width / 2, panel);
        }

        if (node.getRight() != null) {
            drawLine(panel, x, y, x + HORIZONTAL_SPACING, y + VERTICAL_SPACING);
            renderNode(node.getRight(), level + 1, position * 2 + 1, width / 2, panel);
        }
    }

    private CirclePanel createNodePanel(int value) {
        CirclePanel nodePanel = new CirclePanel();
        nodePanel.setPreferredSize(new Dimension(NODE_SIZE, NODE_SIZE));
        nodePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        nodePanel.add(new JLabel(String.valueOf(value)), gbc);
        nodePanel.setBackground(Color.BLUE);
        // Add context menu or other customizations here
        return nodePanel;
    }

    private void drawLine(JPanel panel, int x1, int y1, int x2, int y2) {
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(x1, y1, x2, y2);
            }
        };
        linePanel.setPreferredSize(new Dimension(Math.abs(x2 - x1), Math.abs(y2 - y1)));
        linePanel.setBounds(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        linePanel.setBackground(Color.RED);
        panel.add(linePanel);
    }

    private void resize(){
        MAIN_WINDOW.setSize(new Dimension(this.getWidth() - 304,this.getHeight() - 64));
    }
}
