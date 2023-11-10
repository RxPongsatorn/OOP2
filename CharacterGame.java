import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;

public class CharacterGame extends JPanel implements KeyListener, ActionListener {
    private int x = 100; // Initial x position
    private int y = 100;
    private int currentFrame = 0; // Current frame of animation
    private int deltaX = 15; // Movement speed x
    private int deltaY = 15; // Movement speed y
    private int numOfFrame = 8;
    private int shotFrame = 0;
    private int numOfShot = 14;
    private ImageIcon[] characterFrames;
    private Rectangle characterHitbox;
    private ImageIcon[] character2Frames;
    private ImageIcon[] characterOrcFrames;
    private ImageIcon[] characterOrcFrames2;
    private ImageIcon[] characterShot;
    private ImageIcon arrow;
    private int xArrow = 175; 
    private int yArrow = 0;
    private Timer shootingarrow;
    private Timer arrowMoving;
    private Timer shootingAct;
    private ImageIcon backgroundImage;
    private ImageIcon backgroundImage2;
    private ImageIcon youwinBackground;
    private boolean isMovingForward = true; // Flag to indicate forward/backward movement
    private boolean lookingForward = false;
    private boolean lookingBackward = false;
    private boolean isShooting = false;
    public boolean youwin = false;
    public boolean newMap = false;
    public boolean OrcDead = false;
    Knight knight = new Knight(100,100,10);
    private Orc_Berserk orcBerserk = new Orc_Berserk(0, 0, 100);
    private Orc_Berserk orcWarrior = new Orc_Berserk(0, 0, 120);

    //inner class
    public class Knight{
        int atk = 10;
        int x;
        int y;
        public Knight(int x,int y,int atk){
            this.x=x;
            this.y=y;
            this.atk=atk;
        }
        int getAtk(){
            return this.atk;
        }
    }

    //inner class
    public class Orc_Berserk{
        int health = 100;
        int x;
        int y;
        public Orc_Berserk(){

        }
        public Orc_Berserk(int x,int y,int health){
            this.x = x;
            this.y = y;
            this.health = health;
        }
        void setHealth(int health){
            this.health = health;
        }
        int getHealth(){
            return this.health;
        }
        void decreaseHealth(int dmgTaken){
            this.health-=dmgTaken;
            if(this.health<0){
                youwin=false;
                OrcDead = true;
            }
        }
    }
    public class Orc_Warrior{
        int health = 120;
        int x;
        int y;
        public Orc_Warrior(){

        }
        public Orc_Warrior(int x,int y,int health){
            this.x = x;
            this.y = y;
            this.health = health;
        }
        void setHealth(int health){
            this.health = health;
        }
        int getHealth(){
            return health;
        }
        void decreaseHealth(int dmgTaken){
            this.health-=dmgTaken;
            if(this.health<=0){
                youwin=true;
            }
        }
    }

    public void drawOrcHealthBar(Graphics g) {
        Orc_Berserk orc_berserk = new Orc_Berserk();
        int healthBarWidth = 100; // Width of the health bar
        int healthBarHeight = 10; // Height of the health bar
        int maxHealth = 100; // Max health value (adjust according to your game)
        int orcHealth = orcBerserk.getHealth();
        // Calculate the current health percentage to determine the width of the health bar
        int currentWidth = (orcHealth * healthBarWidth) / maxHealth;

        // Draw the black background of the health bar
        g.setColor(Color.BLACK);
        g.fillRect(getWidth()-190, getHeight()/2-50, healthBarWidth, healthBarHeight);

        // Draw the red portion representing Orc's current health
        g.setColor(Color.RED);
        g.fillRect(getWidth()-190, getHeight()/2-50, currentWidth, healthBarHeight);
    }

    public void drawOrc_WarriorHealthBar(Graphics g) {
        Orc_Warrior orc_warrior = new Orc_Warrior();
        int healthBarWidth = 100; // Width of the health bar
        int healthBarHeight = 10; // Height of the health bar
        int maxHealth = 100; // Max health value (adjust according to your game)
        int orcHealth = orc_warrior.getHealth();
        // Calculate the current health percentage to determine the width of the health bar
        int currentWidth = (orcHealth * healthBarWidth) / maxHealth;

        // Draw the black background of the health bar
        g.setColor(Color.BLACK);
        g.fillRect(getWidth()-190, getHeight()/2-150, healthBarWidth, healthBarHeight);

        // Draw the red portion representing Orc's current health
        g.setColor(Color.RED);
        g.fillRect(getWidth()-190, getHeight()/2-150, currentWidth, healthBarHeight);
    }



