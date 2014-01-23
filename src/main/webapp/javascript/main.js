var VIZAPP = new Object();


var toponymIdToMarker = {};
var groupIdToPolygon = {};
var groupNameToColor = {};
var toponymIdToGroupName = {};

VIZAPP.initialize = function()
{	    
    $.ajaxSetup({ scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"});
    VIZAPP.gui.init();
    VIZAPP.dataInterface.load();
//    colorGeneratorInstance = new colorGenerator();
    
//    $toponymsList.on( "selectablestop", function( event, ui ) {
//        var ids = new Array();
//        $(".ui-selected" , this).each(function() {
//            ids.push($(this).attr('id'));
//        });  
//        $.getJSON("visualization/toponymobject/set", {id: ids}, function(data) {
//            var toponyms = data.toponymObject;
//            if (!(toponyms instanceof Array)){
//                toponyms = [toponyms]
//            }
//            for(var toponymIdx in toponyms){
//                var toponym = toponyms[toponymIdx];
//                var groupName = toponym.formant.formantName;
//                if (toponymIdToGroupName[groupName] == null)
//                    toponymIdToGroupName[toponym.toponymNo] = groupName;
//                if (groupNameToColor[groupName] == null)
//                    groupNameToColor[groupName] = colorGeneratorInstance.generateNextColor();
//                $("#"+toponym.toponymNo, $toponymsList).css({ background: groupNameToColor[groupName] });
//                placeNewMarker(toponym.toponymNo, {lat: toponym.latitude, lng: toponym.longitude}, 
//                                groupNameToColor[groupName], toponym.name);
//            }
//        });
//    });
//    
//    $toponymsList.on( "selectableunselected", function( event, ui ) {
//        $(".ui-selected", $groupsList).each(function(){
//                                        //It is not the best solution, but we can't for now put into jquery because of some strange group names.
//                                        if ($(this).attr("id") == toponymIdToGroupName[ui.unselected.id]){
//                                            $(this).removeClass("ui-selected")
//                                                   .css({ background: "#FFFFFF" });
//                                          if (groupIdToPolygon[$(this).attr("id")] != null)
//                                               groupIdToPolygon[$(this).attr("id")].setMap(null);
//                                          groupIdToPolygon[$(this).attr("id")] = null;
//                                        }
//                                      });
//                                      
//        $(ui.unselected).removeClass("ui-selected").css({ background: "#FFFFFF" });
//        if (toponymIdToMarker[ui.unselected.id] != null) toponymIdToMarker[ui.unselected.id].setMap(null);
//        toponymIdToMarker[ui.unselected.id] = null;
//    } );
//    
//    $groupsList.on( "selectablestop", function( event, ui ) {
//        //$(".ui-selected", $toponymsList).removeClass("ui-selected");
//        $(".ui-selected" , this).each(function() {
//            var groupName = $(this).attr("id");
//            if (groupNameToColor[groupName] == null)
//                groupNameToColor[groupName] = colorGeneratorInstance.generateNextColor();
//            $(this).css({ background: groupNameToColor[groupName] });
//            
//            $.ajax({url: "getToponyms", contentType: "application/x-www-form-urlencoded", dataType: "json", type: "POST",
//                data: {group_name: groupName},
//                success: function(toponymsList) {
//                    for(var toponymIdx in toponymsList){
//                        var toponym = toponymsList[toponymIdx];
//                        placeNewMarker(toponym.id, {lat: toponym.latitude, lng: toponym.longitude}, 
//                                        groupNameToColor[groupName], toponym.name);
//                        $("#"+toponym.id, $toponymsList).addClass("ui-selected")
//                                                        .css({ background: groupNameToColor[groupName] });
//                    }
//                }
//            });
//            $.ajax({url: "getPolygons", contentType: "application/x-www-form-urlencoded", dataType: "json", type: "POST",
//                data: {group_name: groupName},
//                success: function(polygonsCoordinatesList) {
//                    placeNewPolygon(groupName, polygonsCoordinatesList, groupNameToColor[groupName]);
//                }
//            });
//        });        
//    });
//    
//    $groupsList.on( "selectableunselected", function( event, ui ) {
//        $(ui.unselected).removeClass("ui-selected").css({ background: "#FFFFFF" });
//        if (groupIdToPolygon[ui.unselected.id] != null)
//             groupIdToPolygon[ui.unselected.id].setMap(null);
//        groupIdToPolygon[ui.unselected.id] = null;
//        $.ajax({url: "getToponyms", contentType: "application/x-www-form-urlencoded", dataType: "json", type: "POST",
//                data: {group_name: ui.unselected.id},
//                success: function(toponymsList) {
//                    for(var toponymIdx in toponymsList){
//                        var toponym = toponymsList[toponymIdx];
//                        if (toponymIdToMarker[toponym.id] != null) toponymIdToMarker[toponym.id].setMap(null);
//                        toponymIdToMarker[toponym.id] = null;
//                        $("#"+toponym.id, $toponymsList).removeClass("ui-selected")
//                                                        .css({ background: "#FFFFFF" });
//                    }
//                }
//            });
//    });

//    
//    $("#deselect-button").button()
//                          .click(function(){
////                                $toponymsList.trigger("click");
//                                if (!$toponymsList.isHidden()) 
//                                    $(".ui-selected", $toponymsList).each(function(){
//                                        $(this).trigger("selectableunselected", {unselected: this});
//                                    });
//                                else if (!$groupsList.isHidden())
//                                    $(".ui-selected", $groupsList).each(function(){
//                                        $(this).trigger("selectableunselected", {unselected: this});
//                                    });
//                          });
//    $("#deselect-button span.ui-button-text").addClass("custom-button-text");
}

function placeNewMarker(id, coordinates, title){
    if (toponymIdToMarker[id] == null){
        var latlng = new google.maps.LatLng(coordinates.lat, coordinates.lng);
        var marker = new google.maps.Marker({
            position: latlng,
            map: myMap,
            title: title
        });
        //To Do: Maybe another way to store markers. I am not sure about Javascript handles the memory.
        toponymIdToMarker[id] = marker;
    }
}

/**
 * Places a marker on the map and stores it for later reference.
 * 
 * @param {int} id marker's id.
 * @param {lat: latitude, lng: longitude} coordinates
 * @param {type} color
 * @param {string} title
 */
function placeNewMarker (id, coordinates, color, title){
    if (toponymIdToMarker[id] == null){
        var opacity = 1.0;
        var radius = 1000; 
        var latlng = new google.maps.LatLng(coordinates.lat, coordinates.lng);
        var circleOptions = {
            strokeWeight: 0,
            fillColor: color,
            fillOpacity: opacity,
            map: myMap,
            center: latlng,
            title: title,
            radius: radius
        };
        circle = new google.maps.Circle(circleOptions);
        toponymIdToMarker[id] = circle;
    }  
}

/**
 * Places a new polygon on a map and stores it for later reference.
 * 
 * @param {type} groupName
 * @param {type} polygonsCoordinatesList
 * @param {type} color
 * @returns {undefined}
 */
function placeNewPolygon (groupName, polygonsCoordinatesList, color){
    if (groupIdToPolygon[groupName] == null){
        for (var pc in polygonsCoordinatesList){
            var polygonCoordinates = polygonsCoordinatesList[pc];
            for (var p in polygonCoordinates){
                polygonCoordinates[p] = new google.maps.LatLng(polygonCoordinates[p].first, polygonCoordinates[p].second);
            }
        }
        var polygonOptions = {
            paths: polygonsCoordinatesList,
                    clickable: false,
                    strokeColor: color,
                    strokeOpacity: 0.5,
                    strokeWeight: 2,
                    fillColor: color,
                    map: myMap,
                    fillOpacity: 0.2};
        var polygon = new google.maps.Polygon(polygonOptions);
        groupIdToPolygon[groupName] = polygon;
    }
}


// Additional JQuery obejct methods
(function( $ ) {
    /**
     * 
     * @param {type} data
     */
    $.fn.fillWith = function(data){
        for (idx in data)
        {
            this.append("<li id =\"" + idx + "\" class=\"ui-widget-content\">" + data[idx] + "</li>");
        }
    };
    
    /**
     * 
     * @returns {Boolean}
     */
    $.fn.isHidden = function(){
        return this.css('display') == 'none';
    };
})(jQuery);
