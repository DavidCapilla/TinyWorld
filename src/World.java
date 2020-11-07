import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * <h1> Abstract World </h1>
 * It locates all the Beings and contains what rules them.
 */
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
        
        movePreyPopulation();
        movePredatorPopulation();
        
        reproducePopulation();
        
        consumePopulationEnergy();
        removeDeathBeings();
    }
        
    private void reproducePopulation() {
        Being being;
        HashMap<Point, Being> locationsPopulationClone = new HashMap<Point, Being>(locationsPopulation);
        Iterator<Entry<Point, Being>> it  = locationsPopulationClone.entrySet().iterator();
        Map.Entry<Point, Being> entry;
        
        while (it.hasNext()) {
            entry = it.next();
            being = entry.getValue();
            if (being.isReadyToReproduce()) {
                reproduceBeingLocatedHere(being, entry.getKey());
            }
        }
    }
    
    private void reproduceBeingLocatedHere(Being being, Point location) {
        Point newbornLocation;
        try {
            newbornLocation = getRandomEmptyLocationCloseToThisLocation(location);
            if(isPrey(being))
                locationsPopulation.put(newbornLocation, new Prey());
            else if (isPredator(being))
                locationsPopulation.put(newbornLocation, new Predator());
        } catch (ArrayIndexOutOfBoundsException e) {
            // Do nothing, not reproduction is performed
        }
        // Either if it has been possible to reproduce or not, readyToReporduce is set to false.
        being.readyToReporduce = false;
    }

    private Point getRandomEmptyLocationCloseToThisLocation(Point position) {
        List<Point> emptyLocationsCloseTo; 

        emptyLocationsCloseTo = getEmptyLocationsListCloseToThisLocation(position);
        
        if (emptyLocationsCloseTo.size() > 0)
            return emptyLocationsCloseTo.get(randomGenerator.nextInt(emptyLocationsCloseTo.size()));
        else 
            throw new ArrayIndexOutOfBoundsException("Can't get an empty location close this, all near positions are busy.");
    }

    private List<Point> getEmptyLocationsListCloseToThisLocation(Point position) {
        Movement move;
        Point expectedPosition;
        List<Point> emptyLocationsCloseTo = new LinkedList<Point>(); 
        
        // Checks all positions at 1 of distance.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dx <= 1; dx++) {
                move = new Movement(dx, dy);
                expectedPosition = getExpectedPosition(move, position); 
                if (isLocationFree(expectedPosition))
                    emptyLocationsCloseTo.add(expectedPosition);
            }
        }
        return emptyLocationsCloseTo;
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
    
    public void movePreyPopulation() {
        Being being;
        HashMap<Point, Being> locationsPopulationClone = new HashMap<Point, Being>(locationsPopulation);
        Iterator<Entry<Point, Being>> it  = locationsPopulationClone.entrySet().iterator();
        Map.Entry<Point, Being> entry;
        
        while (it.hasNext()) {
            entry = it.next();
            being = entry.getValue();
            if (isPrey(being)) {
                moveThisPreyFromThisPosition((Prey)being, entry.getKey());
            }
        }
    }
    
    private void moveThisPreyFromThisPosition(Prey prey, Point preyPosition) {
        Movement move;
        Being otherBeing;
        Point expectedPosition;
        
        move = prey.getDecidedMovement();
        if (isAllowedMovementForThisConsumer(prey, move, preyPosition))
        {
            expectedPosition = getExpectedPosition(move, preyPosition);
            if (isLocationFree(expectedPosition)) {
                locationsPopulation.remove(preyPosition);
                locationsPopulation.put(expectedPosition, prey);
            }
            else {
                otherBeing = locationsPopulation.get(expectedPosition);
                if (isSource(otherBeing)) {
                    prey.eat(otherBeing);
                    locationsPopulation.remove(preyPosition);
                    locationsPopulation.put(expectedPosition, prey);
                }
                else if (isPredator(otherBeing)) {
                    ((Consumer) otherBeing).eat(prey);
                    locationsPopulation.remove(preyPosition);
                }
            }
        }   
        else {
            // Do nothing, stay in the same position.
        }
    }
    
    private boolean isAllowedMovementForThisConsumer (Consumer consumer, Movement move, Point currentPosition) {
        Point expectedPosition = getExpectedPosition(move, currentPosition);
        Being otherBeing;
        if (expectedPosition.equals(currentPosition))
            return true;            
        else if (isLocationFree(expectedPosition))
            return true;
        else {
            otherBeing = locationsPopulation.get(expectedPosition);
            if (consumer.isFood(otherBeing))
                return true;
            else if (isConsumer(otherBeing) && ((Consumer)otherBeing).isFood(consumer))
                return true; // It allows a Prey to run into a Predator.
            else
                return false;  
        }
    }
    
    public void movePredatorPopulation() {
        Being being;
        HashMap<Point, Being> locationsPopulationClone = new HashMap<Point, Being>(locationsPopulation);
        Iterator<Entry<Point, Being>> it  = locationsPopulationClone.entrySet().iterator();
        Map.Entry<Point, Being> entry;
        
        while (it.hasNext()) {
            entry = it.next();
            being = entry.getValue();
            if (isPredator(being)) {
                moveThisPredatorFromThisPosition((Predator)being, entry.getKey());
            }
        }
    }
    
    private void moveThisPredatorFromThisPosition(Predator predator, Point predatorPosition) {
        Movement move;
        Being otherBeing;
        Point expectedPosition;
        
        move = predator.getDecidedMovement();
        if (isAllowedMovementForThisConsumer(predator, move, predatorPosition))
        {
            expectedPosition = getExpectedPosition(move, predatorPosition);
            if (isLocationFree(expectedPosition)) {
                locationsPopulation.remove(predatorPosition);
                locationsPopulation.put(expectedPosition, predator);
            }
            else {
                otherBeing = locationsPopulation.get(expectedPosition);
                if (isPrey(otherBeing)) {
                    predator.eat(otherBeing);
                    locationsPopulation.remove(predatorPosition);
                    locationsPopulation.put(expectedPosition, predator);
                }
            }
        }   
        else {
            // Do nothing, stay in the same position.
        }
    }

    protected abstract Point getExpectedPosition(Movement move, Point currentPosition);
}
