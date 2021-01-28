/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import service.MrtService;
import station.MrtStation;
import service.Service;
import toursgpro.Manager;

/**
 *
 * @author Yap Jie Xiang
 */
public class MrtReader {

    private static final String MRT_FILE = "resources/MRT.txt";

    public static ArrayList<Service> getMrtServices() {

        ArrayList<Service> result = new ArrayList<>();

        try (BufferedReader bufferedInput = new BufferedReader(new FileReader(new File(MRT_FILE)))) {

            // Add each service to result array
            MrtService mrtService;
            while ((mrtService = nextMrtService(bufferedInput)) != null) {
                result.add(mrtService);
            }

            // Close File
            bufferedInput.close();

        } catch (IOException e) {
            Manager.showError("IOException caught when reading " + MRT_FILE);
        }

        return result;
    }

    private static MrtService nextMrtService(BufferedReader bufferedReader) throws IOException {

        String str;//String buffer

        //Verify 'start' tag
        str = bufferedReader.readLine();
        if (str == null || !str.equals("(start)")) {
            return null;
        }

        //Create local variables
        MrtService mrtLine;
        String code;
        String name;

        //Read first station
        code = bufferedReader.readLine().trim();
        name = bufferedReader.readLine().trim();
        if (code == null || name == null) {//Check data
            Manager.showError("Error detected in Mrt Data File");
            return null;
        }
        //Set name of Route and add first station
        mrtLine = new MrtService(code.substring(0, 2) + " Line");
        mrtLine.add(new MrtStation(code, name));

        //Loop to add all station
        while ((str = bufferedReader.readLine()) != null) {
            //Verify 'end' tag has NOT been reached
            if (str.equals("(end)")) {
                break;
            }

            //Read next station
            code = str.trim();
            name = bufferedReader.readLine().trim();
            if (name == null) {
                Manager.showError("Error detected in Mrt Data File");
                return null;
            }
            //Append station station
            mrtLine.add(new MrtStation(code, name));
        }

        return mrtLine;
    }

}
