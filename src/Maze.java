import java.io.*;

public class Maze {
    private final int dimension = 15;
    public int counter = 0;

    // Checks if we can move to (x,y)
    boolean canMove(char[][] maze, boolean found, int x, int y) {
        if (found) {
            return (x >= 0 && x < dimension && y >= 0 && y < dimension && (maze[x][y] == '.' || maze[x][y] == '0' || maze[x][y] == 'k'));
        } else {
            return (x >= 0 && x < dimension && y >= 0 && y < dimension && (maze[x][y] == '.' || maze[x][y] == 'k'));
        }
    }


    boolean solveMaze(char[][] maze) {
        if (!solveMazeUtil(maze, false, 0, 1)) {
            System.out.print("Solution doesn't exist\n");
            return false;
        }
        return true;
    }

    // A recursive function to solve Maze problem
    boolean solveMazeUtil(char[][] maze, boolean found, int x, int y) {
        // please do not delete/modify the next line!
        counter++;
        char path_char = '0';
        char rewrite_char = '.';

        if (x == 14 && y == 13 && found) {
            maze[x][y] = '1';
            return true;
        } else {
            rewrite_char = maze[x][y];

        }

        if (canMove(maze, found, x, y)) {
            if (rewrite_char == 'k'){
                found = true;
            }

            if (found) {
                path_char = '1';
            }

            maze[x][y] = path_char;

            if (rewrite_char == 'k'){
                maze[x][y] = 'k';

            }

            if (x + 1 < 15) {
                if (solveMazeUtil(maze, found, x + 1, y)) {
                    return true;
                }
            }

            if (y+1< 15) {
                if (solveMazeUtil(maze, found, x, y + 1)) {
                    return true;
                }
            }

            if (y-1>=0) {
                if (solveMazeUtil(maze, found, x, y - 1)) {
                    return true;
                }
            }

            if (x-1>=0) {
                if (solveMazeUtil(maze, found, x - 1, y)) {
                    return true;
                }
            }

        } else {
            maze[x][y] = rewrite_char;
            return false;
        }
        return false;
    }

    //Loads maze from text file
    char[][] loadMaze(String directory) throws IOException{
        char[][] maze = new char[dimension][dimension];

        try (BufferedReader br = new BufferedReader(new FileReader(directory))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++){
                    maze[row][col] = line.charAt(col);
                }
                row++;
            }
        }
        return maze;
    }

    //Prints maze
    private static void printMaze(char[][] maze) {
        for (int i = 0; i < maze[0].length; i++) {
            for (int j = 0; j < maze[0].length; j++)
                System.out.print(" " + maze[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Maze m = new Maze();
        
        //all four maps solved
        for (int i = 3; i < 4; i++) {
            try {
                char[][] myMaze = m.loadMaze(System.getProperty("user.dir") + "/src/mazes/m"+i+".txt");

                System.out.println("\nMaze "+i);
                Maze.printMaze(myMaze);
                if(m.solveMaze(myMaze)){
                    System.out.println("\nThis bug is solved!");
                    Maze.printMaze(myMaze);
                }
            } catch (Exception e){
                System.out.print(System.getProperty("user.dir") + "/src/mazes/m"+i+".txt");
                System.out.print("\nFile was not found.");
            }

        }

    }
}


