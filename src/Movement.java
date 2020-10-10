import java.awt.Point;

public class Movement {
    private Point relativeMovement;
    
    public Movement() {
        relativeMovement = new Point();
    }
    
    public Movement(int x, int y) {
        setRelativeMovement(new Point(x, y));
    }
    
    public Point getRelativeMovement() {
        return relativeMovement;
    }
    
    public void setRelativeMovement (Point relativeMovement) {
        if(relativeMovement.x < -1 || relativeMovement.x > 1 ||
           relativeMovement.y < -1 || relativeMovement.y > 1)
            throw new ArrayIndexOutOfBoundsException("relativeMovement only allows unit movement.");
        else
            this.relativeMovement = relativeMovement;       
    }
}
