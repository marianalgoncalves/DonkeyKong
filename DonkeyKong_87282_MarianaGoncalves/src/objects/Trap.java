package objects;

import behaviors.Interactable;
import behaviors.NotPassable;
import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject implements Interactable, NotPassable {

    private Point2D position;
	private int removeLifePoints;

	public Trap(Point2D position) {
		this.position = position;
		this.removeLifePoints = 10;
	}

	@Override
	public String getName() {
		return "Trap";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public Point2D getPosition() {	
		return position;
	}

	@Override
    public void interact(Manel manel){
        manel.removeLifePoints(removeLifePoints);
        
    }
}
