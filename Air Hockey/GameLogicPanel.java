import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.Box;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.security.Key;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Dimension;

class GameLogicPanel extends JPanel implements KeyListener
{
    private Color ballColor; //Colors
    private Color boardColor;
    private Color borderColor;
    private Color lineColor;
    private GameGui frame;
    private PlayerHandle redPlayerHandle = new PlayerHandle(Color.red,360,110,75,75,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D,70,340,0,701);
    private PlayerHandle bluePlayerHandle = new PlayerHandle(Color.blue,360,660,75,75, KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,420,700,0,701);
    private Ball ball;
    private JPanel redTeamPanel = new JPanel(); // panels
    private JPanel blueTeamPanel = new JPanel();
    private JPanel playerScoredPanel = new JPanel();
    private JLabel redTeamScore = new JLabel("RED TEAM SCORE: 0");
    private JLabel blueTeamScore = new JLabel("BLUE TEAM SCORE: 0"); //labels
    private JLabel playerScoredLabel = new JLabel();
    private int blueScore = 0; //scores
    private int redScore = 0;
    private ArrayList<Rectangle> upDownBorder = new ArrayList<>();
    private ArrayList<Rectangle> leftRightBorder = new ArrayList<>(); //borders
    private boolean[] keys = new boolean[256];
    private Timer mainTimer;
    private Timer scoreAnimationTimer; //timers
    private Timer flashBlueTimer;
    private Timer flashRedTimer;
    private Timer winTimer;
    private Timer flashWinTimer;
    private int moveTime = 20;
    private int scoreTime = 2000;  // timer set times
    private int flashColorTime = 120;
    private int flashWinTime = 120;
    private int winTime = 3000;
    private int[] redFlashCounter = {0}; //counters for color flashes
    private int[] blueFlashCounter = {0};
    private int[] winFlashCounter = {0};

    public int getRedScore() //getters for scores
    {
        return redScore;
    }

    public int getBlueScore()
    {
        return blueScore;
    }

    public GameLogicPanel(GameGui frame) //constructor
    {
        this.frame = frame;
        ballColor = frame.getBallColor();
        this.ball = new Ball(ballColor,377,406,30,30);
        boardColor = frame.getBoardColor();
        if(boardColor == Color.black) //deciding which border color to have
            borderColor = Color.white;
        else
            borderColor = Color.black;
        if(boardColor == Color.black)
            lineColor = new Color(150,150,150,120);
        else if( boardColor == Color.white)
            lineColor = Color.DARK_GRAY;
        else
            lineColor = Color.black;
        addKeyListener(this);
        setFocusable(true); 
        setBackground(boardColor);
        setLayout(new BorderLayout());
        blueTeamScore.setFont(new Font("Serif", Font.BOLD, 30));
        redTeamScore.setFont(new Font("Serif", Font.BOLD, 30));
        blueTeamScore.setAlignmentX(CENTER_ALIGNMENT); // panel and label setting
        redTeamScore.setAlignmentY(BOTTOM_ALIGNMENT);
        playerScoredLabel.setFont(new Font("Serif", Font.BOLD, 60));
        playerScoredLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerScoredLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        blueTeamScore.setForeground(Color.blue);
        redTeamScore.setForeground(Color.red);
        blueTeamPanel.setBackground(boardColor);
        playerScoredPanel.setBackground(new Color(0,0,0,0));
        playerScoredLabel.setBackground(boardColor);
        playerScoredPanel.setLayout(new BoxLayout(playerScoredPanel,BoxLayout.Y_AXIS));
        redTeamPanel.setBackground(boardColor);
        blueTeamPanel.add(blueTeamScore);
        redTeamPanel.add(redTeamScore);
        playerScoredPanel.add(Box.createVerticalStrut(346));
        playerScoredPanel.add(playerScoredLabel);
        playerScoredPanel.setVisible(false);
        add(playerScoredPanel);
        add(redTeamPanel, BorderLayout.NORTH);
        add(blueTeamPanel, BorderLayout.SOUTH);
        for(int x = 0; x <= 800-15; x+=22) 
        {
            if( x < 250 || x > 510)
            {
                upDownBorder.add(new Rectangle(x,70,19,4));  //border placement
                upDownBorder.add(new Rectangle(x,800-30,19,4));
            }
        }

        for(int y = 70; y <= 800-30; y+=22)
        {
            leftRightBorder.add(new Rectangle(0,y,4,19));  //border placement
            leftRightBorder.add(new Rectangle(800-15,y,4,19));
        }

        //Timer setting
         flashRedTimer = new Timer(flashColorTime, new ActionListener() { // flashes the color of the border after red player scored
            public void actionPerformed(ActionEvent evt) {
                if(redFlashCounter[0] % 2 == 0)
                    borderColor = Color.red;
                else
                    borderColor = (boardColor == Color.black) ? Color.white : Color.black;
                repaint();
                redFlashCounter[0]++;
            }
        });

        flashBlueTimer = new Timer(flashColorTime, new ActionListener() { // flashes the color of the border after blue player scored
            public void actionPerformed(ActionEvent evt) {
                if(blueFlashCounter[0] % 2 == 0)
                    borderColor = Color.blue;
                else
                    borderColor = (boardColor == Color.black) ? Color.white : Color.black;
                repaint();
                blueFlashCounter[0]++;
            }
        });

        flashWinTimer = new Timer(flashWinTime, new ActionListener() { //enables the win animation/effect after someone won (scored 5 points)
            public void actionPerformed(ActionEvent evt) {
                if(winFlashCounter[0] % 2 == 0)
                    playerScoredPanel.setVisible(true);
                else
                    playerScoredPanel.setVisible(false);
                repaint();
                winFlashCounter[0]++;
            }
        });

        scoreAnimationTimer = new Timer(scoreTime, new ActionListener() { // reverts the changes from the score animation and starts the next round
            public void actionPerformed(ActionEvent evt) {
                flashBlueTimer.stop();
                flashRedTimer.stop();
                resetBoard();
                playerScoredPanel.setVisible(false);
                mainTimer.restart();
            }
        });
        scoreAnimationTimer.setRepeats(false); // when it activated once, we don't need for it to activate agaim

        winTimer = new Timer(winTime, new ActionListener() { // enables game Over screen and ends the game
           public void actionPerformed(ActionEvent evt)
           {
                flashWinTimer.stop();
                flashBlueTimer.stop();
                flashRedTimer.stop();
                playerScoredPanel.setVisible(false);
                frame.setContentPane(new GameOverScreen(frame,GameLogicPanel.this));
                frame.setSize(new Dimension(800,800));
                frame.revalidate();
                frame.repaint();
           } 
        });
        winTimer.setRepeats(false);
        mainTimer = new Timer(moveTime, new ActionListener() { // the main timer of the game
            public void actionPerformed(ActionEvent evt) {
                movePlayers(redPlayerHandle);
                movePlayers(bluePlayerHandle);
                moveBall(redPlayerHandle,ball);
                moveBall(bluePlayerHandle,ball);
                checkWinningPlayer();
                collidesWithBorders();
                repaint(); 
            }
        });
        mainTimer.start();
    }

