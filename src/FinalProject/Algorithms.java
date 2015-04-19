package FinalProject;

import java.util.ArrayList;

// Final project
// Landegger Theo
// turtle
// Lough Ryan
// rlough
public class Algorithms {
    /**
     *  Performs the breadth first search algorithm for the maze
     */
    public static ArrayList<KObjs> breadthFirst(Room start, String dir, 
            Maze m) {
        ArrayList<KObjs> front = new ArrayList<KObjs>();
        if (dir.equals("north")) {
            if (!start.east.isWall()) {
                front.add(new KObjs(((Room)start.east).coord, "west"));
            }
            if (!start.west.isWall()) {
                front.add(new KObjs(((Room)start.west).coord, "east"));
            }
            if (!start.south.isWall()) {
                front.add(new KObjs(((Room)start.south).coord, "north"));
            }
        }
        else if (dir.equals("south")) {
            if (!start.east.isWall()) {
                front.add(new KObjs(((Room)start.east).coord, "west"));
            }
            if (!start.west.isWall()) {
                front.add(new KObjs(((Room)start.west).coord, "east"));
            }
            if (!start.north.isWall()) {
                front.add(new KObjs(((Room)start.north).coord, "south"));
            }
        }
        else if (dir.equals("east")) {
            if (!start.north.isWall()) {
                front.add(new KObjs(((Room)start.north).coord, "south"));
            }
            if (!start.west.isWall()) {
                front.add(new KObjs(((Room)start.west).coord, "east"));
            }
            if (!start.south.isWall()) {
                front.add(new KObjs(((Room)start.south).coord, "north"));
            }
        }
        else if (dir.equals("west")) {
            if (!start.east.isWall()) {
                front.add(new KObjs(((Room)start.east).coord, "west"));
            }
            if (!start.north.isWall()) {
                front.add(new KObjs(((Room)start.north).coord, "south"));
            }
            if (!start.south.isWall()) {
                front.add(new KObjs(((Room)start.south).coord, "north"));
            }
        }
        else {
            throw new RuntimeException("Error: Not a direction");
        }
        return front;
    }
    /**
     *  Performs the breadth first search algorithm for the maze
     */
    public static ArrayList<KObjs> depththFirst(Maze m) {
        ArrayList<KObjs> klist = new ArrayList<KObjs>();
        klist.add(new KObjs(new Posn(0, 0), "west"));
        KObjs current = klist.get(0);
        boolean bcheck;
        KObjs test;
        while (!current.pos.equals(m.rooms.get(m.rooms.size() - 1).coord)) {
            bcheck = true;
            for (int i = 0; i < 4; i++) {
                String s = m.getDir(i);
                if (current.dir != s && !m.getRoom(m.rooms, 
                        current.pos).hasWall(s)) {
                    test = new KObjs(m.getNewPosn(current.pos, s), rev(s));
                    if (klist.indexOf(test) == -1) {
                        current = test;
                        klist.add(current);
                        i = 4;
                        bcheck = false;
                    }
                }
            }
            if (bcheck) {
                current = klist.get(klist.indexOf(current) - 1);
            }
        }
        return klist;
    }
    
    // Reverses a direction
    public static String rev(String s) {
        if (s.equals("north")) {
            return "south";
        }
        if (s.equals("south")) {
            return "north";
        }
        if (s.equals("east")) {
            return "west";
        }
        if (s.equals("west")) {
            return "east";
        }
        throw new RuntimeException("Hi");
    }
}