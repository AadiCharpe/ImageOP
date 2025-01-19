import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.AffineTransformOp;
import java.awt.image.RescaleOp;
import java.awt.image.Kernel;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        ImageOPFrame frame = new ImageOPFrame();
        frame.setVisible(true);
    }
}

class ImageOPFrame extends JFrame {
    private BufferedImage image;
    public ImageOPFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ImageOP");
        setSize(800, 600);
        JFileChooser chooser = new JFileChooser();
        JLabel label = new JLabel();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add("Open").addActionListener(evt -> {
            chooser.setCurrentDirectory(new File(".."));
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setFileFilter(new FileFilter() {
                public boolean accept(File f) {
                    String name = f.getName();
                    return f.isDirectory() || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".jpg") || name.endsWith(".gif");
                }

                public String getDescription() {
                    return "Image Files";
                }
            });
            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION) {
                try {
                    image = ImageIO.read(chooser.getSelectedFile());
                    label.setIcon(new ImageIcon(image));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        fileMenu.add("Exit").addActionListener(e -> System.exit(0));
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.add("Blur").addActionListener(e -> {
            float w = 1/9f;
            float[] kernel = {w, w, w, w, w, w, w, w, w};
            ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
            image = op.filter(image, null);
            label.setIcon(new ImageIcon(image));
        });

        editMenu.add("Sharpen").addActionListener(e -> {
            float[] kernel = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
            ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
            image = op.filter(image, null);
            label.setIcon(new ImageIcon(image));
        });

        editMenu.add("Brighten").addActionListener(e -> {
            RescaleOp op = new RescaleOp(1.5f, -20.0f, null);
            image = op.filter(image, null);
            label.setIcon(new ImageIcon(image));
        });

        editMenu.add("Darken").addActionListener(e -> {
            RescaleOp op = new RescaleOp(0.75f, -20.0f, null);
            image = op.filter(image, null);
            label.setIcon(new ImageIcon(image));
        });

        editMenu.add("Edge Detection").addActionListener(e -> {
            float[] kernel = {0f, -1f, 0f, -1f, 4f, -1f, 0f, -1f, 0f};
            ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
            image = op.filter(image, null);
            label.setIcon(new ImageIcon(image));
        });

        editMenu.add("Rotate").addActionListener(e -> {
            int angle = Integer.parseInt(JOptionPane.showInputDialog("Enter angle:"));
            AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), image.getWidth() / 2, image.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);
            label.setIcon(new ImageIcon(image));
        });
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
        add(new JScrollPane(label));
    }
}