package ServerPart;

import DatabaseAccess.KingiseppDistrict;
import baseclasses.Tuple;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Double;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@student.uni-tuebingen.de>
 */
public class GetToponymsCoordinatesServlet extends HttpServlet {
    private EntityManager em;

    /**
     * Initializes the Servlet.
     * 
     * @param servletConfig
     * @throws ServletException 
     */
    public void init(ServletConfig servletConfig) throws ServletException {
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Object returnData = null;
        
        if (request.getParameter("id[]") != null){
            String[] indices = request.getParameterValues("id[]");
            List coordinates = em.createNamedQuery("KingiseppDistrict.findLatLngByIds")
                    .setParameter("ids", Arrays.asList(indices))
                    .getResultList();
            returnData = coordinates;
        } else if (request.getParameter("group_name") != null){
            List coordinates = em.createNamedQuery("KingiseppDistrict.findLatLngByGroupName")
                    .setParameter("groupName", request.getParameter("group_name"))
                    .getResultList();
            returnData = coordinates;
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
    }// </editor-fold>
}
