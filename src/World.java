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
        
        consumePopulationEnergy();
        movePrey();
        movePredator();
        
        removeDeathBeings();
    }
        
    private void consumePopulationEnergy() {
        Iterator<Entry<Point, Being>> it  = locationsPopulation.entrySet().iterator();
        Being being;
        while (it.hasNext()) {
            Map.Entry<Point, Being> entry = it.next();
            being = entry.getValue();
            if (isConsumer(being))
                ((Consumer) being).consumeEnergy();
        }   
    }
    
    private boolean isConsumer(Being being) {
        if (isPrey(being) || isPredator(being))
            return true;
        else 
            return false;
    }
    
    private boolean isSource(Being being) {
        if (being.Type.equals("Source"))
            return true;
        else 
            return false;
    }
    
    private boolean isPrey(Being being) {
        if (being.Type.equals("Prey"))
            return true;
        else 
            return false;
    }
    
    private boolean isPredator(Being being) {
        if (being.Type.equals("Predator"))
            return true;
        else 
            return false;
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
        Being being;
        Being otherBeing;
        Movement move;
        Point currentPosition;
        Point expectedPosition;
        HashMap<Point, Being> locationsPopulationClone = new HashMap<Point, Being>(locationsPopulation);
        Iterator<Entry<Point, Being>> it  = locationsPopulationClone.entrySet().iterator();
        Map.Entry<Point, Being> entry;
        while (it.hasNext()) {
            entry = it.next();
            being = entry.getValue();
            if (isPrey(being)) { // XXX
                currentPosition = entry.getKey();
                move = ((Consumer) being).getDecidedMovement();
                if (isAllowedMovementForPrey(move, currentPosition)) // XXX
                {
                    expectedPosition = getExpectedPosition(move, currentPosition);
                    if (isLocationFree(expectedPosition)) {
                        locationsPopulation.remove(currentPosition);
                        locationsPopulation.put(expectedPosition, being);
                    }
                    else {
                        otherBeing = locationsPopulation.get(expectedPosition);
                        if (isSource(otherBeing)) {
                            ((Consumer) being).eat(otherBeing);
                            locationsPopulation.remove(currentPosition);
                            locationsPopulation.put(expectedPosition, being);
                        }
                        else if (isPredator(otherBeing)) {
                            ((Consumer) otherBeing).eat(being);
                            locationsPopulation.remove(currentPosition);
                        }
                    }
                }   
                else {
                    // Do nothing, stay in the same position.
                }
            }
        }
    }
    
    private boolean isAllowedMovementForPrey(Movement move, Point currentPosition) {
        Point expectedPosition = getExpectedPosition(move, currentPosition);
        if (expectedPosition.equals(currentPosition))
            return true;            
        else if (isLocationFree(expectedPosition))
            return true;
        else if (isSource(locationsPopulation.get(expectedPosition)))
            return true;
        else if (isPredator(locationsPopulation.get(expectedPosition)))
            return true; // It is allowed to run into a Predator.
        else
            return false;
    }
    
    public void movePredator() {
        Being being;
        Being otherBeing;
        Movement move;
        Point currentPosition;
        Point expectedPosition;
        HashMap<Point, Being> locationsPopulationClone = new HashMap<Point, Being>(locationsPopulation);
        Iterator<Entry<Point, Being>> it  = locationsPopulationClone.entrySet().iterator();
        Map.Entry<Point, Being> entry;
        while (it.hasNext()) {
            entry = it.next();
            being = entry.getValue();
            if (isPredator(being)) { // XXX
                currentPosition = entry.getKey();
                move = ((Consumer) being).getDecidedMovement();
                if (isAllowedMovementForPredator(move, currentPosition)) // XXX
                {
                    expectedPosition = getExpectedPosition(move, currentPosition);
                    if (isLocationFree(expectedPosition)) {
                        locationsPopulation.remove(currentPosition);
                        locationsPopulation.put(expectedPosition, being);
                    }
                    else {
                        otherBeing = locationsPopulation.get(expectedPosition);
                        if (isPrey(otherBeing)) {
                            ((Consumer) being).eat(otherBeing);
                            locationsPopulation.remove(currentPosition);
                            locationsPopulation.put(expectedPosition, being);
                        }
                    }
                }   
                else {
                    // Do nothing, stay in the same position.
                }
            }
        }
    }
    
    private boolean isAllowedMovementForPredator(Movement move, Point currentPosition) {
        Point expectedPosition = getExpectedPosition(move, currentPosition);
        if (expectedPosition.equals(currentPosition))
            return true;            
        else if (isLocationFree(expectedPosition))
            return true;
        else if (isPrey(locationsPopulation.get(expectedPosition)))
            return true;
        else
            return false;
    }

    protected abstract Point getExpectedPosition(Movement move, Point currentPosition);
}
