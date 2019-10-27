
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;


public class UI extends JFrame{

        private JLabel jlb = new JLabel();
        private ImageIcon image;
        private int width = 400, height = 400;

        public UI() {
            this.setSize(800, 600);
            this.setLayout(null);

            image = new ImageIcon("image/1.jpg");
            // image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT));
            Image img = image.getImage();
            img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            image.setImage(img);
            jlb.setIcon(image);

            this.add(jlb);
            jlb.setSize(width, height);
            this.setVisible(true);
        }

        public static void main(String[] args) {
            new UI();
        }
}