    public CharacterGame() {
        // Load character move forward frames
        characterFrames = new ImageIcon[numOfFrame];
        for (int i = 0; i < numOfFrame; i++) {
            characterFrames[i] = new ImageIcon("Archer/Run/Forward/frame_" + (i) + ".png");
        }
        characterHitbox = new Rectangle(x, y, 40, 70);

        // Load character move backward frames
        character2Frames = new ImageIcon[numOfFrame];
        for (int i = 0; i < numOfFrame; i++) {
            character2Frames[i] = new ImageIcon("Archer/Run/Backward/frame_" + (i) +"_mirrored" +".png");
        }

        // Load characterOrc  frames
        characterOrcFrames = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            characterOrcFrames[i] = new ImageIcon("Orc_Berserk/Idle/IdleBackward/frame_" + (i) +".png");
        }
        // Load characterOrc2  frames
        characterOrcFrames2 = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            characterOrcFrames2[i] = new ImageIcon("Orc_Warrior/Idle/IdleBackward/frame_" + (i) +".png");
        }
        //load character shooting action
        characterShot = new ImageIcon[numOfShot];
        for (int i = 0; i < numOfShot; i++) {
            characterShot[i] = new ImageIcon("Archer/Shot_1/frame_" + (i) +".png");
        }
        //load arrow
        arrow = new ImageIcon("Archer/Arrow" +".png");


        //load background Image
        backgroundImage = new ImageIcon("Background/Background1.png"); // Replace with your image file path
        backgroundImage2 = new ImageIcon("Background/Background2.png"); // Replace with your image file path
        youwinBackground = new ImageIcon("Background/youwin.png"); // Replace with your image file path

        // Add key listener to the panel
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        
        drawCharacter(g);
        
        // drawOrcHealthBar(g);

        
    }

    public void drawCharacter(Graphics g) {
        Image characterImage;
        Image characterOrcImage;
        Image characterOrc2Image;
        Image arrowImage;
        Image BackgroundGames;
        Image YouwinBackground;

            characterOrcImage = characterOrcFrames[1].getImage();
            if (isMovingForward) {
                characterImage = characterFrames[currentFrame].getImage();
            } else if(!isMovingForward){
                characterImage = character2Frames[currentFrame].getImage();
            }else{
                characterImage = characterFrames[currentFrame].getImage();
            }
            if(newMap){
                BackgroundGames = backgroundImage.getImage();
                    //for orc and hitbox
                    characterOrcImage = null;
                    characterOrc2Image = characterOrcFrames2[1].getImage();
            }else{
                characterOrc2Image = null;
                BackgroundGames = backgroundImage2.getImage();
            }
            
            if(isShooting){
                characterImage = characterShot[shotFrame].getImage();
                arrowImage = arrow.getImage();
            }
            else{
                arrowImage = null;
            }
            if(youwin){
                YouwinBackground = youwinBackground.getImage();
            }else{
                YouwinBackground = null;
            }
        g.drawImage(BackgroundGames, 0, 0, getWidth(), getHeight(), this);
            // if(newMap){
            //     drawOrc_WarriorHealthBar(g);
            // }else{
                
            // }
        //for knight and hitbox
        g.drawImage(characterImage, x, y, this);
        // g.setColor(Color.RED);
        // g.drawRect(characterHitbox.x+40, characterHitbox.y+58, characterHitbox.width, characterHitbox.height);
        //for orc and hitbox
        if(!OrcDead){
            g.drawImage(characterOrcImage, getWidth()-200, getHeight()/2-50, this);
            drawOrcHealthBar(g);
        }
        
        // g.drawImage(characterOrc2Image, getWidth()-200, getHeight()/2-150, this);
        // g.setColor(Color.RED);
        // g.drawRect(getWidth()-165, getHeight()/2-15, 45, 60);
        //for arrow and hitbox
        // g.setColor(Color.BLACK);
        // g.drawLine(xArrow, yArrow+94, xArrow+45, yArrow+94);
        g.drawImage(arrowImage, xArrow, yArrow+70, this);
        g.drawImage(YouwinBackground, 0, 0, getWidth(), getHeight(), this);
        
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_D) {
            x += deltaX; // Move character to the right
            characterHitbox.setLocation(x, y);
            lookingForward = true;
            isMovingForward = true;
            currentFrame = (currentFrame + 1) % numOfFrame; // Cycle through frames
            if(x>getWidth()-50){
                newMap=true;
                x=100;
                y=100;
                youwin = true;
            }
        } else if (key == KeyEvent.VK_A) {
            x -= deltaX; // Move character to the left
            characterHitbox.setLocation(x, y);
            lookingBackward = true;
            isMovingForward = false;
            currentFrame = (currentFrame + 1) % numOfFrame; // Cycle through frames
            if(x<0){
                newMap=false;
            }
        } else if (key == KeyEvent.VK_S) {
            y += deltaY;
            characterHitbox.setLocation(x, y);
            currentFrame = (currentFrame + 1) % numOfFrame; // Cycle through frames
        }else if (key == KeyEvent.VK_W) {
            y -= deltaY;
            characterHitbox.setLocation(x, y);
            currentFrame = (currentFrame + 1) % numOfFrame; // Cycle through frames
        }else if (key == KeyEvent.VK_SPACE) {
            isShooting = true;
            yArrow=y;
            xArrow=x+40;
            shootingarrow = new Timer(25, this); // Adjust the delay as needed for your animation speed
            shootingarrow.start();
            arrowMoving = new Timer(150,this);
            // arrowMoving.start();
            // xArrow=175;
            
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        xArrow+=150;
        if (e.getSource() == shootingarrow) {
            shotFrame = (shotFrame + 1) % numOfShot;

            Line2D arrowLine = new Line2D.Double(xArrow, yArrow + 94, xArrow + 45, yArrow + 94);

            // Check hit for Orc_Berserk
            Rectangle orcBerserkHitbox = new Rectangle(getWidth() - 165, getHeight() / 2 - 15, 45, 60);
            if (arrowLine.intersects(orcBerserkHitbox)&&!newMap) {
                orcBerserk.decreaseHealth(10); // Reduce Orc_Berserk's health when hit by the arrow
                repaint(); // Redraw the screen to update the health bar
            }
            
            // Check hit for Orc_Warrior
            Rectangle orcWarriorHitbox = new Rectangle(getWidth() - 165, getHeight() / 2 -15, 45, 60);
            if (arrowLine.intersects(orcWarriorHitbox)&&newMap) {
                orcWarrior.decreaseHealth(10);
                
                repaint(); // Redraw the screen to update the health bar
            }
            

            if(shotFrame==0){
                isShooting = false;
                shootingarrow.stop();
                
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Character Game");
        CharacterGame characterGame = new CharacterGame();
        frame.add(characterGame);
        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
}
