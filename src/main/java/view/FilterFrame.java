package view;

import controller.FilterController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FilterFrame extends JFrame {

    public static final String NAME = "Filter Frame";

    private FilterController         mController;
    private ArrayList<JLabel>        labels      = new ArrayList<JLabel>();
    private JPanel                   imagePanel  = new JPanel();

    public FilterFrame(FilterController controller) {
        super(NAME);

        this.mController = controller;
        mController.processImages();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(dimension.width / 15, dimension.height / 5);
        setSize(dimension.width / 5 * 3, dimension.height / 5 * 3);

        imagePanel.setLayout(new FlowLayout());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(imagePanel);

        this.setImages();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.add(scrollPane);
        this.pack();
        this.setVisible(true);
    }

    private void setImages() {
        ArrayList<BufferedImage> images = mController.getImages();

        for (BufferedImage image: images) {
            ImageIcon icon = new ImageIcon(image);
            JLabel label = new JLabel();
            label.setIcon(icon);
            labels.add(label);
        }

        for (JLabel label : labels)
            imagePanel.add(label);
    }
}
