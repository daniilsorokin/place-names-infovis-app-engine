package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Path("storage")
public class StorageApp {
    
    public StorageApp() {
    }
    
    @GET
    public Object getGreeting(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, URISyntaxException {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return "Hello, " + currentUser.getNickname();
        } else {
            return Response.temporaryRedirect(new URI(
                    userService.createLoginURL(
                            req.getRequestURI()
                    ))).build();
        }
    }
    
}
