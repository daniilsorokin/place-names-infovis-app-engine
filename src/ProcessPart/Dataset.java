package ProcessPart;

import ServerPart.myHTTPServer;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@student.uni-tuebingen.de>
 */
public class Dataset {
    private ArrayList<Toponym> toponyms;
    
    public static final String ENCODING = myHTTPServer.ENCODING;
    
    /**
     * Constructs a new data set from the file provided as a parameter.
     * 
     * @param fileName
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException 
     */
    public Dataset (String fileName) throws FileNotFoundException, UnsupportedEncodingException{
        this (new File (fileName));
    }
    
    /**
     * Constructs a new data set from the file provided as a parameter.
     * 
     * @param file
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException 
     */
    public Dataset (File file) throws FileNotFoundException, UnsupportedEncodingException {
        toponyms = new ArrayList<Toponym>();
        
        BufferedReader src = new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING));
        String line;
        try {
            while ((line = src.readLine()) != null){
                String[] parts = line.split("\t");
                if (parts.length < 5) {
                    System.err.println("Wrong data type in the current line.");
                    continue;
                }
                if (parts[2].isEmpty() || parts[3].isEmpty()){
                    toponyms.add(new Toponym(parts[1], parts[4]));
                } else {
                    toponyms.add(new Toponym(parts[1].trim(), 
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), parts[4].trim()));
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Returns a list of names of all toponyms contained in the dataset.
     * 
     * @return 
     */
    public String[] getNamesOfToponyms (){
        String[] returnList = new String[toponyms.size()];
        for (int i = 0; i < toponyms.size(); i++){
            returnList[i] = toponyms.get(i).getLatinName();
        }
        return returnList;
    }
    
    /**
     * Returns coordinates of a particular toponym.
     * 
     * @param index toponym's index.
     * @return tuple with coordinates
     */
    public Tuple<Double,Double> getCoordOfToponym (int index){
        return new Tuple<Double, Double>(toponyms.get(index).getLatitude(), toponyms.get(index).getLongitude());
    }
}
