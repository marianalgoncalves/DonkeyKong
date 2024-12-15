package objects;


import behaviors.NotPassable;
import pt.iscte.poo.utils.Point2D;

public class Wall extends GameObject implements NotPassable {

	private Point2D position;

	public Wall(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	
}
