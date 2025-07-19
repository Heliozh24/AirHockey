import javax.swing.JFrame;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;
import java.awt.Component;

class GameGui extends JFrame
{
    private Color boardColor = Color.black;
    private Color ballColor = Color.white;
    private int boardColorChoice = 0;
    private int ballColorChoice = 0;
    private JLayeredPane layeredPane;
    public Color getBoardColor()
    {
        return boardColor;
    }

    public Color getBallColor()
    {
        return ballColor;
    }

    public JLayeredPane getLayeredPane()
    {
        return layeredPane;
    }

    public  void initLabel(JLabel label, int x, int y, int width, int height,Font font, Color color)    //initilaztion methods
    {
        label.setBounds(x,y,width,height);
        label.setFont(font);
        label.setForeground(color);
    }
    
    public  void initButton(JButton button, Dimension dimension, float allignment,Font font, Color foregroundColor, Color backgroundColor)
    {
        button.setPreferredSize(dimension);
        button.setMaximumSize(dimension);
        button.setAlignmentX(allignment);
        button.setFont(font);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
    }
    public  void initPanel(JPanel panel, boolean isOpaque,LayoutManager layoutManager,int x, int y, int width, int height)
    {
        panel.setOpaque(isOpaque);
        panel.setLayout(layoutManager);
        panel.setBounds(x,y,width,height);
    }

    public GameGui()
    {
        super("Air Hockey");
        JLabel background = new JLabel(new ImageIcon("background.png"));
        JLabel gameTitle = new JLabel("Air Hockey"); 
        JLabel  copyrightLabel = new JLabel(" © 2025 Helio Zhuleku.");
        JLabel settings = new JLabel("SETTINGS");
        JLabel settingsBackground = new JLabel(new ImageIcon("background.png"));
        
        JButton playButton = new JButton("PLAY");
        JButton settingsButton = new JButton("SETTINGS");
        JButton exitButton = new JButton("EXIT");
        JButton changeBallColorButton = new JButton("BALL COLOR: WHITE");
        JButton changeBoardColorButton = new JButton("BOARD COLOR: BLACK");
        JButton returnToMainButton = new JButton("← BACK TO MAIN MENU");

        layeredPane = new JLayeredPane();
        JLayeredPane settingsPane = new JLayeredPane();

        JPanel buttonPanel = new JPanel();
        JPanel settingsButtonPanel = new JPanel();
        JPanel secondSettingsPanel = new JPanel();

        background.setBounds(0, 0, 800, 600);
        settingsBackground.setBounds(0, 0, 800, 600);

        initPanel(buttonPanel,false,new BoxLayout(buttonPanel, BoxLayout.Y_AXIS),-145, 150, 1100, 350);
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(50)); 
        buttonPanel.add(settingsButton);
        buttonPanel.add(Box.createVerticalStrut(50)); 
        buttonPanel.add(exitButton);

        Dimension buttonSize = new Dimension(280, 100);
        Dimension settingsButtonSize = new Dimension(350, 70);

        initButton(playButton,buttonSize,Component.CENTER_ALIGNMENT,new Font("Monospaced", Font.BOLD, 45),new Color(255, 255, 255),Color.blue);
        initButton(settingsButton,buttonSize,Component.CENTER_ALIGNMENT,new Font("Monospaced", Font.BOLD, 45),new Color(255, 255, 255),Color.blue);
        initButton(exitButton,buttonSize,Component.CENTER_ALIGNMENT,new Font("Monospaced", Font.BOLD, 45),new Color(255, 255, 255),Color.blue);
        initButton(changeBallColorButton,settingsButtonSize,Component.LEFT_ALIGNMENT,new Font("Monospaced", Font.BOLD, 22),new Color(255, 255, 255),Color.blue);
        initButton(changeBoardColorButton,settingsButtonSize,Component.RIGHT_ALIGNMENT,new Font("Monospaced", Font.BOLD, 22),new Color(255, 255, 255),Color.blue);
        initButton(returnToMainButton, settingsButtonSize,Component.CENTER_ALIGNMENT , new Font("Monospaced", Font.BOLD, 22), new Color(255, 255, 255),Color.blue);

        initLabel(gameTitle,100,20,600,100, new Font("Monospaced", Font.BOLD, 80),Color.blue);
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        initLabel(copyrightLabel,7,538,280,20,new Font("Monospaced", Font.BOLD, 20),(Color.black));
        initLabel(settings,100,20,600,100,new Font("Monospaced", Font.BOLD, 80),Color.blue);
        settings.setHorizontalAlignment(SwingConstants.CENTER);

