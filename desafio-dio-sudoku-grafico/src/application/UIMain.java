package application;

import custom.frame.MainFrame;
import custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class UIMain {

    public static void main(String[] args) {
        var dimensao = new Dimension(600,600);
        JPanel mainPanel= new MainPanel(dimensao);
        JFrame mainFrame = new MainFrame(dimensao, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
