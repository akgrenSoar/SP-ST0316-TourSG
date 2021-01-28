/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import utility.RouteDisplay;
import station.MrtStation;
import java.util.ArrayList;
import java.util.List;
import station.Station;

/**
 *
 * @author Yap Jie Xiang
 */
public class MrtService extends Service {

    public MrtService(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName().substring(0, 2);
    }

    @Override
    protected void add(Station station) {
        ArrayList<String> id = new ArrayList<>();
        id.add(station.getCode());
        id.add(station.getName());
        super.add(id, station);
    }

    public void add(MrtStation mrtStation) {
        add((Station) mrtStation);
    }

    @Override
    public RouteDisplay getRouteDisplay(String input) {

        String description;
        if (input == null) {
            description = "Mrt Service\t" + super.getName();
        } else {
            description = "Mrt Station" + super.get(input).toString();
        }

        return new RouteDisplay(super.getName(),
                "" + super.getCount(),
                description,
                getRoute(),
                null
        );
    }

    @Override
    public boolean hasDirection(String start, String end) {
        return true;
    }

    @Override
    public List<String> getDistanceArray(String start, String end) {
        Integer startInt = indexOf(start);
        Integer endInt = indexOf(end);

        if (startInt > endInt) {
            int temp = startInt;
            startInt = endInt;
            endInt = temp;
        }

        if (startInt != null && endInt != null) {
            ArrayList<String> result = new ArrayList<>();
            for (int i = startInt; i <= endInt; i++) {
                result.add("" + (double)(i - startInt));
            }
            return result;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Mrt" + super.toString();
    }

}
