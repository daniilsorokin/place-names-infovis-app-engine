package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Path("storage")
public class StorageApp {
    
    public StorageApp() {
    }
    
    @GET
    @Produces({"application/xml", "application/json"})
    public String getGreeting(@Context HttpServletResponse currentResponse, @Context UriInfo uriInfo) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        
        if (currentUser != null) {
            return "Hello, " + currentUser.getNickname();
        } else {
            currentResponse.sendRedirect(userService.createLoginURL(uriInfo.getRequestUri().toString()));
            return "";
        }
    }
    
}
