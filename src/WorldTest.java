import static org.junit.Assert.*;

import org.junit.Test;

public class WorldTest {

   
    @Test
    public void testCreateInitialSource10ElementsCompareToElementsInLocationSource() {
        int nRows = 100;
        int nColumns = 100;
        int initialSourceQuantity = 10;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createSource(initialSourceQuantity);
        assertEquals(initialSourceQuantity, flatWorld.getSourcePopulation());
    }   

    @Test
    public void testCreateInitialPrey5ElementsCompareToElementsInLocationPrey() {
        int nRows = 100;
        int nColumns = 100;
        int initialPreyQuantity = 5;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createPrey(initialPreyQuantity);
        assertEquals(initialPreyQuantity, flatWorld.getPreyPopulation());
    }
    
    @Test
    public void testCreateInitialPredator4ElementsCompareToElementsInLocationPredator() {
        int nRows = 100;
        int nColumns = 100;
        int initialPredatorQuantity = 4;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createPredator(initialPredatorQuantity);
        assertEquals(initialPredatorQuantity, flatWorld.getPredatorPopulation());
    }

    @Test
    public void testGetTotalPopulation() {
        int nRows = 100;
        int nColumns = 100;
        int sourceQuantity = 10;
        int preyQuantity = 4;
        int predatorQuantity = 3;
        int sumOfPopulations;   
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createSource(sourceQuantity);
        flatWorld.createPrey(preyQuantity);
        flatWorld.createPredator(predatorQuantity);
        
        sumOfPopulations = sourceQuantity + preyQuantity + predatorQuantity;
        assertEquals(sumOfPopulations, flatWorld.getTotalPopulation());
    }
    
    @Test
    public void testCompareGetTotalPopulationWithSumOfPopulations() {
        int nRows = 100;
        int nColumns = 100;
        int sourceQuantity = 10;
        int preyQuantity = 4;
        int predatorQuantity = 3;
        int sumOfPopulations;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createSource(sourceQuantity);
        flatWorld.createPrey(preyQuantity);
        flatWorld.createPredator(predatorQuantity);
        
        sumOfPopulations = flatWorld.getSourcePopulation() + flatWorld.getPreyPopulation() 
                                                               + flatWorld.getPredatorPopulation();
        assertEquals(sumOfPopulations, flatWorld.getTotalPopulation());
    }

}
