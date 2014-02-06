VIZAPP.databases = function () {
    return [{name:"Ingria area", fileName: "toponyms_Ingria.txt", startPoint: new google.maps.LatLng(59.4, 29.13333), startZoom: 8}];
}();

VIZAPP.dummies = function() {
    return {
        toponymObjects: [{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Zakhonye-1","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.43333","longitude":"28.41667","name":"Захонье-1","toponymNo":"46","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Zakhonye-2","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"58.96833","longitude":"29.36917","name":"Захонье-2","toponymNo":"47","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Izvoz","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.43333","longitude":"28.3","name":"Извоз","toponymNo":"49","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Kommunar","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.62056","longitude":"30.39","name":"Коммунар","toponymNo":"65","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Alekseyevka","formant":{"formantName":"вка","formantNo":"3"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.43","longitude":"28.76","name":"Алексеевка","toponymNo":"3","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Arbolova","formant":{"formantName":"олово","formantNo":"4"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.63","longitude":"28.93","name":"Арболово","toponymNo":"4","type":{"name":"town","nameRus":"город","typeNo":"2"}}, {"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Zakhonye-1","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.43333","longitude":"28.41667","name":"Захонье-1","toponymNo":"46","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Zakhonye-2","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"58.96833","longitude":"29.36917","name":"Захонье-2","toponymNo":"47","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Izvoz","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.43333","longitude":"28.3","name":"Извоз","toponymNo":"49","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Kommunar","formant":{"formantName":"no formant","formantNo":"1"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.62056","longitude":"30.39","name":"Коммунар","toponymNo":"65","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Alekseyevka","formant":{"formantName":"вка","formantNo":"3"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.43","longitude":"28.76","name":"Алексеевка","toponymNo":"3","type":{"name":"town","nameRus":"город","typeNo":"2"}},{"dataset":{"datasetNo":"1","name":"no dataset"},"englishTransliteration":"Arbolova","formant":{"formantName":"олово","formantNo":"4"},"language":{"languageNo":"1","name":"unknown","nameRus":"неизвестный"},"latitude":"59.63","longitude":"28.93","name":"Арболово","toponymNo":"4","type":{"name":"town","nameRus":"город","typeNo":"2"}}],
        formants: [{"formantName":"no formant","formantNo":"1"},{"formantName":"ая","formantNo":"2"},{"formantName":"вка","formantNo":"3"},{"formantName":"олово","formantNo":"4"},{"formantName":"ино","formantNo":"5"}],
    };
}();

VIZAPP.dataInterface = function () {
    var type = "real"; 
    
    return {
        getAllToponyms: function (doWithToponyms){
            if (type == "dummy") {
                doWithToponyms(VIZAPP.dummies.toponymObjects);
            } else if (type == "real") {
                $.getJSON("request/toponymobject/", {}, function(data) {
                    doWithToponyms(data.toponymObject);
                });
            }
        },
        getAllFormants: function (doWithFormants) {
            if (type == "dummy") {
                doWithFormants(VIZAPP.dummies.formants);
            } else if (type == "real") {
                $.getJSON("request/formant/", {}, function(data) {
                    doWithFormants(data.formant);
                });
            }
        },
        getToponymsSet: function(ids, doWithResults) {
            $.getJSON("request/toponymobject/set", {id: ids}, function(data) {
                var toponyms = data.toponymObject;
                if (!(toponyms instanceof Array)){
                    toponyms = [toponyms]
                }
                doWithResults(toponyms);
            });
        },
        getToponym: function(id, doWithResults) {
            $.getJSON("request/toponymobject/"+id, function(data) {
                doWithResults(data);
            });
        },        
        getToponymsByFormant: function(id, doWithResults) {
            $.getJSON("request/formant/" + id + "/toponyms", function(data) {
                var toponyms = data.toponymObject;
                if (!(toponyms instanceof Array)){
                    toponyms = [toponyms]
                }
                doWithResults(toponyms);
            });
        }
        
    };
}();

VIZAPP.myMap = function () {
    var map;
    var mapOptions = { 
        zoom: 4, 
        center: new google.maps.LatLng(59.4, 29.13333),
        streetViewControl: false,
        mapTypeControl: false,
        scaleControl: true,
        overviewMapControl: true,					
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };
    
    var markers = {}
    
    return {
        init: function() {
            google.maps.visualRefresh = true;
            map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
            map.setCenter(VIZAPP.databases[0].startPoint);
            map.setZoom(VIZAPP.databases[0].startZoom);
        },
        placeMarker: function(toponym) {
            if (markers[toponym.toponymNo] === undefined) {
                var opacity = 1.0;
                var radius = 1000; 
                var latlng = new google.maps.LatLng(toponym.latitude, toponym.longitude);
                var circleOptions = {
                    strokeWeight: 0,
                    fillColor: "#0066CC",
                    fillOpacity: opacity,
                    map: map,
                    center: latlng,
    //                title: title,
                    radius: radius
                };
                var marker = new google.maps.Circle(circleOptions);
                markers[toponym.toponymNo] = marker;
            }
            markers[toponym.toponymNo].setMap(map);
        },
        hideMarker: function (toponym) {
            markers[toponym.toponymNo].setMap(null)
        }
    };
}();

VIZAPP.gui = function () {
    
    var $activeList = undefined;
    
    var deselectAllInActiveList = function() {
        $(".ui-selected", $activeList).each(function(){
            $(this).removeClass("ui-selected");
            $(this).trigger("selectableunselected", {unselected: this});
        });
    };
    
    var selectToponym = function(toponym) {        
        var groupName = toponym.formant.formantName;
        VIZAPP.myMap.placeMarker(toponym);
//                        if (toponymIdToGroupName[groupName] == null)
//                            toponymIdToGroupName[toponym.toponymNo] = groupName;
//                        if (groupNameToColor[groupName] == null)
//                            groupNameToColor[groupName] = colorGeneratorInstance.generateNextColor();
//                        $("#"+toponym.toponymNo, $toponymsList).css({ background: groupNameToColor[groupName] });
    };
    
    var selectFormant = function(formant) {
//                    if (groupNameToColor[groupName] == null)
//                        groupNameToColor[groupName] = colorGeneratorInstance.generateNextColor();
//                    $(this).css({ background: groupNameToColor[groupName] });
                    VIZAPP.dataInterface.getToponymsByFormant(formant.id, function(toponyms) {
                        for(var idx in toponyms) {
                            selectToponym(toponyms[idx]);
                            $("#"+toponyms[idx].toponymNo, $("#toponyms-list")).addClass("ui-selected");
//                                                            .css({ background: groupNameToColor[groupName] });
                        }
                    });
//                    $.ajax({url: "getPolygons", contentType: "application/x-www-form-urlencoded", dataType: "json", type: "POST",
//                        data: {group_name: groupName},
//                        success: function(polygonsCoordinatesList) {
//                            placeNewPolygon(groupName, polygonsCoordinatesList, groupNameToColor[groupName]);
//                        }
//                    });        
    };

    var deselectToponym = function(toponym) {
//                $(".ui-selected", $groupsList).each(function(){
//                                        //It is not the best solution, but we can't for now put into jquery because of some strange group names.
//                                        if ($(this).attr("id") == toponymIdToGroupName[ui.unselected.id]){
//                                            $(this).removeClass("ui-selected")
//                                                   .css({ background: "#FFFFFF" });
//                                          if (groupIdToPolygon[$(this).attr("id")] != null)
//                                               groupIdToPolygon[$(this).attr("id")].setMap(null);
//                                          groupIdToPolygon[$(this).attr("id")] = null;
//                                        }
//                                      });
                                      
//                $(ui.unselected).removeClass("ui-selected");
                $("#"+ toponym.formant.formantNo, $("#groups-list")).removeClass("ui-selected");
//                                                   .css({ background: "#FFFFFF" });
                VIZAPP.myMap.hideMarker(toponym);
    };
    
    var deselectFormant = function(formant) {
//                $(ui.unselected).removeClass("ui-selected").css({ background: "#FFFFFF" });
//                if (groupIdToPolygon[ui.unselected.id] != null)
//                     groupIdToPolygon[ui.unselected.id].setMap(null);
//                groupIdToPolygon[ui.unselected.id] = null;
//                $(ui.unselected).removeClass("ui-selected");
                
                VIZAPP.dataInterface.getToponymsByFormant(formant.id, function(toponyms) {
                    for(var idx in toponyms) {
                        $("#"+toponyms[idx].toponymNo, $("#toponyms-list")).removeClass("ui-selected");
                        VIZAPP.myMap.hideMarker(toponyms[idx]);
                    }
                });
    };
    
    return {
        init: function () {
            VIZAPP.myMap.init();

            var $toponymsList = $("#toponyms-list");
            var $groupsList = $("#groups-list");
            
            $(".list").selectable();
            $("#toponyms-list-container").show();
            $("#groups-list-container").hide();
            $activeList = $toponymsList;
            $("#deselect-button").button().click(deselectAllInActiveList);
            $("#deselect-button").prop("disabled", false);
            
            $("#select-toponyms-btn").click(function (){
                $activeList = $toponymsList;
                $("#toponyms-list-container").show('slide',{ direction: "left" });
                $("#groups-list-container").hide('slide', { direction: "right" });
                $(".nano").nanoScroller();
            });
            $("#select-groups-btn").click(function (){
                $activeList = $groupsList;
                $("#groups-list-container").show('slide',{ direction: "right" });
                $("#toponyms-list-container").hide('slide', { direction: "left" });
                $(".nano").nanoScroller();
            });
            
            VIZAPP.dataInterface.getAllToponyms(function(loadedToponymObjects){
                for (var i in loadedToponymObjects) {
                    var toponym = loadedToponymObjects[i];
                    $("<li>").attr("id", toponym.toponymNo)
                             .data("formant-id", toponym.formant.formantNo)
                             .addClass("ui-widget-content")
                             .html(toponym.name)
                             .appendTo($toponymsList);
                }
                $("#select-toponyms-btn").prop("disabled", false);
                $(".nano").nanoScroller();
            });
            
            VIZAPP.dataInterface.getAllFormants(function(loadedFormants) {
                for (var i in loadedFormants) {
                    var formant = loadedFormants[i];
                    $("<li>").attr("id", formant.formantNo)
                             .addClass("ui-widget-content")
                             .html(formant.formantName)
                             .appendTo($groupsList);
                }
                $("#select-groups-btn").prop("disabled", false);
                $(".nano").nanoScroller();
            });
            
            $toponymsList.on( "selectablestop", function( event, ui ) {
                var ids = new Array();
                $(".ui-selected" , this).each(function() { ids.push($(this).attr('id')); });
                VIZAPP.dataInterface.getToponymsSet(ids, function(toponyms) {
                    for(var idx in toponyms) {
                        selectToponym(toponyms[idx]);
                    }
                });
            });
            
            $groupsList.on( "selectablestop", function( event, ui ) {
                $(".ui-selected" , this).each(function() {
                    selectFormant({id:this.id});
                });        
            });            
            
            $toponymsList.on( "selectableunselected", function( event, ui ) {
                deselectToponym({formant: {formantNo: $(ui.unselected).data("formant-id")}, toponymNo: ui.unselected.id});
            } );
            
            $groupsList.on( "selectableunselected", function( event, ui ) {
                deselectFormant({id:ui.unselected.id});
            });                        
        }
    };
}();


(function( $ ) {
    /**
     * 
     * @returns {Boolean}
     */
    $.fn.isHidden = function(){
        return this.css('display') == 'none';
    };
})(jQuery);