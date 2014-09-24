var VIZAPP = new Object();

VIZAPP.isLoginRequired = true;

VIZAPP.initialize = function()
{	        
    VIZAPP.gui.init();
}

$( document ).ready( function() {
    VIZAPP.initialize();
});


