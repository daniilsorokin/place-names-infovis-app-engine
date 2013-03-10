/** Main map. */
var myMap; 

var $toponymsList;

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
    
    myMap = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    myMap.setCenter(DataSources[0].startPoint);
    myMap.setZoom(DataSources[0].startZoom);
    
    $toponymsList = $("#toponyms-list");
    $groupsList = $("#groups-list");
    $groupsList.hide();
    $(".list").selectable();
    
    $toponymsList.on( "selectableselected", function( event, ui ) {
        $.getJSON("getCoordinates", {id: ui.selected.id}, function(coordinates) {
            placeNewMarker(ui.selected.id, coordinates);
        });
    } );
    $toponymsList.on( "selectableunselected", function( event, ui ) {
        if (markers[ui.unselected.id] != null) markers[ui.unselected.id].setMap(null);
        markers[ui.unselected.id] = null;
    } );
   
    $.getJSON("get-toponyms-names", {} ,function(json) {
        $toponymsList.fillWith(json);
        $("#list-toponyms").button("enable");
    });
    
    $.getJSON("get-groups-names", {} ,function(json) {
        $groupsList.fillWith(json);
        $("#list-groups").button("enable");
    });

    $("#list-selector").buttonset();
    $("#list-selector span.ui-button-text").addClass("list-selector-button-text");
    $("#list-toponyms").button("disable").click(function (){
        $groupsList.hide('slide', { direction: "right" });
        $toponymsList.show('slide',{ direction: "left" });
    });
    $("#list-groups").button("disable").click(function (){
        $toponymsList.hide('slide', { direction: "left" });
        $groupsList.show('slide',{ direction: "right" });
    });
    
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


(function( $ ) {
    $.fn.fillWith = function(data){
        for (idx in data)
        {
            this.append("<li id =\"" + idx + "\" class=\"ui-widget-content\">" + data[idx] + "</li>");
        }
    };
})(jQuery);
