import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public abstract class World {

    private int nRows;
    private int nColumns;
    private Random randomGenerator;
    public int[][] regionPopulation;
    private HashMap<Point, Source> locationsSource;
    private HashMap<Point, Prey> locationsPrey;
    private HashMap<Point, Predator> locationsPredator;
    private Clock clock;

    public World (int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        regionPopulation = new int[this.nRows][this.nColumns];
        locationsSource = new HashMap<Point, Source>();
        locationsPrey = new HashMap<Point, Prey>();
        locationsPredator = new HashMap<Point, Predator>();
        randomGenerator = new Random();
        clock = new Clock();
    }
    
    public void setTimeToGrowSource (double timeInSeconds) {
        clock.setTimeToGrowSource(timeInSeconds);
    }
    
    public int getNRows() {
        return nRows;
    }
    
    public int getNColumns() {
        return nColumns;
    }

    public void createSource(int initialSourceQuantity) {
        Point location;
        for (int k = 0; k < initialSourceQuantity; k++) {
            location = findRandomEmptyLocation();
            regionPopulation[location.x][location.y] = 1;
            locationsSource.put(location, new Source());
        }
        clock.setTimeSourceGeneratedToNow();
    }
    
    public void createPrey(int initialPreyQuantity) {
        Point location;
        for (int k = 0; k < initialPreyQuantity; k++) {
            location = findRandomEmptyLocation();
            regionPopulation[location.x][location.y] = 2;
            locationsPrey.put(location, new Prey());
        }
    }
    
    public void createPredator(int initialPredatorQuantity) {
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
    
    public void update() {
        generateSource();
        // movePrey(); TODO
        // movePredator(); TODO
        
        removeConsumedSource();
        removeDeathPrey();
        removeDeathPredator();
    }
        
    public void generateSource() {
        int sourceToGenerate = 1;
        if(clock.isTimeToGrowSource())
            createSource(sourceToGenerate);
    }
    
    private void removeConsumedSource() {
        Iterator<Entry<Point, Source>> it  = locationsSource.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Source> entry = it.next();
            if (!entry.getValue().isAlive())
                it.remove();
        }
    }
    
    private void removeDeathPrey() {
        Iterator<Entry<Point, Prey>> it  = locationsPrey.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Prey> entry = it.next();
            if (!entry.getValue().isAlive())
                it.remove();
        }
    }
    
    private void removeDeathPredator() {
        Iterator<Entry<Point, Predator>> it  = locationsPredator.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Predator> entry = it.next();
            if (!entry.getValue().isAlive())
                it.remove();
        }
    }
    
    public void movePrey() {
        throw new RuntimeException("movePrey not implemented yet.");
    }
    
    public void movePredator() {
        throw new RuntimeException("movePredator not implemented yet.");
    }
}
