/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import utility.RouteDisplay;
import station.BusStation;
import java.util.ArrayList;
import java.util.List;
import station.Station;
import utility.MyArray;

/**
 *
 * @author Yap Jie Xiang
 */
public class BusService extends Service {

    private final ArrayList<String> distance;// HashMap<code, distance>
    private final ArrayList<String> map;// for feeder bus services whose start and end station are the same

    public BusService(String name) {
        super(name);
        this.distance = new ArrayList<>();
        this.map = new ArrayList<>();
    }

    @Override
    public String getName() {
        return super.getName().substring(11);
    }

    @Override
    protected void add(Station station) {
        ArrayList<String> id = new ArrayList<>();
        id.add(station.getCode());
        super.add(id, station);
    }

    public void add(BusStation busStation, String distance) {
        add(busStation);
        this.distance.add(distance);
        this.map.add(busStation.getCode());
    }

    /**
     * Calculates the distance from start to end
     *
     * @param start start bus stop
     * @param end end bus stop
     * @return end - start, or 0.0 if station not found
     */
    @Override
    public double calcDistance(String start, String end) {

        if (!hasDirection(start, end)) {
            return -1.0;
        }

        Integer startStation = map.indexOf(start);
        Integer endStation = map.lastIndexOf(end);

        double result = Double.parseDouble(distance.get(endStation));
        result -= Double.parseDouble(distance.get(startStation));

        return (double) Math.round(result * 10d) / 10d;

    }

    @Override
    public List<Station> getRoute(String start, String end) {
        int startIndex = map.indexOf(start);
        int endIndex = map.lastIndexOf(end);

        return super.getRoute(startIndex, endIndex);
    }

    @Override
    public RouteDisplay getRouteDisplay(String input) {

        String description;
        if (input == null) {
            String name = super.getName();
            description = "Bus Service\t" + name.substring(0, 5) + " " + name.substring(11)
                    + "\n\tDirection #" + name.substring(8, 9);
        } else {
            description = "Bus Station" + super.get(input).toString();
        }

        return new RouteDisplay(super.getName(),
                distance.get(getCount() - 1),
                description,
                getRoute(),
                distance
        );
    }

    @Override
    public boolean hasDirection(String start, String end) {
        return (map.indexOf(start) < map.lastIndexOf(end));
    }

    @Override
    public List<String> getDistanceArray(String start, String end) {
        Integer startInt = map.indexOf(start);
        Integer endInt = map.lastIndexOf(end);
        boolean isReverse = false;

        if (startInt > endInt) {
            isReverse = true;
            int temp = startInt;
            startInt = endInt;
            endInt = temp;
        }

        if (startInt != -1 && endInt != -1) {
            List<String> result = new ArrayList<>(distance.subList(startInt, endInt + 1));
            if (isReverse) {
                result = MyArray.reverseStringArray(result);
            }
            result = MyArray.startFromX(result, 0.0);
            return result;
        }
        return null;
    }

    @Override
    public boolean has(String searchStr) {
        return (super.has(searchStr) && !distance.get(indexOf(searchStr)).equals("-"));
    }

    @Override
    public String toString() {
        return "BusService " + getName() + '\n' + getRoute() + '\n' + distance + '\n';
    }

}
