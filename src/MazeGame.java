// Final project
// Landegger Theo
// turtle
// Lough Ryan
// rlough

import java.util.ArrayList;
import java.util.Scanner;

import javalib.colors.*;
import javalib.impworld.*;
import javalib.worldimages.WorldImage;
import javalib.worldimages.LineImage;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.Posn;

/**
 * Represents a MazeGame
 * @author Ryan
 * @author Theo
 */
public class MazeGame extends World {
    
    Maze maze;
    WorldImage mazePic;
    ArrayList<KObjs> visited = new ArrayList<KObjs>();
    ArrayList<KObjs> front = new ArrayList<KObjs>();
    int width;
    int height;
    int roomWidth;
    int roomHeight;
    Black b = new Black();
    int choice = 0; // 0 Start, 1 Breadth, 2 Depth, 3 New Maze, 4 fast solve
    
    MazeGame(Maze maze, int width, int height) {
        this.maze = maze;
        this.width = width;
        this.height = height;
        mazePic = new RectangleImage(new Posn(width / 2, height / 2), 
                width, height, new White());
        this.roomWidth = width / maze.width;
        this.roomHeight = height / maze.height;
    }
    
    // Run game

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter width: ");
        int x = scan.nextInt();
        System.out.print("Enter height: ");
        int y = scan.nextInt();
        Maze smaze = new Maze(x, y);
        MazeGame start = new MazeGame(smaze, 1000, 1000);
        start.bigBang(1000, 1000, .3);
    }

    // Renders the maze
    public WorldImage makeImage() {
        drawMaze();
        drawPath();
        return mazePic;
    }
    
    // Renders the walls in a maze
    public void drawMaze() {
        for (Room r: this.maze.rooms) {
            Posn topleft = new Posn(r.coord.x * roomWidth, 
                    r.coord.y * roomHeight);
            Posn topright = new Posn(topleft.x + roomWidth, topleft.y);
            Posn botleft = new Posn(topleft.x, topleft.y + roomHeight);
            Posn botright = new Posn(topright.x, botleft.y);
            
            if (r.north.isWall()) {
                mazePic = new OverlayImages(mazePic, 
                        new LineImage(topleft, topright, b));
            }
            if (r.east.isWall()) {
                mazePic = new OverlayImages(mazePic, 
                        new LineImage(topright, botright, b));
            }
            if (r.south.isWall()) {
                mazePic = new OverlayImages(mazePic, 
                        new LineImage(botleft, botright, b));
            }
            if (r.west.isWall()) {
                mazePic = new OverlayImages(mazePic, 
                        new LineImage(topleft, botleft, b));
            }
        }
    }
    
    // Renders the path in a maze
    public void drawPath() {
        for (KObjs k: this.visited) {
            Posn spot = new Posn((k.pos.x * roomWidth) + (roomWidth / 2), 
                    (k.pos.y * roomHeight) + (roomHeight / 2));
            mazePic = new OverlayImages(mazePic, drawPathSpot(spot));
        }
    }
    
    // Draws a spot on a path
    public WorldImage drawPathSpot(Posn p) {
        if (choice == 3 || choice == 4) {
            return new RectangleImage(p, roomWidth / 2, 
                    roomHeight / 2, new Green());
        }
        else {
            return new RectangleImage(p, roomWidth / 2,
                    roomHeight / 2, new Blue());
        }
    }
    
    // Draws elements of the path over time
    public void onTick() {
        if (choice == 1) {
            if (visited.size() == 0) {
                front.add(new KObjs(maze.rooms.get(0).coord, "north"));
                visited.addAll(front);
            }
            @SuppressWarnings("unchecked")
            ArrayList<KObjs> clone = (ArrayList<KObjs>) front.clone();
            front = new ArrayList<KObjs>();
            for (KObjs k: clone) {
                if (k.pos.x == this.maze.width - 1 &&
                        k.pos.y == this.maze.height - 1) {
                    choice = 0;
                    front = new ArrayList<KObjs>();
                    break;
                }
                front.addAll(Algorithms.breadthFirst(maze.rooms.get(
                       maze.getIndex(maze.rooms, k.pos)), k.dir, maze));
            }
            visited.addAll(front);
        }
        if ((choice == 2 || choice == 3) && front.size() > 0) {
            visited.add(front.get(0));
            front.remove(0);
        }
        if (choice == 5 && front.size() > 0) {
            if (front.get(0).pos.x == this.maze.width - 1 &&
                    front.get(0).pos.y == this.maze.height - 1) {
                this.choice = 4;
                visited = Algorithms.solveMaze(maze);
            }
            visited.add(front.get(0));
        }
    }
    
    // Uses key inputs to choose maze algorithm
    public void onKeyEvent(String k) {
        if (k.equals("z")) {
            reset(true);
            choice = 1;
        }
        else if (k.equals("x")) {
            reset(true);
            choice = 2;
            front = Algorithms.depththFirst(maze);
        }
        else if (k.equals("c")) {
            reset(false);
            choice = 3;
            front = Algorithms.solveMaze(maze);
        }
        else if (k.equals("v")) {
            reset(true);
            choice = 4;
            visited = Algorithms.solveMaze(maze);
        }
        else if (k.equals("g")) {
            choice = 0;
            this.maze = new Maze(maze.width, maze.height);
            reset(true);
        }
        else if (k.equals("p")) {
            choice = 5;
            front.add(new KObjs(this.maze.rooms.get(0).coord, "west"));
        }
        else if (choice == 5 && front.size() > 0) {
            Room next = this.maze.getRoom(this.maze.rooms,
                    front.get(0).pos);
            if (k.equals("down") && !next.hasWall("south")) {
                reset(true);
                front.add(new KObjs(((Room)next.south).coord, "west"));
            }
            else if (k.equals("up")  && !next.hasWall("north")) {
                reset(true);
                front.add(new KObjs(((Room)next.north).coord, "west"));
            }
            else if (k.equals("left") && !next.hasWall("west")) {
                reset(true);
                front.add(new KObjs(((Room)next.west).coord, "west"));
            }
            else if (k.equals("right") && !next.hasWall("east")) {
                reset(true);
                front.add(new KObjs(((Room)next.east).coord, "west"));
            }
        }
    }
    
    // Resets Maze
    public void reset(boolean a) {
        front.clear();
        visited.clear();
        if (a) {
            mazePic = new RectangleImage(new Posn(width / 2, height / 2), 
                    width, height, new White());
        }
    }
}
