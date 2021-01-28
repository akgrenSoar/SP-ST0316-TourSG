/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.ArrayList;
import java.util.List;
import station.Station;
import toursgpro.Manager;

/**
 *
 * @author Yap Jie Xiang
 */
public class MyArray {

    public static List<String> reverseStringArray(List<String> input) {
        List<String> result = new ArrayList<>();

        for (int i = input.size() - 1; i >= 0; i--) {
            result.add(input.get(i));
        }
        return result;
    }

    public static List<Station> reverseStationArray(List<Station> input) {
        List<Station> result = new ArrayList<>();

        for (int i = input.size() - 1; i >= 0; i--) {
            result.add(input.get(i));
        }
        return result;
    }

    public static List<String> startFromX(List<String> input, double X) {
        List<String> result = new ArrayList<>();

        try {
            double start = Double.parseDouble(input.get(0)) - X;

            for (String element : input) {
                if (element.equals("-")) {
                    result.add("-");
                    continue;
                }
                double deducted = Double.parseDouble(element) - start;
                result.add("" + (double) Math.round(deducted * 10d) / 10d);
            }
        } catch (NumberFormatException e) {
            Manager.showError(e.toString());
        }

        return result;
    }

}
