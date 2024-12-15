package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {

    public Point2D position;

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition( Point2D position ){
        this.position = position;
    }

    @Override
    public int getLayer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
