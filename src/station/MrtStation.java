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
public class MrtStation extends Station {

    public MrtStation(String code, String name) {
        super(code, name);
    }

    @Override
    public String getDescription() {
        return "-";
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
