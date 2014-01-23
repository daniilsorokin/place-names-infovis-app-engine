VIZAPP.databases = function () {
    return [{name:"Ingria area", fileName: "toponyms_Ingria.txt", startPoint: new google.maps.LatLng(59.4, 29.13333), startZoom: 8}];
}();


VIZAPP.gui = function () {
    var mapOptions = { 
        zoom:4, 
        center: new google.maps.LatLng(59.4, 29.13333),
        streetViewControl: false,
        mapTypeControl: false,
        scaleControl: true,
        overviewMapControl: true,					
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };
    
    return {
        init: function () {
            google.maps.visualRefresh = true;
            this.map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
            this.map.setCenter(VIZAPP.databases[0].startPoint);
            this.map.setZoom(VIZAPP.databases[0].startZoom);
        }
    }
}();