$(document).ready(function () {

	fire_ajax_submit();
	
	 $("#getcards").click(function (event) {
	        event.preventDefault();
	        fire_ajax_submit();
	    });
});

function fire_ajax_submit() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/cards/",
        
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Cards List</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
        }
    });

}