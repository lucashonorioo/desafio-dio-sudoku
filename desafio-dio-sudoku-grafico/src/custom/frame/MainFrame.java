package custom.frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(final Dimension dimensao, final JPanel mainPanel){
        super("Sudoku");
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(mainPanel);

    }
}
