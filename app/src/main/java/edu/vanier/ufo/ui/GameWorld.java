package edu.vanier.ufo.ui;
import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.engine.*;
import edu.vanier.ufo.game.*;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.image.ImageView;

/**
 * This is a simple game world simulating a bunch of spheres looking like atomic
 * particles colliding with each other. When the game loop begins the user will
 * notice random spheres (atomic particles) floating and colliding. The user
 * will navigate his/her ship by right clicking the mouse to thrust forward and
 * left click to fire weapon to atoms.
 *
 * @author cdea
 */
public class GameWorld extends GameEngine {

    // mouse pt label
    Label mousePtLabel = new Label();
    // mouse press pt label
    Label mousePressPtLabel = new Label();
    
    Ship spaceShip = new Ship();
    ImageView lifeView01 = new ImageView(new Image("/images/ship_life.gif"));;
    ImageView lifeView02 = new ImageView(new Image("/images/ship_life.gif"));;
    ImageView lifeView03 =  new ImageView(new Image("/images/ship_life.gif"));
    ImageView gameOver =  new ImageView(new Image("/images/game_over.gif"));
    ImageView gameWin =  new ImageView(new Image("/images/win_display.png"));
   // PauseTransition hitAnimation = new PauseTransition(Duration.seconds(1));
      
        

    ImageView levelUp =  new ImageView(new Image("/images/levelUp.gif"));
    
    Label lifeLabel = new Label();
    Label levelLabel = new Label();
    Label scoreLabel = new Label ();
    HBox row4 = new HBox();
    VBox stats = new VBox();
    
    String explosionAudio = ""; 
    String laserAudio = ""; 
    
    //String rocketType = spaceShip.getRocketType(); 
    
    int numLevel = 1;
    int score = 0;
    int deadInvader =0;

    public GameWorld(int fps, String title) {
        super(fps, title);
    }

