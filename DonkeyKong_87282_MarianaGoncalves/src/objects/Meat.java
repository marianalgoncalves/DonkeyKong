package objects;

import behaviors.Expirable;
import behaviors.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Meat extends GameObject implements Interactable, Expirable{

    private Point2D position;
    private boolean good;
	private int removeLifePoints;
	private int turns;


	public Meat(Point2D position) {
		this.position = position;
        this.good = true;
		this.removeLifePoints = 30;
		this.turns = 0;

	}

	@Override
	public String getName() {
        if(good){
            return "GoodMeat";
        }
		return "BadMeat";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {	
		return position;
	}

	public void setGood(boolean good){
		this.good = false;
	}

	@Override
    public void interact(Manel manel){
		if(good){
			manel.restoreLifePoints();
			return;
		}
       manel.removeLifePoints(removeLifePoints); 
    }

	@Override
	public void expire(){
		turns++;
		if(turns > 10){
			setGood(false);
		}
	}
    
}
