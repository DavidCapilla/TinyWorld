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
            locationsPopulation.put(location, new Source());
        }
        clock.setTimeSourceGeneratedToNow();
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
        int sourcePopulationQuantity = 0;
        for (Being being : locationsPopulation.values())
            if (being.Type.equals("Source"))
                sourcePopulationQuantity++;
        
        return sourcePopulationQuantity;
    }
    
    public int getPreyPopulation() {
        int preyPopulationQuantity = 0;
        for (Being being : locationsPopulation.values())
            if (being.Type.equals("Prey"))
                preyPopulationQuantity++;
        
        return preyPopulationQuantity;
    }

    public int getPredatorPopulation() {
        int predatorPopulationQuantity = 0;
        for (Being being : locationsPopulation.values())
            if (being.Type.equals("Predator"))
                predatorPopulationQuantity++;
        
        return predatorPopulationQuantity;
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
      /*  Iterator<Entry<Point, Source>> it  = locationsSource.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Source> entry = it.next();
            if (!entry.getValue().isAlive())
                it.remove();
        }*/
    }
    
    private void removeDeathPrey() {
        /*Iterator<Entry<Point, Prey>> it  = locationsPrey.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Prey> entry = it.next();
            if (!entry.getValue().isAlive())
                it.remove();
        }*/
    }
    
    private void removeDeathPredator() {
        /*Iterator<Entry<Point, Predator>> it  = locationsPredator.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Predator> entry = it.next();
            if (!entry.getValue().isAlive())
                it.remove();
        }*/
    }
    
    public void movePrey() {
        throw new RuntimeException("movePrey not implemented yet.");
    }
    
    public void movePredator() {
        throw new RuntimeException("movePredator not implemented yet.");
    }
}
