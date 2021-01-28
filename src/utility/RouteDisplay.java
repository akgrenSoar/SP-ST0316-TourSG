/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.List;
import station.Station;

/**
 *
 * @author Yap Jie Xiang
 */
public class RouteDisplay implements Comparable<RouteDisplay> {

    // Name, totalDistance, Description, route, distanceArray
    private final String name;
    private final String totalDistance;
    private final String description;
    private final List<Station> route;
    private final List<String> distanceArray;

    public RouteDisplay(String name, String totalDistance, String description, List<Station> route, List<String> distanceArray) {
        this.name = name;
        this.totalDistance = totalDistance;
        this.description = description;
        this.route = route;
        this.distanceArray = distanceArray;
    }

    public String getName() {
        return name;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public String getDescription() {
        return description;
    }

    public List<Station> getRoute() {
        return route;
    }

    public List<String> getDistanceArray() {
        return distanceArray;
    }

    @Override
    public String toString() {
        return "RouteDisplay{" + "name=" + name + ", totalDistance=" + totalDistance + ", description=" + description + ", route=" + route + ", distanceArray=" + distanceArray + '}';
    }

    @Override
    public int compareTo(RouteDisplay o) {
        int result = (int) (Double.parseDouble(totalDistance) * 10) - (int) (Double.parseDouble(o.getTotalDistance()) * 10);
        return result;
    }

}
