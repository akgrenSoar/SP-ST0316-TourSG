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
public abstract class Station {

    private final String code;
    private final String name;

    public Station(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public abstract String getDescription();

    @Override
    public String toString() {
        return "\t" + code + "\n\t" + name;
    }

}
