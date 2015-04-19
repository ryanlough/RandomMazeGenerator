// Final project
// Landegger Theo
// turtle
// Lough Ryan
// rlough
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import tester.*;
/**
 * Examples of Mazes
 * @author Ryan
 *
 */
public class ExamplesTest {
    Posn p1 = new Posn(0, 0);
    Posn p2 = new Posn(0, 1);
    Posn p3 = new Posn(1, 0);
    Posn p4 = new Posn(1, 1);
    Posn p5 = new Posn(1, 1);
    
    KObjs k1 = new KObjs(p1, "north");
    KObjs k2 = new KObjs(p1, "north");
    KObjs k3 = new KObjs(p1, "south");
    KObjs k4 = new KObjs(p2, "north");
    
    ArrayList<KObjs> klist = new ArrayList<KObjs>();
    KObjs k = new KObjs(p1, "north");
    IRoom wall = new Wall();
    Room r1 = new Room(p1, wall, wall, wall, wall);
    Room r2 = new Room(p2, wall, wall, wall, wall);
    Room r3 = new Room(p3, wall, wall, wall, wall);
    Room r4 = new Room(p4, wall, wall, wall, wall);
    IRoom w = new Wall();
    
    ArrayList<Room> a =
            new ArrayList<Room>(Arrays.asList(r1, r2, r3, r4));
    Maze m = new Maze(2, 2);
    
    Maze m1 = new Maze(3, 1);
    MazeGame g = new MazeGame(m1, 1000, 1000);
    
    // Run Game
    public void testRun(Tester t) {
        init();
        g.bigBang(1000, 1000, .001);
    }
    /**
     * Initializes variables
     */
    public void init() {
        r1.addRoom("south", r2);
        r1.addRoom("east", r3);
        r2.addRoom("north", r1);
        r2.addRoom("east", r4);
        r3.addRoom("west", r1);
        r3.addRoom("south", r4);
        r4.addRoom("north", r3);
        r4.addRoom("west", r2);
        
        m.rooms = a;
        klist.add(k);
    }
    /**
     * Test addRoom method
     */
    @Test
    public void testAddRoom() {
        assertEquals(r1.south, wall);
        r1.addRoom("south", r2);
        assertEquals(r1.south, r2);
        assertEquals(r2.north, r1);
        assertEquals(r1.north, wall);
        r1.addRoom("north", r3);
        assertEquals(r1.north, r3);
        assertEquals(r3.south, r1);
    }
    /**
     * Test hasWall method
     */
    @Test
    public void testHasWall() {
        init();
        assertEquals(r1.hasWall("west"), true);
        assertEquals(r1.hasWall("east"), false);
    }
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        init();
        assertEquals(r1.toString(), "00false");
        assertEquals(r2.toString(), "01false");
        assertEquals(r3.toString(), "10true");
    }
    /**
     * Test the equals method
     */
    @Test
    public void testEquals() {
        assertEquals(p1.equals(p2), false);
        assertEquals(p1.equals(r1), false);
        assertEquals(p4.equals(p5), true);
        
        assertEquals(k1.equals(k2), true);
        assertEquals(k1.equals(k3), false);
        assertEquals(k1.equals(k4), false);
    }
    /**
     * Test the isWall method
     */
    @Test
    public void testIsWall() {
        assertEquals(wall.isWall(), true);
        assertEquals(r1.isWall(), false);
    }
    /**
     * Test the isLoop method
     */
    @Test
    public void testIsLoop() {
        this.init();
        assertEquals(wall.isLoop("south", r1), false);
        assertEquals(r1.isLoop("east", r3), true);
        assertEquals(w.isLoop("east", r3), false);
    }

    /**
     * Test the getDir method
     */
    @Test
    public void testGetDir() {
        assertEquals(m.getDir(0), "north");
        assertEquals(m.getDir(1), "south");
        assertEquals(m.getDir(2), "east");
        assertEquals(m.getDir(3), "west");
    }
    
    @Test
    public void testGetNewPosn() {
        assertEquals(m.getNewPosn(new Posn(1, 1), "north"), new Posn(1, 0));
        assertEquals(m.getNewPosn(new Posn(1, 1), "south"), new Posn(1, 2));
        assertEquals(m.getNewPosn(new Posn(1, 1), "east"), new Posn(2, 1));
        assertEquals(m.getNewPosn(new Posn(1, 1), "west"), new Posn(0, 1));
    }
    
    @Test
    public void testGoodDir() {
        assertEquals(m.goodDir(new Posn(0, 0), "north"), false);
        assertEquals(m.goodDir(new Posn(0, 1), "north"), true);
        assertEquals(m.goodDir(new Posn(0, 0), "south"), true);
        assertEquals(m.goodDir(new Posn(0, 1), "south"), false);
        assertEquals(m.goodDir(new Posn(0, 0), "east"), true);
        assertEquals(m.goodDir(new Posn(1, 0), "east"), false);
        assertEquals(m.goodDir(new Posn(0, 0), "west"), false);
        assertEquals(m.goodDir(new Posn(1, 0), "west"), true);
    }
    /**
     * Tests maze methods
     */
    @Test
    public void testMaze() {
        Maze mazeTest = new Maze(2, 2);
        assertEquals(mazeTest.indexes.size(), 4);
    }
    /**
     * Tests the checkLoop method
     */
    @Test
    public void testCheckLoop() {
        init();
        assertEquals(m.checkLoop(p1, p2), false);
    }
    /**
     * Tests getParent method
     */
    @Test
    public void testGetParent() {
        init();
        assertEquals(m.getParent(1) < 4, true);
    }
    /**
     * Tests the getIndex method
     */
    @Test
    public void testGetIndex() {
        init();
        assertEquals(m.getIndex(a, p1), 0);
        assertEquals(m.getIndex(a, p2), 1);
        assertEquals(m.getIndex(a, p3), 2);
        assertEquals(m.getIndex(a, p4), 3);
    }
    @Test
    public void testGenerate() {
        Maze zzz = new Maze(10, 10);
        assertEquals(zzz.rooms.size(), 100);
    }
    
    @Test
    public void testGetRoom() {
        assertEquals(m1.getRoom(m1.rooms, new Posn(0, 0)), m1.rooms.get(0));
        assertEquals(m1.getIndex(m1.rooms, new Posn(0, 0)), 0);
    }
    /**
     * Tests the MazeGame class
     */
    @Test
    public void testMazeGame() {
        init();
        g.drawMaze();
        g.drawPath();
        g.makeImage();
        g.onTick();
        g.onKeyEvent("z");
        g.onKeyEvent("x");
        g.onKeyEvent("c");
        //assertEquals(g.maze.rooms.size(), 100);
    }
    /**
     * Tests breadthFirstSearch and depthSearch
     */
    @Test
    public void testSearch() {
        init();
        assertEquals(Algorithms.breadthFirst(r1, "west", m).size(), 2);
        assertEquals(Algorithms.breadthFirst(r2, "north", m).size(), 1);
        assertEquals(Algorithms.depththFirst(m).size(), 3);
    }
    /**
     * Tests rev
     */
    @Test
    public void testRev() {
        init();
        assertEquals(Algorithms.rev("north"), "south");
        assertEquals(Algorithms.rev("south"), "north");
        assertEquals(Algorithms.rev("east"), "west");
        assertEquals(Algorithms.rev("west"), "east");
    }
}