    /**
     * Initialize the game world by adding sprite objects.
     *
     * @param primaryStage The game window or primary stage.
     */
    @Override
    public void initialize(final Stage primaryStage) {
        // Sets the window title
        primaryStage.setTitle(getWindowTitle());
        //TODO: try the following window size options:
        //primaryStage.setFullScreen(true);
        //primaryStage.setMaximized(true);   
       //timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(levelUp)));
        // Create the scene
       
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 1000, 600) );

        // Change the background of the main scene.
        getGameSurface().setFill(Color.BLACK);
        //getSceneNodes().getChildren().add(imvBackground1);
      
        // Setup Game input
        primaryStage.setScene(getGameSurface());

        // Setup Game input
        setupInput(primaryStage);
        
        // Create many spheres
        // TODO: change this. It must be implemented as a new game level.
        //generateManySpheres(10,0);
        newLevel(1); 
            
        // Display the number of spheres visible.
        // Create a button to add more spheres.
        // Create a button to freeze the game loop.
        //final Timeline gameLoop = getGameLoop();
        getSpriteManager().addSprites(spaceShip);
        
     
        getSceneNodes().getChildren().add(0, spaceShip.getNode());
        // mouse point
        lifeView01.setFitHeight(50);
        lifeView01.setFitWidth(50);
        lifeView02.setFitHeight(50);
        lifeView02.setFitWidth(50);
        lifeView03.setFitHeight(50);
        lifeView03.setFitWidth(50);

        HBox row1 = new HBox();
       
        HBox row2 = new HBox();
        scoreLabel.setText("Score: " + score);
        scoreLabel.setTextFill(Color.ALICEBLUE);
        row2.getChildren().add(scoreLabel);
        
        HBox row3 = new HBox();
        levelLabel.setTextFill(Color.ALICEBLUE);
        levelLabel.setText("Level: " + numLevel);
        
        row3.getChildren().add(levelLabel);
        row1.getChildren().addAll(lifeView01, lifeView02, lifeView03);
        stats.getChildren().addAll(row2,row3,row1);
     
       //TODO: Add the HUD here.
        getSceneNodes().getChildren().add(0, stats);

        // load sound files
        getSoundManager().loadSoundEffects("laser", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER));
        getSoundManager().loadSoundEffects("laser2", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER2));
        getSoundManager().loadSoundEffects("laser3", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER3));
        getSoundManager().loadSoundEffects("explosion", getClass().getClassLoader().getResource(ResourcesManager.SOUND_EXPLOSION));
        getSoundManager().loadSoundEffects("explosion2", getClass().getClassLoader().getResource(ResourcesManager.SOUND_EXPLOSION2));
        getSoundManager().loadSoundEffects("explosion3", getClass().getClassLoader().getResource(ResourcesManager.SOUND_EXPLOSION3));
        
    }

    /**
     * Sets up the mouse input.
     *
     * @param primaryStage The primary stage (app window).
     */
    private void setupInput(Stage primaryStage) {
        
        System.out.println("Ship's center is (" + spaceShip.getCenterX() + ", " + spaceShip.getCenterY() + ")");

        EventHandler fireOrMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            mousePressPtLabel.setText("Mouse Press PT = (" + event.getX() + ", " + event.getY() + ")");
            if (event.getButton() == MouseButton.PRIMARY) {
                // Aim
                spaceShip.plotCourse(event.getX(), event.getY(), false);

                // fire
                Missile missile = spaceShip.fire();
                getSpriteManager().addSprites(missile);

                // play sound
                getSoundManager().playSound(laserAudio);
                
                getSceneNodes().getChildren().add(0, missile.getNode());

            } else if (event.getButton() == MouseButton.SECONDARY) {
                // determine when all atoms are not on the game surface. Ship should be one sprite left.

                // stop ship from moving forward
                spaceShip.applyTheBrakes(event.getX(), event.getY());
                // move forward and rotate ship
                spaceShip.plotCourse(event.getX(), event.getY(), true);
            }
        };

        // Initialize input
        primaryStage.getScene().setOnMousePressed(fireOrMove);

        // set up stats
        EventHandler changeWeapons = (EventHandler<KeyEvent>) (KeyEvent event) -> {
            spaceShip.fire();
            
            if (KeyCode.SPACE == event.getCode()) {
                spaceShip.shieldToggle();
                return;
            }
            
            spaceShip.changeWeapon(event.getCode());
            
            if(null!=event.getCode())
                switch (event.getCode()) {
                case W -> {
                    double positionX = spaceShip.getCenterX();
                    double positionY = (spaceShip.getCenterY()-50);
                    spaceShip.plotCourse(positionX, positionY, true);
                    }
                case S -> {
                    double positionX = spaceShip.getCenterX();
                    double positionY = (spaceShip.getCenterY()+ 50);
                    spaceShip.plotCourse(positionX, positionY, true);
                    }
                case A -> {
                    double positionX = (spaceShip.getCenterX()-50);
                    double positionY = (spaceShip.getCenterY());
                    spaceShip.plotCourse(positionX, positionY, true);
                    }
                case D -> {
                    double positionX = (spaceShip.getCenterX()+50);
                    double positionY = (spaceShip.getCenterY());
                    spaceShip.plotCourse(positionX, positionY, true);
                    }
                default -> {
                }
            }
        };
        
        primaryStage.getScene().setOnKeyPressed(changeWeapons);

        // set up stats
        EventHandler showMouseMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            mousePtLabel.setText("Mouse PT = (" + event.getX() + ", " + event.getY() + ")");
        };

        primaryStage.getScene().setOnMouseMoved(showMouseMove);
    }

    /**
     * Make some more space spheres (Atomic particles)
     *
     * @param numSpheres The number of random sized, color, and velocity atoms
     * to generate.
     */
    private void generateManySpheres(int numSpheres, double x) {
        Random rnd = new Random();
        Scene gameSurface = getGameSurface();
        String strInvaders = " ";
        
        for (int i = 0; i < numSpheres; i++) {
            
            //TODO: genereate different types of invader sprites. 
            int a = rnd.nextInt(5);
            strInvaders = ResourcesManager.getInvaderSprites().get(a);
            
            Atom atom = new Atom(strInvaders);
           // Atom atom = new Atom("images/newInvaders/ufo-pink.png");
            ImageView atomImage = atom.getImageViewNode();
            // random 0 to 2 + (.0 to 1) * random (1 or -1)
            // Randomize the location of each newly generated atom
            
            atom.setVelocityX((((rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1))));
             atom.setVelocityY((((rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1))));
            //atom.setVelocityY((rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1));

            // random x between 0 to width of scene
            double newX = rnd.nextInt((int) gameSurface.getWidth() - 100);
            // TODO: configure the size of the generated images.
            // check for the right of the width newX is greater than width 
            // minus radius times 2(width of sprite)
            if (newX > (gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2))) {
                newX = gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2);
            }
            // check for the bottom of screen the height newY is greater than height 
            // minus radius times 2(height of sprite)
            double newY = rnd.nextInt((int) (gameSurface.getHeight() - 300));
            if (newY > (gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2))) {
                newY = gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2);
            }

            atomImage.setTranslateX(newX);
            atomImage.setTranslateY(newY);
            atomImage.setVisible(true);
            atomImage.setId("invader");
            atomImage.setCache(true);
            atomImage.setCacheHint(CacheHint.SPEED);
            atomImage.setManaged(false);

            // add to actors in play (sprite objects)
            getSpriteManager().addSprites(atom);

            // add sprite's 
            getSceneNodes().getChildren().add(atom.getNode());
        
        }
    }

    /**
     * Each sprite will update it's velocity and bounce off wall borders.
     *
     * @param sprite - An atomic particle (a sphere).
     */
    @Override
    protected void handleUpdate(Sprite sprite) {
        // advance object
        sprite.update();
        if (sprite instanceof Missile) {
            removeMissiles((Missile) sprite);
        } else {
            bounceOffWalls(sprite);
        }
    }

    /**
     * Change the direction of the moving object when it encounters the walls.
     *
     * @param sprite The sprite to update based on the wall boundaries. TODO The
     * ship has got issues.
     */
    private void bounceOffWalls(Sprite sprite) {
        // bounce off the walls when outside of boundaries
        //FIXME: The ship movement has got issues.
        Node displayNode;
        if (sprite instanceof Ship) {
            displayNode = sprite.getNode();//((Ship)sprite).getCurrentShipImage();
        } else {
            displayNode = sprite.getNode();
        }
        // Get the group node's X and Y but use the ImageView to obtain the width.
        if (sprite.getNode().getTranslateX() > (getGameSurface().getWidth() - displayNode.getBoundsInParent().getWidth())
                || displayNode.getTranslateX() < 0) {

            // bounce the opposite direction
            sprite.setVelocityX(sprite.getVelocityX() * -1);
        }
        // Get the group node's X and Y but use the ImageView to obtain the height.
        if (sprite.getNode().getTranslateY() > getGameSurface().getHeight() - displayNode.getBoundsInParent().getHeight()
                || sprite.getNode().getTranslateY() < 0) {
            sprite.setVelocityY(sprite.getVelocityY() * -1);
        }
    }

    /**
     * Remove missiles when they reach the wall boundaries.
     *
     * @param missile The missile to remove based on the wall boundaries.
     */
    private void removeMissiles(Missile missile) {
        // bounce off the walls when outside of boundaries
        if (missile.getNode().getTranslateX() > (getGameSurface().getWidth()
                - missile.getNode().getBoundsInParent().getWidth())
                || missile.getNode().getTranslateX() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());

        }
        if (missile.getNode().getTranslateY() > getGameSurface().getHeight()
                - missile.getNode().getBoundsInParent().getHeight()
                || missile.getNode().getTranslateY() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());
        }
    }
    
    public void updateLvlHud(int value){
        levelLabel.setText("Level: " + value);
    }
    
    public void updateScore(int value){
         scoreLabel.setText("Score: " + score);
    }
    
    
    
    public void newLevel(int level){
        
        if((spaceShip.lifeNumber != 0) ){
            if (level == 1){
                updateLvlHud(1);
                spaceShip.setRocketType(ResourcesManager.ROCKET_SMALL);
               // rocketType = ResourcesManager.ROCKET_SMALL; 
                explosionAudio = "explosion"; 
                laserAudio = "laser";
                generateManySpheres(10,1.5);
            }

            if (level == 2){
           
               // spaceShip.getNode().setTranslateX(700);
             //   spaceShip.getNode().setTranslateY(700);
                updateLvlHud(2);
                spaceShip.setRocketType(ResourcesManager.ROCKET_CROSS);
                //rocketType = ResourcesManager.ROCKET_CROSS; 
                explosionAudio = "explosion2"; 
                laserAudio = "laser2"; 
                generateManySpheres(20,3.0);
                spaceShip.changeShip("/images/newSpaceShips/spaceShip2.png");
            }

            if (level == 3){
              
                updateLvlHud(3);
                spaceShip.setRocketType(ResourcesManager.ROCKET_RED);
                //rocketType = ResourcesManager.ROCKET_RED; 
                explosionAudio = "explosion3"; 
                laserAudio = "laser3"; 
                generateManySpheres(30,3.0);
                spaceShip.changeShip("/images/newSpaceShips/spaceShip3.png");
            }
        }
    }
    
    public void victory(){
           //getSceneNodes().getChildren().clear();
            gameWin.setFitWidth(700);
            gameWin.setTranslateX(400);
            gameWin.setTranslateY(200);
            getSceneNodes().getChildren().add(gameWin);  
    }
    
    public void deathInvadersDetector() {
        if (deadInvader == 10) {
            
            numLevel = 2;
            newLevel(2);
        }
        if (deadInvader == 30) {
           
            numLevel = 3;
            newLevel(3);
        }
        if (deadInvader == 60) {
            victory();
        }
    }
    
     /**
     * How to handle the collision of two sprite objects. Stops the particle by
     * zeroing out the velocity if a collision occurred.
     *
     * @param spriteA Sprite from the first list.
     * @param spriteB Sprite from the second list.
     * @return boolean returns a true if the two sprites have collided otherwise
     * false.
     */
    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        
       if (spriteA != spriteB) {
            if (spriteA.collide(spriteB)) {

                if ((spriteA != spaceShip) && !(spriteB instanceof Atom ) && (spaceShip.shieldOn==false) ) {
                    spriteA.handleDeath(this);
                    spaceShip.lifeNumber--;
                   
                    if (spaceShip.lifeNumber == 2) {
                    lifeView03.imageProperty().set(null);
                    deadInvader++;
                    deathInvadersDetector(); 
                    }
                    if (spaceShip.lifeNumber == 1) {
                    lifeView02.imageProperty().set(null);
                    deadInvader++;
                    deathInvadersDetector(); 
                    }
                    if (spaceShip.lifeNumber == 0) {
                    spriteB.handleDeath(this);
                    lifeView01.imageProperty().set(null);
                    gameOver.setTranslateX(500);
                    gameOver.setTranslateY(200);
                  
                    getSceneNodes().getChildren().clear();
                    getSceneNodes().getChildren().add(gameOver);
                   
                    }
                    
                } 
                
                else if (spriteB != spaceShip && (spriteA instanceof Atom ) ) {
                    if((spaceShip.lifeNumber != 0) ){
                        if (spriteB instanceof Missile ) {
                            deadInvader++;
                            score++;
                            updateScore(score);
                            getSoundManager().playSound(explosionAudio);
                            spriteA.handleDeath(this);
                        }
                        deathInvadersDetector(); 
                    }
               
                }
               // System.out.println(deadInvader);
                return true; 
            }
       }
        return false; 
}
    
    
    
}
        
