package vue;

import javax.swing.*;
import java.awt.*;

public class Img extends JLabel {
    private static final long serialVersionUID = -9129537057351390955L;
    private int coordX;
    private int coordY;
    private Image img;

    public Img(Image img) {
        this.img = img;
        coordX = 0;
        coordY = 0;
    }
    public void paintComponent(Graphics g) {
        g.drawImage(img, coordX, coordY, this.getWidth(), this.getHeight(), this);
    }
}