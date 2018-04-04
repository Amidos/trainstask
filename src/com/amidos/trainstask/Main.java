package com.amidos.trainstask;

public class Main {

    public static void main(String[] args) throws  Exception {

        DirectedGraph graph = new DirectedGraph();

        FileLoader fileLoader = new FileLoader();

        String graphStr = fileLoader.getFile("input.txt");

        if (graphStr != null && graphStr.length() > 0)
            graph.parseGraph(graphStr);
        else
            throw new Exception("Input file is not valid!");

        int distance;

        //Test 1: The distance of the route A-B-C
        distance = graph.getDistance(graph.getTown("A"), graph.getTown("B"), graph.getTown("C"));
        System.out.println("T1 Result: " + distance);


        //Test 2: The distance of the route A-D
        distance = graph.getDistance(graph.getTown("A"), graph.getTown("D"));
        System.out.println("T2 Result: "+distance);

        //Test 3: The distance of the route A-D-C
        distance = graph.getDistance(graph.getTown("A"), graph.getTown("D"), graph.getTown("C"));
        System.out.println("T3 Result: "+distance);

        //Test 4: The distance of the route A-E-B-C-D.
        distance = graph.getDistance(graph.getTown("A"), graph.getTown("E"), graph.getTown("B"), graph.getTown("C"),graph.getTown("D"));
        System.out.println("T4 Result: "+distance);

        try {
            //Test 5: The distance of the route A-E-D.
            distance = graph.getDistance(graph.getTown("A"), graph.getTown("E"), graph.getTown("D"));
            System.out.println("T5 Result: " + distance);
        }
        catch (Exception exp)
        {
            System.out.println("T1 Failed: " + exp.getMessage());
        }

        graph.resetStopped();

        //The number of trips starting at C and ending at C with a maximum of 3 stops.
        int trips = graph.getTripsWithMaxStop(graph.getTown("C"), graph.getTown("C"), 3);
        System.out.println("T6 Result: "+trips);

       //Test 7: The number of trips starting at A and ending at C with exactly 4 stops.
        trips = graph.getTripsWithExactStop(graph.getTown("A"), graph.getTown("C"), 4);
        System.out.println("T7 Result: "+trips);

        graph.resetStopped();

        //Test 8:  The length of the shortest route (in terms of distance to travel) from A to C.
        int shortest = graph.getShortestDirection(graph.getTown("A"), graph.getTown("C"));
        System.out.println("T8 Result: "+shortest);

        graph.resetStopped();

        //Test 9:  The length of the shortest route (in terms of distance to travel) from B to B.
        shortest = graph.getShortestDirection(graph.getTown("B"), graph.getTown("B"));
        System.out.println("T9 Result: "+shortest);

        graph.resetStopped();

        //Test 10:  The number of different routes from C to C with a distance of less than 30.
        trips = graph.getTripsWithDistance(graph.getTown("C"), graph.getTown("C"),30);
        System.out.println("T10 Result: "+trips);
    }
}
