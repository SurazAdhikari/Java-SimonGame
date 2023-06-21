import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanelComponent());
        this.setTitle("Simon Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
