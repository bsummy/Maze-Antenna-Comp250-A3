
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MazeExposedTests {
    char[][] m0, m1, m2, m3;
    Maze solver;

    @BeforeEach
    void setUp() throws IOException {
        solver = new Maze();

        m0 = solver.loadMaze("/Users/bennettsummy/IdeaProjects/comp250A3/src/mazes/m0.txt");
        m1 = solver.loadMaze("/Users/bennettsummy/IdeaProjects/comp250A3/src/mazes/m1.txt");
        m2 = solver.loadMaze("/Users/bennettsummy/IdeaProjects/comp250A3/src/mazes/m2.txt");
        m3 = solver.loadMaze("/Users/bennettsummy/IdeaProjects/comp250A3/src/mazes/m3.txt");
    }

    @Test
    @Tag("score:6")
    @DisplayName("Maze 0")
    void mazeTest0() {
        assertTrue(solver.solveMaze(m0));
    }

    @Test
    @Tag("score:7")
    @DisplayName("Maze 1")
    void mazeTest1() {
        assertTrue(solver.solveMaze(m1));
    }

    @Test
    @Tag("score:6")
    @DisplayName("Maze 2")
    void mazeTest2() {
        assertTrue(solver.solveMaze(m2));
    }

    @Test
    @Tag("score:6")
    @DisplayName("Maze 3")
    void mazeTest3() {
        assertFalse(solver.solveMaze(m3));
    }


    @Test
    @DisplayName("Custom Maze 1")
    void custom1() {
        char[][] maze;
        try {
            maze = solver.loadMaze("custom_mazes/custom_maze.txt");
            assertTrue(solver.solveMaze(maze));
            char[][] mazeSol = {
                    {'#', '0', '.', '.', '.', '.', '.', '.', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '0', '#', '#', '#', '#', '#', '.', '.', '.', '.', '#', '#', '#', '#'},
                    {'#', '0', '#', '#', '0', '0', '0', '#', '#', '#', '.', '.', '.', '.', '.'},
                    {'#', '0', '#', '#', '0', '#', '0', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '0', '0', '0', '0', '#', '0', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '0', '#', 'k', '#', '#', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '1', '1', '1', '#', '#', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '1', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '1', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '1', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '1', '#', '1', '1', '1', '1', '1', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '1', '1', '1', '#', '#', '#', '1', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '1', '1', '1', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '1', '#', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '1', '1', '1', '1', '#'},
            };
            assertArrayEquals(maze, mazeSol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Maze loop")
    void mazeTestLoop() throws IOException {
        char[][] aMaze = loadMaze("""
            #.#############
            #.#...........#
            #.#.#########.#
            #.#.#.......#.#
            #.#.#.#####.#.#
            #.#.#.#...#.#.#
            #.#.#.#.#.#.#.#
            #.#.#.#k#.#.#.#
            #.#.#.###.#.#.#
            #.#.#.....#.#.#
            #.#.#######.#.#
            #.#.........#.#
            #.###########.#
            #.............#
            #############.#
            """);
        assertTrue(solver.solveMaze(aMaze));
    }

    @Test
    @DisplayName("Maze branch")
    void mazeTestBranch() throws IOException {
        char[][] aMaze = loadMaze("""
            #.#############
            #.............#
            #######.#######
            #.............#
            #######.#######
            #.............#
            #######.#######
            #......k......#
            #######.#######
            #.............#
            #######.#######
            #.............#
            #######.#######
            #.............#
            #############.#
            """);
        assertTrue(solver.solveMaze(aMaze));
    }

    @Test
    @DisplayName("Another Maze 1")
    void maze1Test() throws IOException {
        char[][] aMaze = loadMaze("""
            #.#############
            #.............#
            ###.##.###..###
            #....#.####.#.#
            ##.#......###.#
            ##.#####......#
            ##.....#.######
            #k######.#...##
            #........#.#..#
            ###.##.....####
            #...###.##..###
            #.#...#####.###
            #.#######...###
            #....######...#
            ####......###.#
            """);
        assertTrue(solver.solveMaze(aMaze));
    }

    @Test
    @DisplayName("Maze 1 with no exit")
    void maze1NoExitTest() throws IOException {
        char[][] aMaze = loadMaze("""
            #.#############
            #.............#
            ###.##.###..###
            #....#.####.#.#
            ##.#......###.#
            ##.#####......#
            ##.....#.######
            #k######.#...##
            #........#.#..#
            ###.##.....####
            #...###.##..###
            #.#...#####.###
            #.#######...###
            #....######.###
            ####......###.#
            """);
        assertFalse(solver.solveMaze(aMaze));
    }

    @Test
    @DisplayName("Maze 1 with no key")
    void maze1NoKeyTest() throws IOException {
        char[][] aMaze = loadMaze("""
            #.#############
            #.............#
            ###.##.###..###
            #....#.####.#.#
            ##.#......###.#
            ##.#####......#
            ##.....#.######
            #.######.#...##
            #........#.#..#
            ###.##.....####
            #...###.##..###
            #.#...#####.###
            #.#######...###
            #....######...#
            ####......###.#
            """);
        assertFalse(solver.solveMaze(aMaze));
    }

    public char[][] loadMaze(String mazeString) throws IOException {
        char[][] maze = new char[15][15];

        try (BufferedReader br = new BufferedReader(new StringReader(mazeString))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    maze[row][col] = line.charAt(col);
                }
                row++;
            }
        }
        return maze;
    }
}
