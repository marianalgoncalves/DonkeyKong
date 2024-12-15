package objects;

import behaviors.Climbable;
import behaviors.Interactable;
import behaviors.NotPassable;
import behaviors.Player;
import java.util.ArrayList;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public final class Manel extends LivingMovableGameObject implements Player {

	private int jumpCount;
	private boolean isFalling;

	
	
	public Manel(Point2D initialPosition){
		this.position = initialPosition;
		this.jumpCount = 0;
		this.isFalling = false;
		super.lifePoints = getStartingLifePoints();
		super.attackPoints = getStartingAttackPoints();

	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getStartingLifePoints(){
		return 1000;
	}

	@Override
	public int getStartingAttackPoints(){
		return 20;
	}

	public int getJumpCount(){
		return jumpCount;
	}

	public void setJumpCount(int jumpCount){
		this.jumpCount = jumpCount;
	}

	public void loadJump(){
		this.jumpCount++;
	}

	@Override
	public void move() { 
		System.err.println("Move Manel precisa de Argumentos");

	}
	

	public void move(Direction direction) {

		ArrayList<GameObject> gameObjectList = GameEngine.getInstance().getCurrentRoom().getGameObjectList();
		Point2D nextPosition = position.plus(direction.asVector());
		Point2D nextDownPosition = nextPosition.plus(Direction.DOWN.asVector());

		boolean isClimbable = false;

		boolean willFall = true;

		for (GameObject gameObject : gameObjectList) {
			if(gameObject.getPosition().equals(nextPosition)){ // NEXT POSITION

				if(gameObject instanceof Climbable){
					isClimbable = true;
				}
				if(gameObject instanceof Interactable && !(gameObject instanceof NotPassable)){
					interact(gameObject);
				}

				if(gameObject instanceof LivingMovableGameObject){
					attack(gameObject);
				}

				if(gameObject instanceof Princess){
					GameEngine.getInstance().winGame();
				}
			}
			if(gameObject.getPosition().equals(position)){ // CURRENT POSITION
				
				if(gameObject instanceof Climbable){
					isClimbable = true;
				}
			}
			if(gameObject.getPosition().equals(nextDownPosition)){
	
					if((gameObject instanceof Interactable) && (gameObject instanceof NotPassable)){
						interact(gameObject);
					}
					if((gameObject instanceof NotPassable) || (gameObject instanceof Climbable)){
						willFall = false;
					}
					

			}

		}
		if((direction == Direction.UP || direction == Direction.DOWN) && !isClimbable){
			return;
		}


		if(canMove(nextPosition)){
			position = nextPosition;
			isFalling = willFall;
		}
	
	
	}

	public void jump(Direction direction){
		if(direction == Direction.LEFT || direction == Direction.RIGHT ){
			for(int i=0; i<jumpCount+1; i++){  //direction
				move(direction); 
	
			}
		}
		else{
			move(direction);
		}
		jumpCount = 0;
	}

	public boolean isFalling(){
		return isFalling;
	}

	public void fall(){
		Point2D downPosition = position.plus(Direction.DOWN.asVector());
		Point2D twoDownPosition = downPosition.plus(Direction.DOWN.asVector());
		
		ArrayList<GameObject> gameObjectList = GameEngine.getInstance().getCurrentRoom().getGameObjectList();
		for (GameObject gameObject : gameObjectList) {
			if(((gameObject instanceof NotPassable) || (gameObject instanceof Climbable)) && gameObject.getPosition().equals(twoDownPosition)){
				isFalling = false;
			}
		}

		position = downPosition;

	}
	
	private void interact(GameObject gameObject){
		ArrayList<GameObject> removeToGameObjectList = GameEngine.getInstance().getCurrentRoom().getRemoveToGameObjectList();
		if(!(gameObject instanceof NotPassable)){
		removeToGameObjectList.add(gameObject);
		}

		((Interactable)gameObject).interact(this);
	}
	
	@Override
	public void die(){
		GameEngine.getInstance().startOver();
	}


}
