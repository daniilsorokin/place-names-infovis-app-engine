package de.uni.tuebingen.sfs.toponym.clusters.visualization.services;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.resources.ToponymClustersVisualization;
import java.util.Collections;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@ApplicationPath("visualization")
public class ToponymCustersVisualizationService extends Application {
    private Set<Object> singletons = Collections.<Object>singleton(new ToponymClustersVisualization());

    public Set<Object> getSingletons (){
        return singletons;
    }
}
