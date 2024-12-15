package objects;

import behaviors.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Interactable {

	private Point2D position; 
	private int addAttackPoints;

	public Sword(Point2D position) {
		this.position = position;
		this.addAttackPoints = 10;
	}

	@Override
	public String getName() {
		return "Sword";
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
    

