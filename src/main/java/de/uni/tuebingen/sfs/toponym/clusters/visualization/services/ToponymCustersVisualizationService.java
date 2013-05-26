package de.uni.tuebingen.sfs.toponym.clusters.visualization.services;

import com.google.common.collect.ImmutableSet;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.resources.*;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@ApplicationPath("visualization")
public class ToponymCustersVisualizationService extends Application {
    private ImmutableSet<Object> singletons = ImmutableSet.of(new ToponymClustersVisualizationREST(), new ToponymFacadeREST());
    
    public Set<Object> getSingletons (){
        return singletons;
    }
}
