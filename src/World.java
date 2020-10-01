import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public abstract class World {

    private int nRows;
    private int nColumns;
    private Random randomGenerator;
    public int[][] regionPopulation;
    private HashMap<Point, Source> locationsSource;
    private HashMap<Point, Prey> locationsPrey;
    private HashMap<Point, Predator> locationsPredator;
    


    public World (int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        regionPopulation = new int[this.nRows][this.nColumns];
        locationsSource = new HashMap<Point, Source>();
        locationsPrey = new HashMap<Point, Prey>();
        locationsPredator = new HashMap<Point, Predator>();
        randomGenerator = new Random();
    }
    
    public int getNRows() {
        return nRows;
    }
    
    public int getNColumns() {
        return nColumns;
    }

    public void createInitialSource(int initialSourceQuantity) {
        Point location;
        for (int k = 0; k < initialSourceQuantity; k++) {
            location = findRandomEmptyLocation();
            regionPopulation[location.x][location.y] = 1;
            locationsSource.put(location, new Source());
        }
    }
    
    public void createInitialPrey(int initialPreyQuantity) {
        Point location;
        for (int k = 0; k < initialPreyQuantity; k++) {
            location = findRandomEmptyLocation();
            regionPopulation[location.x][location.y] = 2;
            locationsPrey.put(location, new Prey());
        }
    }
    
    public void createInitialPredator(int initialPredatorQuantity) {
        Point location;
        for (int k = 0; k < initialPredatorQuantity; k++) {
            location = findRandomEmptyLocation();
            regionPopulation[location.x][location.y] = 3;
            locationsPredator.put(location, new Predator());
        }
    }
    
    public Point findRandomEmptyLocation() {
        if (nRows * nColumns - getTotalPopulation() <= 0)
            throw new ArrayIndexOutOfBoundsException("Current population is at maximum capacity.");
        
        int freeIndex = -1;
        int locationIndex = randomGenerator.nextInt(nRows * nColumns - getTotalPopulation());
        Point randomEmptyLocation = null;

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                if(regionPopulation[i][j] == 0)
                    if (++freeIndex == locationIndex) {
                        randomEmptyLocation = new Point(i,j);
                    }
            }
        }
        
        if (randomEmptyLocation == null)
            throw new ArrayIndexOutOfBoundsException("Empty place not found.");
        return randomEmptyLocation;
    }
    
    public int getTotalPopulation() {
        return getSourcePopulation() + getPreyPopulation() + getPredatorPopulation();
    }

    public int getSourcePopulation() {
        return locationsSource.size();
    }
    
    public int getPreyPopulation() {
        return locationsPrey.size();
    }

    public int getPredatorPopulation() {
        return locationsPredator.size();
    }    
}
