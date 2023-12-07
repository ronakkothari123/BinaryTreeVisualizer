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
        MAIN_WINDOW.setPreferredSize(new Dimension(640,640));
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

        initializeTree();

        //this.add(HUD, BorderLayout.NORTH);
        this.add(DESC, BorderLayout.SOUTH);
        this.add(OPTIONS, BorderLayout.EAST);
        this.add(MAIN_WINDOW, BorderLayout.CENTER);

        this.pack();

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

        MAIN_WINDOW.add(root);
    }
}
