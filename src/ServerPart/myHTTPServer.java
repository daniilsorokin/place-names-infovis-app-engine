package ServerPart;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import java.util.*;
import ProcessPart.*;

/**
 * Template from http://www.prasannatech.net/2008/10/simple-http-server-java.html
 * @author Daniil Sorokin <daniil.sorokin@student.uni-tuebingen.de>
 */
public class myHTTPServer extends Thread{
    
    public static final String ENCODING = "UTF-8";
    
    public static final String HTML_START =
            "<html>" +
            "<title>HTTP Server in java</title>" +
            "<body>";
    
    public static final String HTML_END =
            "</body>" +
            "</html>";

    private Socket connectedClient = null;
    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;
    
    private Dataset workingDataset = null;
    
    public static void main (String args[]) throws Exception {
        
        ServerSocket Server = new ServerSocket (5000, 10, InetAddress.getLocalHost());
        System.out.println ("TCPServer Waiting for client on port 5000, address:" +  Server.getInetAddress());
        Dataset currentDataSet = new Dataset("data/toponyms_Ingria.txt");
        
        while(true) {
            Socket connected = Server.accept();
            (new myHTTPServer(connected, currentDataSet)).start();
        }
    }   
    
    /**
     * Main constructor. 
     * 
     * @param client client's connection.
     */
    public myHTTPServer(Socket client) {
        connectedClient = client;
    }
    
    /**
     * Main constructor with dataset
     * 
     * @param client client's connection.
     * @param dataset dataset to work with.
     */
    public myHTTPServer(Socket client, Dataset dataset) {
        connectedClient = client;
        workingDataset = dataset;
    }
    
    /**
     * Main methods of the thread
     */
    public void run() {
        try {
            System.out.println( "The Client "+
                    connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");
            
            inFromClient = new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));
            outToClient = new DataOutputStream(connectedClient.getOutputStream());
            
            String requestString = inFromClient.readLine();
            String headerLine = requestString;
            
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
            String httpQueryString = tokenizer.nextToken();
            
            StringBuffer responseBuffer = new StringBuffer();
            responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
            responseBuffer.append("The HTTP Client request is ....<BR>");
            
            System.out.println("The HTTP request string is ....");
            while (inFromClient.ready())
            {
                // Read the HTTP complete HTTP Query
                responseBuffer.append(requestString);
                responseBuffer.append("<BR>");
                System.out.println(requestString);
                requestString = inFromClient.readLine();
            }
            
            if (httpMethod.equals("GET")) {
                String query = httpQueryString.replaceFirst("/", "");
                query = URLDecoder.decode(query, ENCODING);
                File toLoad;
                if (httpQueryString.equals("/")) {
                    // The default home page
                    sendResponse(200, responseBuffer.toString());
                } else if (query.equals("index")){
                    sendResponse(200, new File("src/HTMLPart/index.html"));
                } else if (query.equals("getdata")){
                    String[] returnData = null;
                    if (workingDataset != null) {
                        returnData = workingDataset.getNamesOfToponyms();
                    } else {
                        System.err.println("Dataset is empty");
                    }
                    sendResponse(200, returnData);
                } else if (query.split("\\?", 2)[0].equals("getCoordinates")){
                    int index = Integer.parseInt(query.split("\\?", 2)[1].trim().replace("id=", ""));
                    Tuple<Double, Double> returnData = null;
                    if (workingDataset != null) {
                        returnData = workingDataset.getCoordOfToponym(index);
                    } else {
                        System.err.println("Dataset is empty");
                    }
                    sendResponse(200, returnData);
                } else if ((toLoad = new File(query)).isFile()){
                    sendResponse(200, toLoad);
                } else if (query.equals("test")){
                    sendResponse(200, "This is a test!");
                } else {
                    sendResponse(404, "<b>The Requested resource not found ...." +
                            "Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>");
                }
            }
            else sendResponse(404, "<b>The Requested resource not found ...." +
                    "Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>");
        } catch (IOException e){
            System.err.println(e.toString());
        }
    }
    
    /**
     * Sends a response of a basic type.
     * 
     * @param statusCode
     * @param responseString
     * @throws Exception 
     */
    public void sendResponse (int statusCode, String responseString) throws IOException  {
        
        String statusLine;
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";
        
        if (statusCode == 200)
            statusLine = "HTTP/1.1 200 OK" + "\r\n";
        else
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

        responseString = myHTTPServer.HTML_START + responseString + myHTTPServer.HTML_END;
        contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
        
        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");
        outToClient.writeBytes(responseString);
        
        outToClient.close();
    }
    
    /**
     * Sends a file.
     * 
     * @param statusCode
     * @param responseString
     * @throws Exception 
     */
    public void sendResponse (int statusCode, File responseContent) throws IOException {
        
        String statusLine;
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine;
        String contentTypeLine = "Content-Type: \r\n";
         
        FileInputStream fin = new FileInputStream(responseContent);
        
        if (statusCode == 200)
            statusLine = "HTTP/1.1 200 OK" + "\r\n";
        else
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

        contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
        if (responseContent.getName().endsWith(".htm") || responseContent.getName().endsWith(".html"))
            contentTypeLine = "Content-Type: text/html" + "\r\n";
        else if (responseContent.getName().endsWith(".js"))
            contentTypeLine = "Content-Type: text/javascript" + "\r\n";
        
        System.out.println(contentTypeLine);
        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");
        
        byte[] buffer = new byte[1024] ;
        int bytesRead;
        
        while ((bytesRead = fin.read(buffer)) != -1 ) {
            outToClient.write(buffer, 0, bytesRead);
        }
        fin.close();
                
        outToClient.close();
    }
    
    /**
     * Sends an object in json format.
     * 
     * @param statusCode
     * @param responseObj
     * @throws Exception 
     */
    public void sendResponse (int statusCode, Object responseObj) throws IOException {
        
        String statusLine;
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine;
        String contentTypeLine = "Content-Type: application/json; charset=utf-8" + "\r\n";
        
        if (statusCode == 200)
            statusLine = "HTTP/1.1 200 OK" + "\r\n";
        else
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
            
        Gson gson = new Gson();
        String json = gson.toJson(responseObj);
        contentLengthLine = "Content-Length: " + json.length() + "\r\n";

        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");
        outToClient.writeBytes(json);
        
        outToClient.close();
    }
}
