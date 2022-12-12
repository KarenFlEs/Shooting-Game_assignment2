package edu.vanier.ufo.helpers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A resource manager providing useful resource definitions used in this game.
 *
 * @author Sleiman
 */
public class ResourcesManager {

    /**
     * Used to control the speed of the game.
     */
    public static final int FRAMES_PER_SECOND = 85;
    private static final String RESOURCES_FOLDER = "";
    private static final String IMAGES_FOLDER = RESOURCES_FOLDER + "images/";
    private static final String SOUNDS_FOLDER = RESOURCES_FOLDER + "sounds/";
    
    // Ship images.
    public static final String SPACE_SHIP = IMAGES_FOLDER + "spiked ship.png";
    public static final String SPACE_TANK = IMAGES_FOLDER + "tank.png";
    
    public static final String SPACE_STAR_SHIP = IMAGES_FOLDER + "newSpaceships/spaceShips_001.png";
   
    // Rocket images
    private static final String ROCKETS_IMAGES_FOLDER = RESOURCES_FOLDER + "images/newRockets/";
    
    public static final String ROCKET_SMALL = ROCKETS_IMAGES_FOLDER + "rocket2.png";
    public static final String ROCKET_FIRE = ROCKETS_IMAGES_FOLDER + "light-missile.png";
    public static final String ROCKET_CROSS = ROCKETS_IMAGES_FOLDER + "rocketGreen.png";
    public static final String ROCKET_RED = ROCKETS_IMAGES_FOLDER + "rocketRed.png";

    //explosion + others
    private static final String OTHER_IMAGES_FOLDER = RESOURCES_FOLDER + "images/OtherImages/";
    
    public static final String EXPLOSION = OTHER_IMAGES_FOLDER + "explosion2.gif";
    public static final String SHIP_LIFE = OTHER_IMAGES_FOLDER + "ship_life.gif";
    public static final String LEVEL_UP = OTHER_IMAGES_FOLDER + "levelUp.gif";
    public static final String GAME_OVER = OTHER_IMAGES_FOLDER + "game_over.gif";
    public static final String GAME_WIN = OTHER_IMAGES_FOLDER + "win_display.png";
    
    // Invader sprites.
    private static final String NEWINVADERS_IMAGES_FOLDER = RESOURCES_FOLDER + "images/newInvaders/";
    
    public static final String INVADER_UFO_GREEN = NEWINVADERS_IMAGES_FOLDER + "ufo-green.png"; 
    public static final String INVADER_UFO_PINK = NEWINVADERS_IMAGES_FOLDER + "ufo-pink.png"; 
    public static final String INVADER_JELLYMONSTER = NEWINVADERS_IMAGES_FOLDER + "invader-JellyMonster.png"; 
    
    public static final String INVADER_YELLOW_MONSTER = NEWINVADERS_IMAGES_FOLDER + "invader-YellowMonster.png";
    public static final String INVADER_GREEN_MONSTER = NEWINVADERS_IMAGES_FOLDER + "invader-GreenMonster.png";
    
    
    // Sound effect files
    public static final String SOUND_LASER = SOUNDS_FOLDER + "laser1.wav";    
    public static final String SOUND_LASER2 = SOUNDS_FOLDER + "laser2.wav"; 
    public static final String SOUND_LASER3 = SOUNDS_FOLDER + "laser3.mp3"; 
    
    public static final String SOUND_EXPLOSION = SOUNDS_FOLDER + "explosion.wav";
    public static final String SOUND_EXPLOSION2 = SOUNDS_FOLDER + "explosion2.wav";
    public static final String SOUND_EXPLOSION3 = SOUNDS_FOLDER + "explosion3.wav";
    
    public static final String SOUND_ALIEN = SOUNDS_FOLDER + "alienMove.wav";
    public static final String SOUND_LEVELUP = SOUNDS_FOLDER + "Level2.wav";    
    
    public static final String[] INVADER_SPRITES_PATH = {			
			//INVADER_UFO, INVADER_CHICKEN, INVADER_BEE,INVADER_SCI_FI
                        INVADER_UFO_GREEN, INVADER_UFO_PINK, INVADER_JELLYMONSTER,INVADER_YELLOW_MONSTER, INVADER_GREEN_MONSTER
	};

//    public static final String ROCKET_SMALL = IMAGES_FOLDER + "rocket.png";
    public static HashMap<Integer, String> getInvaderSprites() {
        HashMap<Integer, String> invaders = new HashMap<Integer, String>();
        
      //  List <String> fileNames = new ArrayList<> ();
        
        for (int i = 0; i < INVADER_SPRITES_PATH.length; i++){
            invaders.put(i, INVADER_SPRITES_PATH [i]);
        }
        
       // invaders.put(1, ResourcesManager.NEWINVADERS_IMAGES_FOLDER + "large_invader_b.png");
       // invaders.put(2, ResourcesManager.NEWINVADERS_IMAGES_FOLDER + "small_invader_b.png");
        return invaders;
    }
    
    /**
     * 
     * @param folderPath
     * @param fileExtension
     * @return 
     */
    public static List<String> getPathsOfSprites (String folderPath, String fileExtension){
        List <String> fileNames = new ArrayList<> ();
       
        try{
            File invadersFolder = new File (folderPath);
            
            FilenameFilter filter = new FilenameFilter(){
                @Override
                public boolean accept (File f, String name){
                    return name.endsWith(fileExtension); 
                }
            };
            
            File[] files = invadersFolder.listFiles(filter); 
            
            for (File file : files){
                fileNames.add(file.getName()); 
            }
        
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    
        System.out.println(fileNames);
        
        return fileNames; 
    }

}