    public void resetBoard() //resets colors and positions
    {
        borderColor = (boardColor == Color.black) ? Color.white : Color.black;
        ball = new Ball(ballColor,377,406,30,30);
        redPlayerHandle = new PlayerHandle(Color.red,360,110,75,75,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D,70,340,0,701);
        bluePlayerHandle = new PlayerHandle(Color.blue,360,660,75,75, KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,420,700,0,701);
    }

    public boolean isIntersecting(PlayerHandle playerhandle, Ball ball) // method of finding if the ball collided with players
    {
        double x1 = playerhandle.getCentreX();
        double x2 = ball.getCentreX();
        double y1 = playerhandle.getCentreY();
        double y2 = ball.getCentreY();
        double r1 = playerhandle.getRadius();
        double r2 = ball.getRadius();
        return (Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) <= r1+r2);
    }

    public void collidesWithBorders() // method for finding ball collisions with the borders
    {
        
        Rectangle ballRectangle = new Rectangle(ball.getX(),ball.getY(),30,30);
        for(Rectangle border: upDownBorder)
        {
            if (border.intersects(ballRectangle))
            {
                ball.getVelocity().setY(ball.getVelocity().getY()*(-1));   // direction of the ball gets reflected on the wall
                ball.setX((int) ball.getVelocity().getX());  //relocation of the ball
                ball.setY((int) ball.getVelocity().getY());  //relocation of the ball
                ball.setCentreX(); //updating centre point of the ball
                ball.setCentreY();
                break;
            }
        }

        for(Rectangle border: leftRightBorder)
        {
            if (border.intersects(ballRectangle))
            {
                ball.getVelocity().setX(ball.getVelocity().getX()*(-1));  // direction of the ball gets reflected on the wall  
                ball.setX((int) ball.getVelocity().getX());   //relocation of the ball
                ball.setY((int) ball.getVelocity().getY());    //relocation of the ball
                ball.setCentreX(); //updating centre point of the ball
                ball.setCentreY();
                break;
            }
        }
    }

    public void checkWinningPlayer() //decides which player won and activates the animation
    {
        
        if( ball.getY() <= 40) // if ball gets out of the play field a goal happended (top border (red) blue scored)
        {
            blueScore++;
            if(blueScore == 5)
            {
                mainTimer.stop();
                playerScoredLabel.setText("BLUE TEAM WON!");
                blueTeamScore.setText("BLUE TEAM SCORE: "+ blueScore);
                playerScoredLabel.setForeground(Color.yellow);
                playerScoredPanel.setVisible(true);
                flashWinTimer.start();
                flashBlueTimer.restart();
                winTimer.start();
            }
            else
            {
                blueTeamScore.setText("BLUE TEAM SCORE: "+ blueScore);
                mainTimer.stop();
                scoreAnimationTimer.restart();
                flashBlueTimer.restart();
                playerScoredLabel.setText("BLUE TEAM SCORED!");
                playerScoredLabel.setForeground(Color.blue);
                playerScoredPanel.setVisible(true);
            }
        }
        else if(ball.getY() >= 770)  // if ball gets out of the play field a goal happended (down border (blue) red scored)
        {
            redScore++;
            if(redScore == 5)
            {
                mainTimer.stop();
                playerScoredLabel.setText("RED TEAM WON!");
                redTeamScore.setText("RED TEAM SCORE: "+ redScore);
                playerScoredLabel.setForeground(Color.yellow);
                playerScoredPanel.setVisible(true);
                flashWinTimer.start();
                flashRedTimer.restart();
                winTimer.start();
            }
            else
            {
                redTeamScore.setText("RED TEAM SCORE: "+ redScore);
                mainTimer.stop();
                scoreAnimationTimer.restart();
                flashRedTimer.restart();
                playerScoredLabel.setText("RED TEAM SCORED!");
                playerScoredLabel.setForeground(Color.red);
                playerScoredPanel.setVisible(true);
            } 
        }
    }
    public void moveBall(PlayerHandle playerHandle, Ball ball) // ball moving mechanism
    {
        ball.setCentreX(); //updating ball centre
        ball.setCentreY();

        if(isIntersecting(playerHandle, ball)) // if ball and player collide
        {
            Vector2D impactVector = new Vector2D(ball.getCentreX()-playerHandle.getCentreX(),ball.getCentreY()-playerHandle.getCentreY()); // we find the impact vector( the direction which the ball will follow)
            Vector2D ballDirection = impactVector.normalize(); // we normalize it so it has length = 1
            ball.setVelocity(ballDirection.multiply(16)); // we multiply it by 16 to achieve proper speed
            ball.setX((int) ball.getVelocity().getX()); // we relocate it
            ball.setY((int) ball.getVelocity().getY()); // we relocate it
            ball.setCentreX(); //updating the centre
            ball.setCentreY();
            ball.resetSpeedLoss(); // setting speedLoss = 1 because the player pushed the ball
        }                           
        else
        {
            if (ball.getVelocity() != null && ball.getVelocity().length() > 0.1) { // if the ball is actually moving
                ball.lowerSpeedLoss(0.0001); // speedLoss -= 0.0001, works like friction decreases the speed of the ball gradually
                ball.setVelocity(ball.getVelocity().multiply(ball.getSpeedLoss())); // changing the velocity of the ball via multiplying its velocity with the speedLoss
                ball.setX((int) ball.getVelocity().getX()); //relocation
                ball.setY((int) ball.getVelocity().getY()); //relocation
                ball.setCentreX(); //updating centre
                ball.setCentreY();     
            }
        }
    }

    public void keyReleased(KeyEvent e) { // if any player releases any key its corresponding relocation metric will be set to 0;
        keys[e.getKeyCode()] = false; // if someone releaseas any key, we write it as false on the keys table to know if that key is pressed or not
        if(!keys[KeyEvent.VK_LEFT] && !keys[KeyEvent.VK_RIGHT] )
            bluePlayerHandle.setDx(0);
        if(!keys[KeyEvent.VK_UP] && !keys[KeyEvent.VK_DOWN])
            bluePlayerHandle.setDy(0);
        if(!keys[KeyEvent.VK_A] && !keys[KeyEvent.VK_D] )
           redPlayerHandle.setDx(0);
        if(!keys[KeyEvent.VK_W] && !keys[KeyEvent.VK_S])
            redPlayerHandle.setDy(0);
    }
    public void keyTyped(KeyEvent e) { }
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true; // key is now pressed, we write it at the table as true
    }
    
    public void movePlayers(PlayerHandle playerHandle) // player movement( checks also if player collides with border we do not let the move further)
    {
        if(playerHandle.getY() >= playerHandle.getDownLimit())
        {
            playerHandle.setDownBorder(true);
            playerHandle.setDy(0);
        }
        else 
        {
            playerHandle.setDownBorder(false);
        }
        if(playerHandle.getY() <= playerHandle.getTopLimit())
        {
            playerHandle.setUpBorder(true);
            playerHandle.setDy(0);
        }
        else
        {
            playerHandle.setUpBorder(false);
        }

        if(playerHandle.getX() <= playerHandle.getLeftLimit())
        {
            playerHandle.setLeftBorder(true);
            playerHandle.setDx(0);
        }
        else
        {
            playerHandle.setLeftBorder(false);
        }

        if(playerHandle.getX() >= playerHandle.getRightLimit())
        {
            playerHandle.setRightBorder(true);
            playerHandle.setDx(0);
        }
        else
        {
            playerHandle.setRightBorder(false);
        }

        if(keys[playerHandle.getLeftKey()] && !playerHandle.getLeftBorderPass())
        {
            playerHandle.setDx(-20);
        }

        if(keys[playerHandle.getRightKey()] && !playerHandle.getRightBorderPass())
        {
            playerHandle.setDx(20);
        }

        if(keys[playerHandle.getUpKey()] && !playerHandle.getUpBorderPass())
        {
            playerHandle.setDy(-20);
        }

        if(keys[playerHandle.getDownKey()] && !playerHandle.getDownBorderPass())
        {
            playerHandle.setDy(20);
        }

        if(keys[playerHandle.getLeftKey()] && keys[playerHandle.getUpKey()] && !playerHandle.getLeftBorderPass() && !playerHandle.getUpBorderPass()) 
        {
            playerHandle.setDx(-20);
            playerHandle.setDy(-20); 
        }

        if(keys[playerHandle.getRightKey()] && keys[playerHandle.getUpKey()] && !playerHandle.getRightBorderPass() && !playerHandle.getUpBorderPass())
        {
            playerHandle.setDx(20); 
            playerHandle.setDy(-20); 
        }

        if(keys[playerHandle.getRightKey()] && keys[playerHandle.getDownKey()]  && !playerHandle.getDownBorderPass() && !playerHandle.getRightBorderPass())
        {
            playerHandle.setDx(20); 
            playerHandle.setDy(20); 
        }

        if(keys[playerHandle.getLeftKey() ] && keys[playerHandle.getDownKey()]  && !playerHandle.getDownBorderPass() && !playerHandle.getLeftBorderPass()) 
        {
            playerHandle.setDx(-20); 
            playerHandle.setDy(20);  
        }
        playerHandle.setX(playerHandle.getDx()); // relocation of the player
        playerHandle.setY(playerHandle.getDy()); 
        playerHandle.setCentreX(); //  updating centre of player
        playerHandle.setCentreY();
    }

    public void paintComponent(Graphics gfx) // painting all the components to the screen
    {
        super.paintComponent(gfx);
        gfx.setColor(borderColor);
        for(Rectangle borderPart: upDownBorder) //borders
        {
            gfx.fillRect(borderPart.x,borderPart.y,borderPart.width,borderPart.height);
        }

        for(Rectangle borderPart: leftRightBorder) //borders
        {
            gfx.fillRect(borderPart.x,borderPart.y,borderPart.width,borderPart.height);
        }

        gfx.setColor(lineColor);
        for(int x = 0; x <= 800-20; x++)
        {
            gfx.fillRect(x,420,12,2); // the line which is on the middle of the field
        }
        Graphics2D gfx2d = (Graphics2D) gfx;
        gfx2d.setStroke(new BasicStroke(3)); // thicness of the drawing
        gfx.drawOval(260,290,260,260); // circle on the middle of the field
        gfx.drawArc(262,-55,265,260,180,180); //arcs on the goal areas
        gfx.drawArc(262,643,265,260,0,180);
        gfx.setColor(Color.red);
        gfx.fillOval( redPlayerHandle.getX(),redPlayerHandle.getY(),redPlayerHandle.getWidth(),redPlayerHandle.getHeight()); // player handles/paddles
        gfx.setColor(new Color(220,50,40));
        gfx.fillOval(redPlayerHandle.getX()+19,redPlayerHandle.getY()+18,40,40); // the circle inside the player circle( purely decorational)
        gfx.setColor(Color.blue);
        gfx.fillOval(bluePlayerHandle.getX(),bluePlayerHandle.getY(),bluePlayerHandle.getWidth(),bluePlayerHandle.getHeight());
        gfx.setColor(new Color(50,40,220));
        gfx.fillOval(bluePlayerHandle.getX()+19,bluePlayerHandle.getY()+18,40,40);
        gfx.setColor(ballColor);
        gfx.fillOval(  ball.getX(), ball.getY(),ball.getWidth(),ball.getHeight()); // the ball

    }  
}