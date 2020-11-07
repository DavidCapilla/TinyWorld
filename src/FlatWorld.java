import java.awt.Point;

/**
 * <h1> Implementation of a Flat World </h1>
 * Extends the World by implementing a finite flat world,
 * having defined boundaries delimiting the movement of the Beings.
 */
public class FlatWorld extends World{

    public FlatWorld(int nRows, int nColumns) {
        super(nRows, nColumns);
    }

    @Override
    protected Point getExpectedPosition(Movement move, Point currentPosition) {
        Point translation = move.getTranslation();
        Point expectedPosition = new Point(currentPosition);
        expectedPosition.translate(translation.x, translation.y);
        if (isLocationInFlatWorld(expectedPosition))
            return expectedPosition;
        else
            return currentPosition;
    }
    
    protected boolean isLocationInFlatWorld(Point location) {
        if(location.x < 0 || location.x >= getNRows() ||
           location.y < 0 || location.y >= getNColumns())
            return false;
        else
            return true;
    }

}
