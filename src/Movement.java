import java.awt.Point;

public class Movement {
    private Point translation;
    
    public Movement() {
        translation = new Point();
    }
    
    public Movement(int x, int y) {
        setTranslation(new Point(x, y));
    }
    
    public Point getTranslation() {
        return translation;
    }
    
    public void setTranslation (Point translation) {
        if(translation.x < -1 || translation.x > 1 ||
           translation.y < -1 || translation.y > 1)
            throw new ArrayIndexOutOfBoundsException("relativeMovement only allows unit movement.");
        else
            this.translation = translation;       
    }
}
