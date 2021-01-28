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
public class BusLocation {

    private final double X;
    private final double Y;
    private final int ZID;
    private final String code;

    public BusLocation(double X, double Y, int ZID, String code) {
        this.X = X;
        this.Y = Y;
        this.ZID = ZID;
        this.code = code;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public int getZID() {
        return ZID;
    }

    public String getCode() {
        return code;
    }

}
