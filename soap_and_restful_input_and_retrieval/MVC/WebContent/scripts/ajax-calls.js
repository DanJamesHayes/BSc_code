/**
 * Method ensures document fully loaded before allowing functions to called
 * 
 * @returns call to relevant JS function 
 */
$(document).ready(function(){
  $("#getAllFilms").click(getAllFilms);
  $("#getFilm").click(getFilm);
  $("#addFilm").click(addFilm);
  $("#populateEdit").click(populateEdit);
  $("#editFilm").click(editFilm);
  $("#deleteFilm").click(deleteFilm);
});


/**
 * Get all films in specified format (default to Json if no format specified)
 * 
 * @returns call to loadXml, loadString or loadJson
 */
function getAllFilms() {
	
	// Collect request format option and clear any previous request results
	var format = $("input[name='showFormat']:checked").val();
	$("#allFilms").html("");
	
	// Get request with XML format
	if(format == "xml") {
		$.ajax({
			type: "GET",
			url: "GetAllFilms?format=xml",
			dataType: "xml",
			success: function(xml){
				loadXml(xml, "#allFilms");
			}
	    });
		
	// Get request with string format
	} else if(format == "string") {
		$.get("GetAllFilms?format=string", function(data, status, request){
			loadString(request, "#allFilms");
	    });
	
	// get request wit JSON format (default)
	} else {
		$.getJSON('GetAllFilms?format=json', function(data) {
			loadJson(data, "#allFilms");
		});
	}
}


/**
 * Collect search query from input box and use it as the FileName parameter for 
 * the GetFilm servlet
 * 
 * @returns call to loadXml, loadString or loadJson
 */
function getFilm() {
	
	// Collect search query and format and clear previous search results
	var format = $("input[name='searchFormat']:checked").val();
	var searchString = $("input[name='searchValue']").val();
	$("#searchResult").html("");
	
	// Get request with XML format
	if(format == "xml") {
		$.ajax({
			type: "GET",
			url: "GetFilm?format=xml&FileName=" + searchString,
			dataType: "xml",
			success: function(data){
				loadXml(data, "#searchResult");
			}
	    });
	
	// Get request with string format
	} else if(format == "string") {
		$.get("GetFilm?format=string&FileName=" + searchString, 
				function(data, status, request){
			loadString(request, "#searchResult");
	    });
		
	// get request wit JSON format (default)
	} else {
		$.getJSON("GetFilm?format=json&FileName=" + searchString, 
				function(data) {
			loadJson(data, "#searchResult");
		});
	}
}


/**
 * Collect and URI encode data from the add film input elements.  Use collected
 * data as parameters for the InsertFilm servlet
 * 
 * @returns Result of insertion attempt
 */
function addFilm() {
	
	$("#insertResult").html("");
	
	var title = encodeURIComponent($("input[name='addTitle']").val());
	var year = encodeURIComponent($("input[name='addYear']").val());
	var director = encodeURIComponent($("input[name='addDirector']").val());
	var stars = encodeURIComponent($("input[name='addStars']").val());
	var review = encodeURIComponent($("input[name='addReview']").val());
	
	var addString = "FileName=" + title + "&Year=" + year 
			+ "&Director=" + director + "&Stars=" + stars
			+ "&Review=" + review;
	
	$.get("InsertFilm?" + addString, function(data) {
		$("#insertResult").html(data);
		$("input[name='addTitle']").val("");
		$("input[name='addYear']").val("");
		$("input[name='addDirector']").val("");
		$("input[name='addStars']").val("");
		$("input[name='addReview']").val("");
	});
}


/**
 * Collects the film id from the editId element and use it as FileName parameter
 * for the GetFilmByID servlet
 * 
 * @returns Call to populateEditXml function or informs the user that the ID is 
 * 			not recognised
 */
function populateEdit() {
	
	var id = $("input[name='editId']").val();
	$("#populateResult").html("");
	
	$.ajax({
		type: "GET",
		url: "GetFilmById?FileName=" + id,
		dataType: "xml",
		success: function(xml){
			// Get ID of returned film to check if request was successful
			var idReturned = "";
			$(xml).find("film").each(function(){
				idReturned = $(this).find('id').text()
			});
			
			if(id == idReturned) {
				populateEditXml(xml);
			} else {
				$("#populateResult").html("No record found with that ID.");
				$("#updateResult").html("");
			}
		}
    });
}

/**
 * Collect and URI encode data from the edit film input elements.  Use collected
 * data as parameters for the UpdateFilm servlet
 * 
 * @returns Result of update attempt
 */
function editFilm() {
	
	var id = encodeURIComponent($("input[name='editId']").val());
	var title = encodeURIComponent($("input[name='editTitle']").val());
	var year = encodeURIComponent($("input[name='editYear']").val());
	var director = encodeURIComponent($("input[name='editDirector']").val());
	var stars = encodeURIComponent($("input[name='editStars']").val());
	var review = encodeURIComponent($("input[name='editReview']").val());
	
	var addString = "FileName=" + title + "&Id=" + id + "&Year=" + year
			+ "&Director=" + director + "&Stars=" + stars
			+ "&Review=" + review;
	
	$.get("UpdateFilm?" + addString, function(data) {
		$("#updateResult").html(data);
		// Alter which input fields can be amended
		$("input[name='editId']").prop('readonly', false).val("");
		$("input[name='editTitle']").prop('readonly', true).val("");
		$("input[name='editYear']").prop('readonly', true).val("");
		$("input[name='editDirector']").prop('readonly', true).val("");
		$("input[name='editStars']").prop('readonly', true).val("");
		$("input[name='editReview']").prop('readonly', true).val("");
		// Alter which input buttons can be pressed
		$("#populateEdit").prop('disabled', false);
		$("#editFilm").prop('disabled', true);
	});
}

/**
 * Collect and URI encode the ID from the deleteId field and pass it as a
 * parameter to the DeleteFilm servlet
 * 
 * @returns Result of delete attempt
 */
function deleteFilm() {
	
	var id = encodeURIComponent($("input[name='deleteId']").val());
	$("input[name='deleteId']").val("");
	$("#deleteResult").html("");
	
	$.get("DeleteFilm?Id=" + id, function(data) {
		$("#deleteResult").html(data);
	});
}