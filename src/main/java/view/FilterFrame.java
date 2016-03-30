package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FilterFrame extends JFrame {

    public static final String NAME = "Filter Frame";

    private ArrayList<BufferedImage> images;
    private ArrayList<JLabel>        labels;
    private JPanel                   imagePanel;

    public FilterFrame(ArrayList<BufferedImage> images) {
        super(NAME);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(dimension.width / 15, dimension.height / 5);
        setSize(dimension.width / 5 * 3, dimension.height / 5 * 3);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus is not available");
        }

        setImages();
        imagePanel.setLayout(new FlowLayout());
        this.add(imagePanel);
        this.pack();
    }

    private void setImages() {
        for (BufferedImage image: images) {
            ImageIcon icon = new ImageIcon(image);
            JLabel label = new JLabel();
            label.setIcon(icon);
            labels.add(label);
        }

        for (JLabel label : labels) {
            imagePanel.add(label);
        }
    }
}
