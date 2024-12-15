package objects;

import behaviors.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Hammer extends GameObject implements Interactable {

	private Point2D position;
	private int addAttackPoints;

	public Hammer(Point2D position) {
		this.position = position;
		this.addAttackPoints = 20;
	}

	@Override
	public String getName() {
		return "Hammer";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {	
		return position;
	}

	@Override
    public void interact(Manel manel){
        manel.addAttackPoints(addAttackPoints);
    }


}
