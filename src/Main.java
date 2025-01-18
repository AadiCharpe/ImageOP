import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ImageOP");
        frame.setSize(800, 600);
        JFileChooser chooser = new JFileChooser();
        JLabel label = new JLabel();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add("Open").addActionListener(e -> {
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
            int result = chooser.showOpenDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION) {
                label.setIcon(new ImageIcon(chooser.getSelectedFile().getPath()));
            }
        });
        fileMenu.add("Exit").addActionListener(e -> System.exit(0));
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.add(new JScrollPane(label));
        frame.setVisible(true);
    }
}