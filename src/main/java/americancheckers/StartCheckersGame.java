package americancheckers;

import javax.swing.*;

public class StartCheckersGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CheckersGUI::new);
    }
}
