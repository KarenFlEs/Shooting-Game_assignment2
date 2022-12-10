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
    //public static final String SPACE_STAR_SHIP = IMAGES_FOLDER + "starship.png";
    public static final String SPACE_STAR_SHIP = IMAGES_FOLDER + "newSpaceships/spaceShips_001.png";
    public static final String SPACE_TANK = IMAGES_FOLDER + "tank.png";
   
    // Rocket images
    public static final String ROCKET_SMALL = IMAGES_FOLDER + "rocket2.png";
    public static final String ROCKET_FIRE = IMAGES_FOLDER + "light-missile.png";

    // Invader sprites.
    
    private static final String NEWINVADERS_IMAGES_FOLDER = RESOURCES_FOLDER + "images/newInvaders/";
    
    public static final String INVADER_UFO_GREEN = NEWINVADERS_IMAGES_FOLDER + "ufo-green.png"; 
    public static final String INVADER_UFO_PINK = NEWINVADERS_IMAGES_FOLDER + "ufo-pink.png"; 
    public static final String INVADER_JELLYMONSTER = NEWINVADERS_IMAGES_FOLDER + "invader-JellyMonster.png"; 
    public static final String INVADER_FLYMONSTER = NEWINVADERS_IMAGES_FOLDER + "invader-flyMonster"; 
    
    /*
    public static final String INVADER_LARGE_SHIP = IMAGES_FOLDER + "large_invader_b.png";
    public static final String INVADER_SMALL_SHIP = IMAGES_FOLDER + "small_invader_b.png";
    public static final String INVADER_UFO = IMAGES_FOLDER + "ufo.png";
    public static final String INVADER_CHICKEN = IMAGES_FOLDER + "rounded-chicken.png";
    public static final String INVADER_BEE = IMAGES_FOLDER + "small-bee.png";
    public static final String INVADER_SCI_FI = IMAGES_FOLDER + "sci-fi.png";
   */
    
    
    // Sound effect files
    public static final String SOUND_LASER = SOUNDS_FOLDER + "laser_2.mp3";    
    //public static final String SOUND_LASER = SOUNDS_FOLDER + "alienMove2.wav";    
    
    public static final String[] INVADER_SPRITES_PATH = {			
			//INVADER_UFO, INVADER_CHICKEN, INVADER_BEE,INVADER_SCI_FI
                        INVADER_UFO_GREEN, INVADER_UFO_PINK, INVADER_JELLYMONSTER,INVADER_FLYMONSTER
	};

//    public static final String ROCKET_SMALL = IMAGES_FOLDER + "rocket.png";
    public static HashMap<Integer, String> getInvaderSprites() {
        HashMap<Integer, String> invaders = new HashMap<Integer, String>();
        
        for (int i = 1; i <= INVADER_SPRITES_PATH.length; i++){
            invaders.put(i, INVADER_SPRITES_PATH [i]);
        }
        System.out.println(invaders);
        
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
            
        /*File() files = invadersFolder.listFiles(filter); 
        
        for (File file : files){
            fileNames.add(file.getName()); 
        }
        */
        
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    
        return fileNames; 
    }

}
