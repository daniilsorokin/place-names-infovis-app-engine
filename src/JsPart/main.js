/** Main map. */
var myMap; 


var DataSources = [{name:"Ingria area", fileName: "toponyms_Ingria.txt", startPoint: new google.maps.LatLng(59.4, 29.13333), startZoom: 8}]


/**
 * Main function which should be called after the loading of the page.
 */
function initialize()
{	
    var myOptions = {
        zoom:4, 
        center: new google.maps.LatLng(59.4, 29.13333),
        streetViewControl: false,
        scaleControl: true,
        overviewMapControl: true,					
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };
    
    myMap = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    myMap.setCenter(DataSources[0].startPoint);
    myMap.setZoom(DataSources[0].startZoom);
    
    $( "#group_list" ).selectable();
    
    var groups = ["test1", "test2", "test3"];
    var $groupsList = $("#group_list");
    for (gIdx in groups)
    {
        $groupsList.append("<li class=\"ui-widget-content\">" + groups[gIdx] + "</li>");
    }
    
}
