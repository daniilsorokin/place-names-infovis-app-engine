/** Main map. */
var myMap; 

var $toponymsList;

var markers = {};

var DataSources = [{name:"Ingria area", fileName: "toponyms_Ingria.txt", startPoint: new google.maps.LatLng(59.4, 29.13333), startZoom: 8}];

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
    $.ajaxSetup({ scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"});
    
    myMap = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    myMap.setCenter(DataSources[0].startPoint);
    myMap.setZoom(DataSources[0].startZoom);
    
    $toponymsList = $("#toponyms-list");
    $groupsList = $("#groups-list");
    $groupsList.hide();
    $(".list").selectable();
    
    $toponymsList.on( "selectablestop", function( event, ui ) {
//        $(".ui-selected", $groupsList).each(function() {
//             $(this).removeClass("ui-selected");
//        });  
        var ids = new Array();
        $(".ui-selected" , this).each(function() {
             ids.push($(this).attr('id'));
        });  
        $.getJSON("getToponyms", {id: ids}, function(toponymsList) {
            for(var toponymIdx in toponymsList){
                var toponym = toponymsList[toponymIdx];
                placeNewMarker(toponym.id, {lat: toponym.latitude, lng: toponym.longitude});
            }
        });
    } );
    $toponymsList.on( "selectableunselected", function( event, ui ) {
        if (markers[ui.unselected.id] != null) markers[ui.unselected.id].setMap(null);
        markers[ui.unselected.id] = null;
    } );
    
    $groupsList.on( "selectablestop", function( event, ui ) {
        //$(".ui-selected", $toponymsList).removeClass("ui-selected");
//        console.log($(".ui-selected", $toponymsList));
        $(".ui-selected" , this).each(function() {
            $.ajax({url: "getToponyms", contentType: "application/x-www-form-urlencoded", dataType: "json", type: "POST",
                data: {group_name: $(this).attr("id")},
                success: function(toponymsList) {
                    for(var toponymIdx in toponymsList){
                        var toponym = toponymsList[toponymIdx];
                        placeNewMarker(toponym.id, {lat: toponym.latitude, lng: toponym.longitude});
                        $("#"+toponym.id, $toponymsList).addClass("ui-selected");
                    }
                }
            });
        });        
    });
    $groupsList.on( "selectableunselected", function( event, ui ) {
        $.ajax({url: "getToponyms", contentType: "application/x-www-form-urlencoded", dataType: "json", type: "POST",
                data: {group_name: ui.unselected.id},
                success: function(toponymsList) {
                    for(var toponymIdx in toponymsList){
                        var toponym = toponymsList[toponymIdx];
                        markers[toponym.id].setMap(null);
                        markers[toponym.id] = null;
                        $("#"+toponym.id, $toponymsList).removeClass("ui-selected");
                    }
                }
            });
    });
   
    $.getJSON("get-toponyms-names", {} ,function(namesList) {
        for (nameIdx in namesList) {
            var nameObj = namesList[nameIdx];
            $toponymsList.append("<li id =\"" + nameObj.first + "\" class=\"ui-widget-content\">" + nameObj.second + "</li>");
        }
        $("#list-toponyms").button("enable");
    });
    
    $.getJSON("get-groups-names", {} ,function(groupsList) {
        for (nameIdx in groupsList) {
            var groupName = groupsList[nameIdx];
            $groupsList.append("<li id =\"" + groupName + "\" class=\"ui-widget-content\">" + groupName + "</li>");
        }
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
        var latlng = new google.maps.LatLng(coordinates.lat, coordinates.lng);
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
