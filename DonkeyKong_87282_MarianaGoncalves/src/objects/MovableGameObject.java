package objects;

import behaviors.Movable;
import behaviors.NotPassable;
import java.util.ArrayList;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public abstract class MovableGameObject extends GameObject implements Movable {



    public boolean canMove(Point2D nextPosition){

        GameObject gameObject = collision(nextPosition);

        if (gameObject != null || isOutOfBounds(nextPosition)){
            return false;
        }
        return true;
    
    }

    private boolean isOutOfBounds(Point2D nextPosition){
        int width = GameEngine.getInstance().getCurrentRoom().getWidth();
		int height = GameEngine.getInstance().getCurrentRoom().getHeight();
        

        int x = nextPosition.getX();
        int y = nextPosition.getY();

        if(x<0 || y<0 || x>=width || y>=height){
            return true;
        }

        return false;


    }

    public GameObject collision(Point2D nextPosition){
        GameObject object = null;
        ArrayList<GameObject> gameObjectList = GameEngine.getInstance().getCurrentRoom().getGameObjectList();
        for (GameObject gameObject : gameObjectList) {
            if(gameObject.getPosition().equals(nextPosition)){
                if(gameObject instanceof NotPassable || gameObject instanceof LivingMovableGameObject){
                    object = gameObject;
                    break;
                }
            }
        }
        return object;
    }
}
