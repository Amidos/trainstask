package com.amidos.trainstask;
import java.util.Enumeration;
import java.util.Hashtable;

public class DirectedGraph {


    Hashtable<String, Town> townList = new Hashtable<String, Town>();
    Hashtable<String, Direction> townDirectionList = new Hashtable<String, Direction>();

    private Hashtable<Town, Direction> directionList;

    public DirectedGraph() {
        this.setRouteList(new Hashtable<Town, Direction>());
    }


    public Hashtable<Town, Direction> getDirectionList() {
        return directionList;
    }

    public void setRouteList(Hashtable<Town, Direction> routeList) {
        this.directionList = routeList;
    }

    public void addDirection(Town town, Direction direction)
    {
        this.directionList.put(town,direction);
    }

    public int getDistance(Town... towns) throws Exception
    {
        if (towns.length < 2)
            return 0;

        int distance = 0, j = 0;

        for (int i=0;i<towns.length-1;i++)
        {
            if (directionList.containsKey(towns[i]))
            {
                Direction dir = directionList.get(towns[i]);

                while (dir != null)
                {
                    if(dir.getTo().equals(towns[i+1])) {
                        distance += dir.getDistance();
                        j++;
                        break;
                    }
                    dir = dir.getCanGoTo();
                }
            }
            else {
                throw new Exception("NO SUCH ROUTE!");
            }
        }

        if (j < towns.length-1)
            throw new Exception("NO SUCH ROUTE!");

        return distance;
    }

    public void parseGraph(String graphStr)
    {
        String[] townNames = {"A","B","C","D","E"};

        for (String town : townNames)
        {
            townList.put(town,new Town(town));
        }

        //remove word 'Graph:' and all spaces
        graphStr = graphStr.replaceAll("\\s+|Graph:","");

        String[] nodesList = graphStr.split(",");

        for (String s : nodesList) {
            //we need str with 3 chars. Example: AB5
            if (s.length() == 3) {
                String start = String.valueOf(s.charAt(0));

                String end = String.valueOf(s.charAt(1));

                int distance = Character.getNumericValue(s.charAt(2));

                if (!townList.containsKey(start) && !townList.containsKey(end))
                    continue;

                Town startTown = townList.get(start);
                Town endTown = townList.get(end);

                Direction dir = new Direction(startTown, endTown, distance);

                if (townDirectionList.containsKey(start)) {
                    Direction existingDir = townDirectionList.get(start);

                    while (existingDir != null) {
                        if (existingDir.getCanGoTo() == null) {
                            existingDir.setCanGoTo(dir);
                            break;
                        }

                        existingDir = existingDir.getCanGoTo();
                    }

                } else {
                    townDirectionList.put(start, dir);
                }
            }
        }

        for (String name : townNames)
            this.addDirection(townList.get(name),townDirectionList.get(name));

    }

    public Town getTown(String letter)
    {
        return (townList.containsKey(letter)) ? townList.get(letter) : null;
    }

    private int getTripsWithMaxStop(Town start, Town destination, int stopLevel, int maxStop) throws Exception
    {
        if (!directionList.containsKey(start))
            throw new Exception("NO SUCH ROUTE!");

        stopLevel++;

        if (stopLevel > maxStop)
            return 0;

        Direction dir = directionList.get(start);

        start.setStopped(true);

        int foundTrips = 0;
        while (dir != null)
        {
            if (dir.getTo().equals(destination))
            {
                dir = dir.getCanGoTo();
                foundTrips++;
                continue;
            }
            else if (start.hasStopped())
            {
                foundTrips += getTripsWithMaxStop(dir.getTo(), destination, stopLevel, maxStop);
                stopLevel--;
            }

            dir = dir.getCanGoTo();
        }

        start.setStopped(false);

        return foundTrips;
    }

    public int getTripsWithMaxStop(Town start, Town destination, int maxStop) throws Exception
    {
        return getTripsWithMaxStop(start, destination,0,maxStop);
    }

    //A to C: "A B C D C"   A to C "A D C D C" and A to C  "A D E B C".
    private int getTripsWithExactStop(Town start, Town destination, int stopLevel, int exactStop) throws Exception
    {
        int foundTrips = 0;

        if (!this.directionList.containsKey(start))
            throw new Exception("NO SUCH ROUTE");

            stopLevel++;

            if (stopLevel > exactStop)
                return 0;

            Direction route = this.directionList.get(start);
            while (route != null)
            {
                if (route.getTo().equals(destination) && stopLevel == exactStop)
                {
                    foundTrips++;
                    route = route.getCanGoTo();
                    continue;
                }
                else {
                    foundTrips += getTripsWithExactStop(route.getTo(), destination, stopLevel, exactStop);
                }

                route = route.getCanGoTo();
            }

        return foundTrips;
    }

    public int getTripsWithExactStop(Town start, Town destination, int exactStop) throws Exception
    {
        return getTripsWithExactStop(start,destination,0,exactStop);
    }

    private int getShortestDirection(Town start, Town destination, int distance, int shorter) throws Exception
    {
        if (!directionList.containsKey(start))
            throw new Exception("NO SUCH ROUTE!");

        start.setStopped(true);

        Direction dir = directionList.get(start);

        while (dir != null)
        {
            if (dir.getTo().equals(destination) || !dir.getTo().hasStopped())
                distance += dir.getDistance();


            if (dir.getTo().equals(destination))
            {
                start.setStopped(false);

                return (shorter == 0 || distance < shorter) ? distance : shorter;
            }
            else if (!dir.getTo().hasStopped())
            {
                shorter = getShortestDirection(dir.getTo(), destination, distance, shorter);

                distance -= dir.getDistance();
            }

            dir = dir.getCanGoTo();
        }

        start.setStopped(false);

        return shorter;

    }

    public int getShortestDirection(Town start, Town destination) throws Exception {

        return getShortestDirection(start,destination, 0,0);

    }

    public void resetStopped()
    {
        if (directionList != null && directionList.size() > 0)
        {
            Enumeration<Town> enumKey = directionList.keys();
            while(enumKey.hasMoreElements()) {

                Town town = enumKey.nextElement();

                town.setStopped(false);

            }
        }
    }

    private int getTripsWithDistance(Town start, Town destination, int distance, int maxDistance) throws Exception
    {

        if (!directionList.containsKey(start))
            throw new Exception("NO SUCH ROUTE!");

        Direction dir = directionList.get(start);

        int foundTrips = 0;

        while (dir != null)
        {
            distance += dir.getDistance();

            if (distance <= maxDistance)
            {
                if (dir.getTo().equals(destination))
                {
                    foundTrips++;

                    foundTrips += getTripsWithDistance(dir.getTo(), destination, distance, maxDistance);

                    dir = dir.getCanGoTo();

                    continue;
                }
                else
                {
                    foundTrips += getTripsWithDistance(dir.getTo(), destination, distance, maxDistance);
                    distance -= dir.getDistance();
                }
            }
            else
                distance -= dir.getDistance();

            dir = dir.getCanGoTo();
        }

        return foundTrips;
    }

    public int getTripsWithDistance(Town start, Town destination, int maxDistance) throws Exception {

        return getTripsWithDistance(start, destination, 0, maxDistance);
    }

}
