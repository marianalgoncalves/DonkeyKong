package objects;

import behaviors.Climbable;
import pt.iscte.poo.utils.Point2D;

public class Stairs extends GameObject implements Climbable {
    
    private Point2D position;

	public Stairs(Point2D position) {
		this.position = position;
	}

    @Override
	public String getName() {
		return "Stairs";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {	
		return position;
	}


    
}
