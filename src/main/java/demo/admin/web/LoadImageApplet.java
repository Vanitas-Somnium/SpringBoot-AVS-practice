package demo.admin.web;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.*;

public class LoadImageApplet extends Applet {

    private BufferedImage img;

    public void init() {
        try {
            File imageFile = new File("C:\\Users\\lg\\Pictures\\Naver.png");
            img = ImageIO.read(imageFile);
            System.out.println(img.toString());
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void paint(Graphics g) {
        g.drawImage(img, 50, 50, null);
    }

    public static void main(String[] args){
        LoadImageApplet A = new LoadImageApplet();
        A.init();

    }
}
