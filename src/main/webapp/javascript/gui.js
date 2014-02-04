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
        getSet: function(ids, doWithResults) {
            $.getJSON("request/toponymobject/set", {id: ids}, function(data) {
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
    
    return {
        init: function () {
            VIZAPP.myMap.init();
            
            var $toponymsList = $("#toponyms-list");
            var $groupsList = $("#groups-list");
            
            $(".list").selectable();
            
            $toponymsList.show();
            $groupsList.hide();
            
            $("#select-toponyms-btn").click(function (){
                $groupsList.hide('slide', { direction: "right" });
                $toponymsList.show('slide',{ direction: "left" });
            });
            $("#select-groups-btn").click(function (){
                $toponymsList.hide('slide', { direction: "left" });
                $groupsList.show('slide',{ direction: "right" });
            });
            
            VIZAPP.dataInterface.getAllToponyms(function(loadedToponymObjects){
                for (var i in loadedToponymObjects) {
                    var toponym = loadedToponymObjects[i];
                    $toponymsList.append("<li id =\"" + toponym.toponymNo + "\" class=\"ui-widget-content\">" + toponym.name + "</li>");
                }
                $("#select-toponyms-btn").removeAttr("disabled");
                $(".nano").nanoScroller();
            });
            
            VIZAPP.dataInterface.getAllFormants(function(loadedFormants) {
                for (var i in loadedFormants) {
                    var formant = loadedFormants[i];
                    $groupsList.append("<li id =\"" + formant.formantNo + "\" class=\"ui-widget-content\">" + formant.formantName + "</li>");
                }
                $("#select-groups-btn").removeAttr("disabled");
                $(".nano").nanoScroller();
            });
            
            $toponymsList.on( "selectablestop", function( event, ui ) {
                var ids = new Array();
                $(".ui-selected" , this).each(function() { ids.push($(this).attr('id')); });
                VIZAPP.dataInterface.getSet(ids, function(toponyms) {
                    for(var idx in toponyms) {
                        var toponym = toponyms[idx];
                        var groupName = toponym.formant.formantName;
//                        if (toponymIdToGroupName[groupName] == null)
//                            toponymIdToGroupName[toponym.toponymNo] = groupName;
//                        if (groupNameToColor[groupName] == null)
//                            groupNameToColor[groupName] = colorGeneratorInstance.generateNextColor();
//                        $("#"+toponym.toponymNo, $toponymsList).css({ background: groupNameToColor[groupName] });
                        VIZAPP.myMap.placeMarker(toponym);
                    }
                });
            });

        }
    };
}();