/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package station;

/**
 *
 * @author Yap Jie Xiang
 */
public class BusStation extends Station {

    private final String description;
    private final BusLocation location;

    public BusStation(String code, String road, String description, BusLocation location) {
        super(code, road);
        this.description = description;
        this.location = location;
    }

    public BusLocation getLocation() {
        return location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" + description;
    }

}
