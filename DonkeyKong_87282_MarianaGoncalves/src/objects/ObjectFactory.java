package objects;

import pt.iscte.poo.utils.Point2D;

public class ObjectFactory {

    public static GameObject createObject(char objectLetter, Point2D position){
        
        GameObject object = null;

        switch (String.valueOf(objectLetter)) {
            case "W":
                object = new Wall(position);	
                break;
            case "S":
                object = new Stairs(position);
                break;
            case "H":
                object = new Hammer(position);
                break;
            case "G":
                object = new DonkeyKong(position);	
                break;
            case "s":
                object = new Sword(position);	
                break;
            case "t":
                object = new Trap(position);	
                break;
            case "P":
                object = new Princess(position);	
                break;
            case "0":
                object = new Door(position);	
                break;
            case "m":
                object = new Meat(position);	
                break;

            default:
                
        }	

        return object;
    }
    
}

