package ProcessPart;

import ServerPart.myHTTPServer;
import baseclasses.Tuple;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@student.uni-tuebingen.de>
 */
public class Dataset {
    private ArrayList<Toponym> toponyms;
    private HashMap<String, List<Toponym>> groups;
    
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
        groups = new HashMap<String, List<Toponym>>();
        BufferedReader src = new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING));
        String line;
        try {
            while ((line = src.readLine()) != null){
                String[] parts = line.split("\t");
                if (parts.length < 5) {
                    System.err.println("Wrong data type in the current line.");
                    continue;
                }
                Toponym newElement;
                if (parts[2].isEmpty() || parts[3].isEmpty()){
                    newElement = new Toponym(parts[1], parts[4]);
                    
                } else {
                    newElement = new Toponym(parts[1].trim(), 
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), parts[4].trim());
                }
                toponyms.add(newElement);
                if (newElement.getGroup() != null){
                    if (!groups.containsKey(newElement.getGroup())){
                        groups.put(newElement.getGroup(), new ArrayList<Toponym>());
                    }
                    groups.get(newElement.getGroup()).add(newElement);
                }
            }
            System.out.println("Number of toponyms: " + toponyms.size() + "\n" +
                    "Number of groups: " + groups.size());
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
     * Returns a list of names of all toponyms contained in the dataset.
     * 
     * @return 
     */
    public String[] getNamesOfGroups (){
        return groups.keySet().toArray(new String[groups.size()]);
    }
    
    /**
     * Returns coordinates of a particular toponym.
     * 
     * @param index toponym's index.
     * @return Tuple with coordinates
     */
    public Toponym getToponym (int index){
        return toponyms.get(index);
    }
}
