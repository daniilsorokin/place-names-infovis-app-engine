/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessPart;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@student.uni-tuebingen.de>
 */
public class Toponym {
    private String name;
    private String latName;
    private Double lat;
    private Double lng;
    private String group;
    
    /**
     * Basic constructors. Just creates a new toponym with a name.
     * 
     * @param name 
     */
    public Toponym (String latName){
        this.name = "";
        this.latName = latName;
        this.lat = null;
        this.lng = null;
        this.group = null; 
    }

    /**
     * Constructor with all information about a toponym specified.
     * 
     * @param name
     * @param latName
     * @param lat
     * @param lng
     * @param group 
     */
    public Toponym (String latName, double lat, double lng, String group){
        this.name = "";
        this.latName = latName;
        this.lat = lat;
        this.lng = lng;
        this.group = group; 
    }
    
    /**
     * Constructor with all information except coordinates.
     * 
     * @param name
     * @param latName
     * @param group 
     */
    public Toponym (String latName, String group){
        this.name = "";
        this.latName = latName;
        this.lat = null;
        this.lng = null;
        this.group = group; 
    }
    
    /**
     * Constructor with all information except a group.
     * 
     * @param name
     * @param latName
     * @param lat
     * @param lng 
     */
    public Toponym (String latName, double lat, double lng){
        this.name = "";
        this.latName = latName;
        this.lat = lat;
        this.lng = lng;
        this.group = null; 
    }
    
    /**
     * Returns the name of the toponym in the original orthography
     * 
     * @return the name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns the name of the toponym transcribed into Latin.
     * 
     * @return the name.
     */
    public String getLatinName(){
        return latName;
    }
    
    /**
     * Latitude of the toponym's coordinates.
     * 
     * @return latitude or null if there is no data.
     */
    public Double getLatitude(){
        return lat;
    }
    
    /**
     * Longitude of the toponym's coordinates.
     * 
     * @return longitude or null if there is no data.
     */
    public Double getLongitude(){
        return lng;
    }
    
    /**
     * Returns the name of the group, toponym was assigned to.
     * 
     * @return the name of the group. null if there is no group set.
     */
    public String getGroup(){
        return group;
    }
    
}
