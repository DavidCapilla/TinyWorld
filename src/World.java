
public abstract class World {

    public int nRows;
    public int nColumns;
    private int[][] coordinates;
    
    public World (int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        coordinates = new int[this.nRows][this.nColumns];
    }
}
