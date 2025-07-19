import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Action;
import javax.swing.ButtonModel;
import java.awt.event.ActionListener;

class GameOverScreen extends JLayeredPane
{
    private GameGui frame;
    private int redScore; //data to give on the labels
    private int blueScore;
    private int loserScore;
    private int winnerScore;
    private Color loserColor;
    private Color winnerColor;
    private String loserString;
    private String winnerString;

    public GameOverScreen(GameGui frame, GameLogicPanel gamePanel) //constructor
    {
        this.redScore = gamePanel.getRedScore();
        this.blueScore = gamePanel.getBlueScore(); //based on score we decide what data to give on the labels
        if(redScore > blueScore)
        {
            winnerScore = redScore;
            winnerColor = Color.red;
            winnerString = "RED TEAM";
            loserScore  = blueScore;
            loserColor = Color.blue;
            loserString = "BLUE TEAM";
        }
        else
        {
            winnerScore = blueScore;
            winnerColor = Color.blue;
            winnerString = "BLUE TEAM";
            loserScore  = redScore;
            loserColor = Color.red;
            loserString = "RED TEAM";
        }
        JLabel gameOverTitle = new JLabel("GAME OVER");
        JLabel gameAnalysis = new JLabel("GAME ANALYSIS");
        JLabel winnerLabel = new JLabel("WINNER OF THE GAME: " + winnerString);
        JLabel loserLabel = new JLabel("LOSER OF THE GAME: " + loserString);
        JLabel winnerScoreLabel = new JLabel("WINNER'S SCORE: " + winnerScore);
        JLabel loserScoreLabel = new JLabel("LOSER'S SCORE: " + loserScore);
        JLabel background = new JLabel(new ImageIcon("background2.png"));
        JButton backToMainMenuButton = new JButton("← BACK TO MAIN MENU");
        JPanel gameAnalysisPanel = new JPanel();
        background.setBounds(0, 0, 800,800);
        frame.initLabel(gameOverTitle,100,0,600,100, new Font("Monospaced", Font.BOLD, 80),Color.orange); //initilizations of panels, buttons, labels
        gameOverTitle.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(gameAnalysis,100,120,600,100, new Font("Monospaced", Font.BOLD, 50),Color.blue);
        gameAnalysis.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(winnerLabel,100,20,400,100, new Font("Monospaced", Font.BOLD, 30),winnerColor);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(winnerScoreLabel,100,20,400,100, new Font("Monospaced", Font.BOLD, 30),winnerColor);
        winnerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(loserLabel,100,20,400,100, new Font("Monospaced", Font.BOLD, 30),loserColor);
        loserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(loserScoreLabel,100,20,400,100, new Font("Monospaced", Font.BOLD, 30),loserColor);
        loserScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initButton(backToMainMenuButton,new Dimension(350, 100),Component.CENTER_ALIGNMENT,new Font("Monospaced", Font.BOLD, 25),new Color(255, 255, 255),Color.blue);
        backToMainMenuButton.setBounds(230, 640, 350, 80);
        backToMainMenuButton.setAlignmentX(CENTER_ALIGNMENT);
        frame.initPanel(gameAnalysisPanel,false,new BoxLayout(gameAnalysisPanel, BoxLayout.Y_AXIS),0, 200, 783, 400);
        gameAnalysisPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
        add(background,Integer.valueOf(0));
        add(gameOverTitle,Integer.valueOf(1));
        add(gameAnalysis, Integer.valueOf(2));
        add(gameAnalysisPanel,Integer.valueOf(3));
        add(backToMainMenuButton,Integer.valueOf(4));
        gameAnalysisPanel.add(winnerLabel);
        gameAnalysisPanel.add(Box.createVerticalStrut(50));
        gameAnalysisPanel.add(loserLabel);
        gameAnalysisPanel.add(Box.createVerticalStrut(50));
        gameAnalysisPanel.add(winnerScoreLabel);
        gameAnalysisPanel.add(Box.createVerticalStrut(50));
        gameAnalysisPanel.add(loserScoreLabel);

        backToMainMenuButton.addActionListener(e -> {frame.getLayeredPane().setVisible(true); //button functionality
        GameOverScreen.this.setVisible(false);
        frame.setContentPane(frame.getLayeredPane());
        frame.setSize(new Dimension(800,600)); revalidate();});

        backToMainMenuButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
                
                if(model.isPressed())
                {
                    backToMainMenuButton.setBackground(new Color(87,90,230));
                }
                else if(model.isRollover())
                {
                    backToMainMenuButton.setBackground(new Color(87,90,230));
                }
                else
                {
                    backToMainMenuButton.setBackground(Color.blue);
                }
            }
        });
    } 
}