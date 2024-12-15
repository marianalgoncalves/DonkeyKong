package objects;

import behaviors.Interactable;
import java.util.ArrayList;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends MovableGameObject implements Interactable {

    private int removeLifePoints;
    
    public Banana(Point2D position) {
        this.position = position;
        this.removeLifePoints = 10;
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public Point2D getPosition() {	
        return position;
    }

    @Override
    public void move(){
        Direction direction = Direction.DOWN;
        Point2D nextPosition = position.plus(direction.asVector());
		Manel manel = GameEngine.getInstance().getManel();

        position = nextPosition;

        if(manel.getPosition().equals(position)){ // NEXT POSITION
			interact(manel);
				
		}
    }

    @Override
    public void interact(Manel manel){
        ArrayList<GameObject> removeToGameObjectList = GameEngine.getInstance().getCurrentRoom().getRemoveToGameObjectList();
        removeToGameObjectList.add(this);

        manel.removeLifePoints(removeLifePoints);
        
    } 

}

