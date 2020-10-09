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
    private HashMap<Point, Being> locationsPopulation;
    private double timeToGrowSource;
    private Clock clock;

    public World (int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        locationsPopulation = new HashMap<Point, Being>();
        randomGenerator = new Random();
        clock = new Clock();
    }
    
    public String getPopulationType(Point location) {
        Being being = locationsPopulation.get(location);
        if (being == null)
            return "";
        else
            return being.Type;
    }
    
    public void setTimeToGrowSource (double timeInSeconds) {
        timeToGrowSource = timeInSeconds;
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
            locationsPopulation.put(location, new Source());
        }
        clock.setChronometerOriginToNow();
    }
    
    public void createPrey(int initialPreyQuantity) {
        Point location;
        for (int k = 0; k < initialPreyQuantity; k++) {
            location = findRandomEmptyLocation();
            locationsPopulation.put(location, new Prey());
        }
    }
    
    public void createPredator(int initialPredatorQuantity) {
        Point location;
        for (int k = 0; k < initialPredatorQuantity; k++) {
            location = findRandomEmptyLocation();
            locationsPopulation.put(location, new Predator());
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
                if(isLocationFree(new Point(i,j)))
                    if (++freeIndex == locationIndex) {
                        randomEmptyLocation = new Point(i,j);
                    }
            }
        }
        
        if (randomEmptyLocation == null)
            throw new ArrayIndexOutOfBoundsException("Empty place not found.");
        return randomEmptyLocation;
    }
    
    private boolean isLocationFree(Point location) {
        if (locationsPopulation.get(location) == null)
            return true;
        else
            return false;
    }

    public int getTotalPopulation() {
        return locationsPopulation.size();
    }

    public int getSourcePopulation() {
        return getPopulation("Source");
    }
    
    public int getPreyPopulation() {
        return getPopulation("Prey");
    }

    public int getPredatorPopulation() {        
        return getPopulation("Predator");
    }    
    
    public int getPopulation(String type) {
        int PopulationQuantity = 0;
        for (Being being : locationsPopulation.values())
            if (being.Type.equals(type))
                PopulationQuantity++; 
        return PopulationQuantity;
    }
    
    public void update() {
        generateSource();
        // movePrey(); TODO
        // movePredator(); TODO
        
        removeDeathBeings();
    }
        
    public void generateSource() {
        int sourceToGenerate = 1;
        if(isTimeToGrowSource())
            createSource(sourceToGenerate);
    }
    
    private boolean isTimeToGrowSource() {
        return clock.hasPassedMoreTimeThanThatFromOrigin(timeToGrowSource);
    }

    private void removeDeathBeings() {
        Iterator<Entry<Point, Being>> it  = locationsPopulation.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Being> entry = it.next();
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
