package objects;

import java.util.ArrayList;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public abstract class LivingMovableGameObject extends MovableGameObject {

    public int lifePoints;
    public int attackPoints;


    public int getAttackPoints(){
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints){
        this.attackPoints = attackPoints;
    }

    public int getLifePoints(){
        return lifePoints;
    }

    public void setLifePoints(int lifePoints){
        this.lifePoints = lifePoints;
    }

    public abstract int getStartingLifePoints();
    
    public abstract int getStartingAttackPoints();


    public void removeLifePoints(int removeLifePoints){
		
		this.setLifePoints(lifePoints - removeLifePoints);
        ImageGUI.getInstance().setStatusMessage(getName() +" Has Lost " + removeLifePoints + " Life Points!" + " Current Life Points: " + lifePoints + "/" + getStartingLifePoints());
		if(lifePoints <= 0){
			die();
            ImageGUI.getInstance().setStatusMessage(getName() +" Was Killed! ");
		}
	}
    public void die(){
        ArrayList<GameObject> removeToGameObjectList = GameEngine.getInstance().getCurrentRoom().getRemoveToGameObjectList();
        removeToGameObjectList.add(this);
    }

    public void tryToAttack(Point2D nextPosition){
        GameObject gameObject = collision(nextPosition);
        if(gameObject instanceof Manel){
            attack(gameObject);
        }
    }

    public void attack(GameObject gameObject){
        if(gameObject instanceof LivingMovableGameObject){
            ((LivingMovableGameObject)gameObject).removeLifePoints(lifePoints);
        }
    }
    public void addAttackPoints(int addAttackPoints){
		setAttackPoints(attackPoints + addAttackPoints);
		ImageGUI.getInstance().setStatusMessage(" Added " + addAttackPoints +" Attack Points To " + getName() + "! Attack Points: " + attackPoints);

	}

	public void restoreLifePoints(){
		setLifePoints(getStartingLifePoints());
		ImageGUI.getInstance().setStatusMessage(getName() + " Life Points Were Fully Restored ! Life Points: " + lifePoints + "/" + getStartingLifePoints());
	}
    
}
