package objects;

import java.util.ArrayList;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public final class DonkeyKong extends LivingMovableGameObject{

	public int LIFE_POINTS = 90;

	public DonkeyKong(Point2D position) {
		this.position = position;
		super.lifePoints = getStartingLifePoints();
		super.attackPoints = getStartingAttackPoints();
	}

	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public int getStartingLifePoints(){
		return 150;
	}

	@Override
	public int getStartingAttackPoints(){
		return 20;
	}

	@Override
	public void move(){
		Direction direction = Direction.random();
		Point2D nextPosition = position.plus(direction.asVector());
		
		if( (direction == Direction.LEFT || direction == Direction.RIGHT) ){
			if(canMove(nextPosition)){
				position = nextPosition;
				return;
			}
			
			tryToAttack(nextPosition);
			
		}
		ArrayList<GameObject> addToGameObjectList = GameEngine.getInstance().getCurrentRoom().getAddToGameObjectList();
		addToGameObjectList.add(new Banana(position));
		

	}
    
}
