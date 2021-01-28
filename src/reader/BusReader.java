/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import station.BusLocation;
import service.BusService;
import station.BusStation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import toursgpro.Manager;

/**
 *
 * @author Yap Jie Xiang
 */
public class BusReader {

    private static final String BUS_LOCATION = "resources/lta-bus_stop_locations.csv";
    private static final String BUS_STATION = "resources/lta-bus_stop_codes.csv";
    private static final String BUS_SBS = "resources/lta-sbst_route.csv";
    private static final String BUS_SMRT = "resources/lta-smrt_route.csv";

    public static ArrayList<BusService> getBusServices() {
        ArrayList<BusService> result = new ArrayList<>();

        HashMap<String, BusStation> stationMap = readBusStation();

        result.addAll(getBusServices(stationMap, BUS_SBS, "SBS "));
        result.addAll(getBusServices(stationMap, BUS_SMRT, "SMRT"));

        return result;
    }

    private static ArrayList<BusService> getBusServices(HashMap<String, BusStation> stationMap, String fileName, String prefix) {

        ArrayList<BusService> busServiceArray = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)))) {

            // Checking first line of csv file
            if (!bufferedReader.readLine().equals("SVC_NUM,DIR,ROUTE_SEQ,BS_CODE,DISTANCE")) {
                Manager.showError(fileName + " header should be SVC_NUM,DIR,ROUTE_SEQ,BS_CODE,DISTANCE");
                System.exit(1);
            }

            String line;
            String[] tokens;
            BusService busService = null;

            String serviceNo;
            String directionNumber = "0";
            boolean directionNext = false;
            String stationCode;
            String distance;

            while ((line = bufferedReader.readLine()) != null && !line.equals("")) {

                // SVC_NUM,     DIR,      ROUTE_SEQ,      BS_CODE,        DISTANCE
                tokens = line.split(",");
                serviceNo = prefix + " DIR" + tokens[1] + "  " + tokens[0];
                stationCode = tokens[3];
                distance = tokens[4];
                if (!directionNumber.equals(tokens[1])) {
                    directionNext = true;
                    directionNumber = tokens[1];
                }

                if (busService == null) {                                                     // First Bus Service
                    busService = new BusService(serviceNo);
                    directionNext = false;
                } else if (directionNext) {                                                     // Next Direction
                    busServiceArray.add(busService);
                    busService = new BusService(serviceNo);
                    directionNext = false;
                } else if (!busService.getName().equals(tokens[0])) {           // Next Bus Service
                    busServiceArray.add(busService);
                    busService = new BusService(serviceNo);
                }

                busService.add(stationMap.get(stationCode), distance);
            }

            if (busService != null) {
                busServiceArray.add(busService);
            }

        } catch (IOException e) {
            Manager.showError("IOException caught when reading " + fileName);
        }

        return busServiceArray;
    }

    private static HashMap<String, BusStation> readBusStation() {

        HashMap<String, BusLocation> locationMap = readBusLocation();
        HashMap<String, BusStation> stationMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(BUS_STATION)))) {

            // Checking first line of csv file
            if (!bufferedReader.readLine().equals("BUS_CODE,ROAD_DESCRIPT,BUSSTOP_DESC")) {
                Manager.showError(BUS_LOCATION + " header should be BUS_CODE,ROAD_DESCRIPT,BUSSTOP_DESC");
                System.exit(1);
            }

            String line;
            String[] tokens;
            while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                // BUS_CODE,ROAD_DESCRIPT,BUSSTOP_DESC
                tokens = line.split(",");
                BusStation busStation = new BusStation(
                        tokens[0],// CODE
                        tokens[1],// ROAD
                        tokens[2],// DESCRIPTION
                        locationMap.get(tokens[0])
                );
                stationMap.put(tokens[0], busStation);
            }

        } catch (IOException e) {
            Manager.showError("IOException caught when reading " + BUS_STATION);
        }

        return stationMap;
    }

    private static HashMap<String, BusLocation> readBusLocation() {
        HashMap<String, BusLocation> locationMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(BUS_LOCATION)))) {

            // Checking first line of csv file
            if (!bufferedReader.readLine().equals("X,Y,ZID,BUSCODE")) {
                Manager.showError(BUS_LOCATION + " header should be X,Y,ZID,BUSCODE");
                System.exit(1);
            }

            String line;
            String[] tokens;
            while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                // X,Y,ZID,BUSCODE
                tokens = line.split(",");
                BusLocation busLocation = new BusLocation(
                        Double.parseDouble(tokens[0]),// X
                        Double.parseDouble(tokens[1]),// Y
                        Integer.parseInt(tokens[2]),// ZID
                        tokens[3]// BUSCODE
                );
                locationMap.put(tokens[3], busLocation);
            }

        } catch (IOException e) {
            Manager.showError("IOException caught when reading " + BUS_LOCATION);
        }

        return locationMap;
    }

}
