import javax.swing.JFrame;
import javax.swing.ImageIcon;


class AirHockeyGame // the window of the game
{
    public static void main(String[] args) {
        GameGui gameGui = new GameGui();
        gameGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGui.setSize(800, 600);
        ImageIcon icon = new ImageIcon("imageIcon.png");
        gameGui.setIconImage(icon.getImage());
        gameGui.setVisible(true);
    
    }
}