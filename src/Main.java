import core.Maze;

public class Main {
    public static void main(String[] args) {
        // later, this will be the main entry point, but for now, we're just testing
        
        Maze maze = new Maze(10, 10);

        System.out.println(maze.nodes.size());
    }
}
