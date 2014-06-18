<%@page import="de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Toponym_"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="javax.persistence.*, javax.persistence.criteria.*, de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Toponym, java.util.List"%>
<% Class.forName("org.postgresql.Driver");
   EntityManager em = Persistence.createEntityManagerFactory("de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU").createEntityManager(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Toponym clusters visualization</title>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCu0d6lpgFSzznwCppY2HEoca4y1AM0Cow&v=3.exp&sensor=false"></script>
        <script type="text/javascript" src="javascript/main.js"></script>
        <script type="text/javascript" src="javascript/colors.js"></script>
        <script type="text/javascript">
            $( document ).ready( function() {
                initialize();
            });
        </script>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
        <link rel="stylesheet" href="viswebproject.css" />
    </head>
    <body>        
        <table id="page-structure" class="page-structure">
            <tr><td id="list-selector-container" style="width: 20%;">
                    <div id="list-selector"> 
                        <input type="radio" id="list-toponyms" name="radio" checked="checked"/>
                        <label for="list-toponyms" class="list-selector-button">Toponyms</label>
                        <input type="radio" id="list-groups" name="radio"/>
                        <label for="list-groups" class="list-selector-button">Groups</label>
                    </div>
                </td>
                <td style="width:80%;" rowspan="2">
                    <div id="map_canvas" style="width:100%; height:100%; margin:0px 0px; padding:0px;"></div>
                </td>
            </tr>
            <tr><td>
                    <div class="mousescroll">
                        <button id="deselect-button">Deselect all</button>
                        <ul id="toponyms-list" class="list">
                        <% 
                            List<Toponym> allToponyms = em.createNamedQuery("Toponym.findAll").getResultList();
                            for (int i = 0; i < allToponyms.size(); i++) {
                                Toponym t = allToponyms.get(i);
                        %>  
                        <li id ="<%=t.getId() %>" class="ui-widget-content"><%=t.getName() %></li>
                        <%
                            } 
                        %>
                        </ul>
                        <ul id="groups-list" class="list">
                        <% 
                            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                            Root<Toponym> toponymRoot = cq.from(Toponym.class);
                            cq.groupBy(toponymRoot.get(Toponym_.groupName));
                            cq.select(toponymRoot.get(Toponym_.groupName));
                            List<String> groups = em.createQuery(cq).getResultList();
                            for (int i = 0; i < groups.size(); i++) {
                                String g = groups.get(i);
                        %>  
                            <li id ="<%=g %>" class="ui-widget-content"><%=g %></li>
                        <%
                            } 
                        %>
                        </ul>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>
