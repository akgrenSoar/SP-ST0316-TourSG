/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import utility.RouteDisplay;
import station.Station;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import utility.MyArray;

/**
 *
 * @author Yap Jie Xiang
 */
public abstract class Service {

    private final String name;
    private final HashMap<String, Integer> codeMap;        // HashMap<ID, index>
    private final ArrayList<Station> route;
    private int count = 0;

    public Service(String name) {
        this.name = name;
        codeMap = new HashMap<>();
        route = new ArrayList<>();
    }

    protected void add(ArrayList<String> ID, Station station) {
        if (ID == null) {
            return;
        }

        for (String identifier : ID) {
            codeMap.put(identifier.toLowerCase(), count);
        }
        count++;
        route.add(station);
    }

    protected abstract void add(Station station);

    public abstract RouteDisplay getRouteDisplay(String str);

    public abstract boolean hasDirection(String start, String end);

    public abstract List<String> getDistanceArray(String start, String end);

    public String getName() {
        return name;
    }

    public boolean has(String searchStr) {
        return (codeMap.containsKey(searchStr.toLowerCase()));
    }

    public Station get(String searchStr) {

        Integer index = codeMap.get(searchStr.toLowerCase());

        return (index == null) ? null : route.get(index);
    }

    public ArrayList<Station> getRoute() {
        return route;
    }

    public List<Station> getRoute(String start, String end) {
        Integer startIndex = codeMap.get(start.toLowerCase());
        Integer endIndex = codeMap.get(end.toLowerCase());

        if (startIndex != null && endIndex != null) {
            if (startIndex > endIndex) {
                List<Station> temp = route.subList(endIndex, startIndex + 1);
                return new ArrayList<>(MyArray.reverseStationArray(temp));
            } else {
                return new ArrayList<>(route.subList(startIndex, endIndex + 1));
            }
        }
        return null;
    }

    protected List<Station> getRoute(int start, int end) {

        if (start != -1 && end != -1) {
            if (start > end) {
                List<Station> temp = route.subList(end, start + 1);
                return new ArrayList<>(MyArray.reverseStationArray(temp));
            } else {
                return new ArrayList<>(route.subList(start, end + 1));
            }
        }
        return null;
    }

    public double calcDistance(String start, String end) {
        if (has(start) && has(end)) {
            int startD = codeMap.get(start.toLowerCase());
            int endD = codeMap.get(end.toLowerCase());

            if (startD < endD) {
                return (double) (codeMap.get(end.toLowerCase()) - codeMap.get(start.toLowerCase()));
            } else {
                return (double) (codeMap.get(start.toLowerCase()) - codeMap.get(end.toLowerCase()));
            }
        }
        return 0.0;
    }

    protected int getCount() {
        return count;
    }

    protected Integer indexOf(String searchStr) {
        return codeMap.get(searchStr.toLowerCase());
    }

    @Override
    public String toString() {
        return "Service " + name + '\n' + route + '\n';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Service other = (Service) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }




}