        initPanel(secondSettingsPanel, false,new FlowLayout(),-115, 350, 1000, 100);
        secondSettingsPanel.add(returnToMainButton);
        initPanel(settingsButtonPanel,false,new BoxLayout(settingsButtonPanel, BoxLayout.X_AXIS),20, 70, 1000, 300);
        settingsButtonPanel.add(changeBallColorButton);
        settingsButtonPanel.add(Box.createHorizontalStrut(45));
        settingsButtonPanel.add(changeBoardColorButton);

        layeredPane.setPreferredSize(new Dimension(800, 600));
        layeredPane.add(background, Integer.valueOf(0));
        layeredPane.add(gameTitle, Integer.valueOf(1)); 
        layeredPane.add(buttonPanel, Integer.valueOf(2));
        layeredPane.add(copyrightLabel, Integer.valueOf(3));
        settingsPane.setPreferredSize(new Dimension(800, 600));
        settingsPane.add(settingsBackground, Integer.valueOf(0));
        settingsPane.add(settings,Integer.valueOf(1));
        settingsPane.add(settingsButtonPanel,Integer.valueOf(2));
        settingsPane.add(Box.createVerticalStrut(500));
        settingsPane.add(secondSettingsPanel, Integer.valueOf(3));

        setContentPane(layeredPane);

        playButton.addActionListener(e -> {GameLogicPanel gamePanel = new GameLogicPanel(this); setContentPane(gamePanel); setSize(new Dimension(808,880)); revalidate(); gamePanel.requestFocusInWindow();});
        // UI Button functionalities
        Color buttonColor = new Color(87,90,230);
        playButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    playButton.setBackground(buttonColor);
                }
                else if(model.isRollover())
                {
                    playButton.setBackground(buttonColor);
                }
                else
                {
                    playButton.setBackground(Color.blue);
                }
            }
        });

         settingsButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    settingsButton.setBackground(buttonColor);
                }
                else if(model.isRollover())
                {
                    settingsButton.setBackground(buttonColor);
                }
                else
                {
                    settingsButton.setBackground(Color.blue);
                }
            }
        });

         settingsButton.addActionListener(e -> { layeredPane.setVisible(false);
            settingsPane.setVisible(true);  //changing the UI Screen
            setContentPane(settingsPane);});

         exitButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    exitButton.setBackground(new Color(255,40,70));
                    System.exit(0);
                }
                else if(model.isRollover())
                {
                    exitButton.setBackground(new Color(255, 40, 70));
                }
                else
                {
                    exitButton.setBackground(Color.blue);
                }
            }
        });

        returnToMainButton.addActionListener(e -> {layeredPane.setVisible(true);
            settingsPane.setVisible(false);
            setContentPane(layeredPane);});

        returnToMainButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    returnToMainButton.setBackground(buttonColor);
                }
                else if(model.isRollover())
                {
                    returnToMainButton.setBackground(buttonColor);
                }
                else
                {
                    returnToMainButton.setBackground(Color.blue);
                }
            }
        });

         changeBallColorButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
                Color ballColors[] = {Color.white, Color.cyan, Color.yellow, Color.magenta, Color.orange};
                String ballColorNames[] = {"White", "Cyan", "Yellow", "Purple", "Orange"};
                if(model.isPressed())
                {
                    changeBallColorButton.setBackground(buttonColor);
                    ballColorChoice = (ballColorChoice + 1) % ballColors.length;
                    ballColor = ballColors[ballColorChoice];     // changing the ball color 
                    changeBallColorButton.setText("BALL COLOR: "+ballColorNames[ballColorChoice]);
                }
                else if(model.isRollover())
                {
                    changeBallColorButton.setBackground(buttonColor);
                }
                else
                {
                    changeBallColorButton.setBackground(Color.blue);
                }
            }
        });

        changeBoardColorButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
                Color boardColors[] = {Color.black, Color.darkGray, Color.lightGray};
                String boardColorNames[] = {"Black", "Dark Gray", "Light Gray"};
                if(model.isPressed())
                {
                    changeBoardColorButton.setBackground(buttonColor);
                    boardColorChoice = (boardColorChoice + 1) % boardColors.length;
                    boardColor = boardColors[boardColorChoice];
                    changeBoardColorButton.setText("BOARD COLOR: "+boardColorNames[boardColorChoice]);
                }
                else if(model.isRollover())
                {
                    changeBoardColorButton.setBackground(buttonColor);
                }
                else
                {
                    changeBoardColorButton.setBackground(Color.blue);
                }
            }
        });
    }
}