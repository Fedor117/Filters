package view;

import controller.FilterController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicturesFrame extends JFrame {

    private JScrollPane    scrollPane       = new JScrollPane();
    private JPanel         mainPanel        = new JPanel();
    private JLabel         firstImageLabel  = new JLabel();
    private JLabel         secondImageLabel = new JLabel();
    private JLabel         thirdImageLabel  = new JLabel();
    private BufferedImage  inputImage;

    public PicturesFrame() {
        super("Pictures Frame");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = new Dimension(toolkit.getScreenSize());
        setLocation(dimension.width / 3, dimension.height / 5);
        setSize(dimension.width / 6, dimension.height / 3);

        this.getImage();

        FilterController controller         = new FilterController(inputImage);
        BufferedImage    edgedImage         = controller.edgesFilter();
        BufferedImage    embossedImage      = controller.embossFilter();
        BufferedImage    sharpenImage       = controller.sharpenFilter();
        Icon             firstImageIcon     = new ImageIcon(edgedImage);
        Icon             secondImageIcon    = new ImageIcon(embossedImage);
        Icon             thirdImageIcon     = new ImageIcon(sharpenImage);
//        outputImagePanel.setImage(output);
//        outputImagePanel.getGraphics().drawImage(output, 0, 0, null);

        this.saveImage(edgedImage);
        this.saveImage(embossedImage);
        this.saveImage(sharpenImage);

        firstImageLabel.setIcon(firstImageIcon);
        secondImageLabel.setIcon(secondImageIcon);
        thirdImageLabel.setIcon(thirdImageIcon);
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(firstImageLabel);
        mainPanel.add(secondImageLabel);
        mainPanel.add(thirdImageLabel);
        scrollPane.add(mainPanel);
        this.add(scrollPane);
        this.pack();
        this.setVisible(true);
    }

    public void getImage() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource("Lenna.jpg").getFile());

        try {
            inputImage = ImageIO.read(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(BufferedImage imageToSave) {
        try {
            ImageIO.write(imageToSave, "png", new File("outputImages//filter." + imageToSave + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getInputImage() {
        return inputImage;
    }

}
