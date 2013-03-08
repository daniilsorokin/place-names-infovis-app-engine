package ServerPart;

import ProcessPart.Dataset;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
public class DBServlet extends HttpServlet {
    
    private Connection connection;
    
    public void init(ServletConfig servletConfig) throws ServletException{
        String datasetFile = servletConfig.getInitParameter("datasetFile");
        try {
            // Initialize class
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite::resource:" + datasetFile);
        } catch (SQLException ex) {
            Logger.getLogger(DBServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Object returnData = null;
        String what = request.getParameter("what");
        if (what == null){
            return;
        }
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if (what.equals("list")) {
                ResultSet rs = statement.executeQuery("select * from kingiseppD");
                ArrayList<String> rarray = new ArrayList<String>();
                while(rs.next())
                {
                    rarray.add(rs.getString("name"));
                }
                returnData = rarray;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Gson gson = new Gson();
        String json = gson.toJson(returnData);
        PrintWriter out = response.getWriter();
        try {
            out.write(json);
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
