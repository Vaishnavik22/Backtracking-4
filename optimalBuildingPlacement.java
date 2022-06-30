/*
Problem: https://github.com/Vaishnavik22/Backtracking-4?organization=Vaishnavik22&organization=Vaishnavik22
*/
class BuildingPlacement {
    int h, w, n;
    int minDistance;
    int grid[][];
    int savedGrid[][];
    int dirs[][] = new int[][] {
        {-1,0}, {0,-1}, {1,0}, {0,1}  
    };
    
    public BuildingPlacement(int h, int w, int n) {
        this.h = h;
        this.w = w;
        this.n = n;
        this.minDistance = Integer.MAX_VALUE;
        savedGrid = new int[h][w];
        initializeGrid();
    }
    
    public void computeDistance() {
        findOptimalPlacement(0, 0, n);
        printResult();
    }
    
    // Function for backtracking
    private void findOptimalPlacement(int row, int col, int buildingsToBePlaced) {
        if (buildingsToBePlaced == 0) {
            bfsToGetMinDistance();
            return;
        }
        
        if (col == w) {
            ++row;
            col = 0;
        }
        
        for (int i = row; i < h; ++i) {
            for (int j = col + 1; j < w; ++j) {
                // place the building
                grid[i][j] = 0;
                findOptimalPlacement(row, col + 1, buildingsToBePlaced - 1);
                // remove building placement
                grid[i][j] = -1;
            }
        }
    }
    
    // BFS from placed buildings to parking lots to find the lot at the maximum distance.
    // This distance is what we want to minimize.
    private void bfsToGetMinDistance() {
        Queue<int[]> queue = new LinkedList<>();
        int distance = 0;
        boolean visitedPos[][] = new boolean[h][w];
        
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (grid[i][j] == 0) {
                    queue.add(new int[]{i, j});
                    visitedPos[i][j] = true;
                }
            }
        }
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                int pos[] = queue.poll();
                
                for (int d[] : dirs) {
                    int x = pos[0] + d[0];
                    int y = pos[1] + d[1];
                    
                    if (x >= 0 && x < h && y >= 0 && y < w && !visitedPos[x][y] && grid[x][y] == -1) {
                        queue.add(new int[]{x, y});
                        visitedPos[x][y] = true;
                    }
                }
            }
            ++distance;
        }
        if (distance - 1 < minDistance) {
            minDistance = distance - 1;
            saveGrid();
        }
    }
    
    /********************** HELPER FUNCTIONS ************************************/
    private void printResult() {
        System.out.println("Optimal grid placement is: ");
        for (int i = 0; i < h; ++i)
            System.out.println(Arrays.toString(savedGrid[i]));
         System.out.println("Minimum Distance is: " + minDistance);
    }
    
    private void saveGrid() {
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                savedGrid[i][j] = grid[i][j];
            }
        }
    }
    
    private void initializeGrid() {
        grid = new int[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                grid[i][j] = -1;
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        BuildingPlacement bp = new BuildingPlacement(4, 4, 3);
        bp.computeDistance();
    }
}