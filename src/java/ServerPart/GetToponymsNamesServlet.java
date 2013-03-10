package ServerPart;

import DatabaseAccess.KingiseppDistrict;
import baseclasses.Tuple;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
public class GetToponymsNamesServlet extends HttpServlet {

    private EntityManager em;

    public void init(ServletConfig servletConfig) throws ServletException {
//        String datasetFile = servletConfig.getInitParameter("datasetFile");
        try {
            // Initialize class
            Class.forName("org.postgresql.Driver");
            // Create an Entity manager
            em = Persistence.createEntityManagerFactory("VisWebProjectPU").createEntityManager();
        }  catch (ClassNotFoundException ex) {
            Logger.getLogger(GetToponymsNamesServlet.class.getName()).log(Level.SEVERE, null, ex);
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

        List<KingiseppDistrict> kingDistr = em.createNamedQuery("KingiseppDistrict.findAll").getResultList();

        ArrayList<String> rarray = new ArrayList<String>();
        for (KingiseppDistrict k : kingDistr) {
            rarray.add(k.getName());
        }

        Gson gson = new Gson();
        String json = gson.toJson(rarray);
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
