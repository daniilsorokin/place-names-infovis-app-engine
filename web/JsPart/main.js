/** Main map. */
var myMap; 

var $groupsList;

var markers = {};

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
    //$.ajaxSetup({ scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8", type:"GET"});
    
    myMap = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    myMap.setCenter(DataSources[0].startPoint);
    myMap.setZoom(DataSources[0].startZoom);
    
    $groupsList = $("#group_list");
    $( "#group_list" ).selectable();
    $( "#group_list" ).on( "selectableselected", function( event, ui ) {
        $.getJSON("getData", {what: "coordinates", id: ui.selected.id}, function(coordinates) {
            placeNewMarker(ui.selected.id, coordinates);
        });
    } );
    $( "#group_list" ).on( "selectableunselected", function( event, ui ) {
        markers[ui.unselected.id].setMap(null);
        markers[ui.unselected.id] = null;
    } );

    
    $.getJSON("getData", {what: "list"} ,function(json) {
        console.log("Dataset size:" + json.length);
        fillTheList(json);
    }).fail(function(jqXHR, textStatus, error) {
        console.log(textStatus);
        console.log(error);
        console.log(jqXHR);
    });
}

function fillTheList (data){
    for (idx in data)
    {
        $groupsList.append("<li id =\"" + idx + "\" class=\"ui-widget-content\">" + data[idx] + "</li>");
    }
}

function placeNewMarker(id, coordinates){
    if (markers[id] == null){
        var latlng = new google.maps.LatLng(coordinates.first, coordinates.second);
        var marker = new google.maps.Marker({
            position: latlng,
            map: myMap,
            title:"Hello World!"
        });
        //To Do: Maybe another way to store markers. I am not sure about Javascript handles the memory.
        markers[id] = marker;
    }
}
