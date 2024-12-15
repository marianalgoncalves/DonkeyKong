package objects;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameObject {
    public Door(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "DoorClosed";
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
