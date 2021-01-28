/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toursgpro;

import utility.RouteDisplay;
import reader.MrtReader;
import reader.BusReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import service.MrtService;
import service.Service;
import station.MrtStation;
import station.Station;
import utility.MyArray;

/**
 *
 * @author Yap Jie Xiang
 */
public class Manager {

    private static final int ERROR_MESSAGE_TYPE = 2;

    private static final ArrayList<Service> ALL_SERVICES = new ArrayList<>();

    public static void initServices() {
        ALL_SERVICES.addAll(MrtReader.getMrtServices());
        ALL_SERVICES.addAll(BusReader.getBusServices());

    }

    /**
     * Check if have station name or service name
     *
     * @param identifier
     * @return true if found
     */
    public static boolean has(String identifier) {
        for (Service service : ALL_SERVICES) {
            if (service.has(identifier) || service.getName().toLowerCase().equals(identifier.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if have station name only
     *
     * @param station
     * @return
     */
    public static boolean hasStation(String station) {
        for (Service service : ALL_SERVICES) {
            if (service.has(station)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the RouteDisplay of all stations with the service name or with the
     * station code
     *
     * @param identifier
     * @return
     */
    public static ArrayList<RouteDisplay> findServices(String identifier) {
        ArrayList<RouteDisplay> routeDisplay = new ArrayList<>();
        for (Service service : ALL_SERVICES) {
            if (service.getName().toLowerCase().equals(identifier.toLowerCase())) {
                routeDisplay.add(service.getRouteDisplay(null));
            } else if (service.has(identifier)) {
                routeDisplay.add(service.getRouteDisplay(identifier));
            }
        }
        return routeDisplay;
    }

    /**
     * Finds all possible transfers between two routes
     *
     * @param service1
     * @param service2
     * @return
     */
    private static ArrayList<Station> findIntersection(Service service1, Service service2) {
        ArrayList<Station> results = new ArrayList<>();

        for (Station station : service1.getRoute()) {
            if (service2.has(station.getName()) || service2.has(station.getCode())) {

                results.add(station);
            }
        }

        return results;
    }

    /**
     * Generates a RouteDisplay for the direct route
     *
     * @param service
     * @param start
     * @param end
     * @return
     */
    private static RouteDisplay getDirectRoute(Service service, String start, String end) {

        // Check if direction is serviced
        if (!service.hasDirection(start, end)) {
            return null;
        }

        // Name, totalDistance, Description, route, distanceArray
        return new RouteDisplay(
                "[" + service.calcDistance(start, end) + "] " + service.getName(),
                "" + service.calcDistance(start, end),
                "Direct Route\n" + service.getName(),
                service.getRoute(start, end),
                service.getDistanceArray(start, end)
        );
    }

    /**
     * Generates a RouteDisplay for the transfer route
     *
     * @param startService
     * @param start
     * @param endService
     * @param end
     * @param transfer
     * @return
     */
    private static RouteDisplay getTransferRoute(Service startService, String start,
            Service endService, String end, String transfer) {

        //Check if direction is serviced
        if (!startService.hasDirection(start, transfer)) {
            return null;
        }
        if (!endService.hasDirection(transfer, end)) {
            return null;
        }

        String name = startService.getName() + " ---" + transfer + "--- " + endService.getName();

        double totalDistance = (startService.calcDistance(start, transfer) + endService.calcDistance(transfer, end));
        totalDistance = (double) (Math.round(totalDistance * 10d) / 10d);

        List<Station> routeArray = startService.getRoute(start, transfer);
        routeArray.add(new MrtStation("-", "-"));
        routeArray.addAll(endService.getRoute(transfer, end));

        List<String> distanceArray = startService.getDistanceArray(start, transfer);
        distanceArray.add("-");
        List<String> endDistanceArray = endService.getDistanceArray(transfer, end);
        distanceArray.addAll(MyArray.startFromX(endDistanceArray, startService.calcDistance(start, transfer)));

        return new RouteDisplay(
                "[" + totalDistance + "] " + name,
                "" + totalDistance,
                "Transfer Route\n" + name,
                routeArray,
                distanceArray
        );
    }

    /**
     * Finds all possible routes between two stations. Maximum of one transfer
     *
     * @param start
     * @param end
     * @return
     */
    public static ArrayList<RouteDisplay> getDirections(String start, String end) {

        // If searching for MRT Station, convert station codes (NS1) to station names(jurong east) first
        for (Service startService : ALL_SERVICES) {
            if (startService.has(start) && startService instanceof MrtService) {
                start = startService.get(start).getName();

                for (Service endService : ALL_SERVICES) {
                    if (endService.has(end) && endService instanceof MrtService) {
                        end = endService.get(end).getName();
                        break;
                    }
                }
                break;
            }
        }

        ArrayList<RouteDisplay> routeDisplay = new ArrayList<>();

        RouteDisplay buffer;

        for (Service startService : ALL_SERVICES) {
            if (startService.has(start)) {

                for (Service endService : ALL_SERVICES) {
                    if (endService.has(end)) {

                        // Direct route
                        if (startService.equals(endService)) {
                            buffer = getDirectRoute(startService, start, end);
                            if (buffer != null) {
                                routeDisplay.add(buffer);
                            }
                        } else {// Transfer route
                            for (Station intersect : findIntersection(startService, endService)) {

                                if (intersect instanceof MrtStation) {
                                    buffer = getTransferRoute(startService, start, endService, end, intersect.getName());
                                } else {
                                    buffer = getTransferRoute(startService, start, endService, end, intersect.getCode());
                                }

                                if (buffer != null) {
                                    routeDisplay.add(buffer);
                                }

                            }

                        }

                    }
                }

            }
        }

        return routeDisplay;
    }

    public static void showError(String message) {

        switch (ERROR_MESSAGE_TYPE) {
            case 0:
                break;
            case 1:
                System.out.println(message);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, message);
                break;
        }

    }
}
