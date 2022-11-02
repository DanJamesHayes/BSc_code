$(document).ready(function(){
  $("#getAllFilms").click(getAllFilms);
  $("#getFilm").click(getFilm);
  $("#addFilm").click(addFilm);
  $("#populateEdit").click(populateEdit);
  $("#editFilm").click(editFilm);
  $("#deleteFilm").click(deleteFilm);
});

function getAllFilms() {
	var format = $("input[name='showFormat']:checked").val();
	$("#allFilms").html("");
	if(format == "xml") {
		$.ajax({
			type: "GET",
			url: "GetAllFilms?format=xml",
			dataType: "xml",
			success: function(xml){
				loadXml(xml, "#allFilms");
			}
	    });
	} else if(format == "string") {
		$.get("GetAllFilms?format=string", function(data, status, request){
			loadString(request, "#allFilms");
	    });
	} else {
		$.getJSON('GetAllFilms?format=json', function(data) {
			loadJson(data, "#allFilms");
		});
	}
}

function getFilm() {
	var format = $("input[name='searchFormat']:checked").val();
	var searchString = $("input[name='searchValue']").val();
	$("#searchResult").html("");
	if(format == "xml") {
		$.ajax({
			type: "GET",
			url: "GetFilm?format=xml&FileName=" + searchString,
			dataType: "xml",
			success: function(xml){
				loadXml(xml, "#searchResult");
			}
	    });
	} else if(format == "string") {
		$.get("GetFilm?format=string&FileName=" + searchString, 
				function(data, status, request){
			loadString(request, "#searchResult");
	    });
	} else {
		$.getJSON("GetFilm?format=json&FileName=" + searchString, 
				function(data) {
			loadJson(data, "#searchResult");
		});
	}
}

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
	$.get("InsertFilm?" + addString, function(data, status, request) {
		$("#insertResult").html(data);
		$("input[name='addTitle']").val("");
		$("input[name='addYear']").val("");
		$("input[name='addDirector']").val("");
		$("input[name='addStars']").val("");
		$("input[name='addReview']").val("");
	});
}

function populateEdit() {
	$("#populateResult").html("");
	var id = $("input[name='editId']").val();
	$.ajax({
		type: "GET",
		url: "GetFilmById?FileName=" + id,
		dataType: "xml",
		success: function(xml){
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
		$("#populateEdit").prop('disabled', false);
		$("input[name='editId']").prop('readonly', false).val("");
		$("input[name='editTitle']").prop('readonly', true).val("");
		$("input[name='editYear']").prop('readonly', true).val("");
		$("input[name='editDirector']").prop('readonly', true).val("");
		$("input[name='editStars']").prop('readonly', true).val("");
		$("input[name='editReview']").prop('readonly', true).val("");
		$("#editFilm").prop('disabled', true);
	});
}

function deleteFilm() {
	$("#deleteResult").html("");
	var id = encodeURIComponent($("input[name='deleteId']").val());
	$.get("DeleteFilm?Id=" + id, function(data) {
		$("input[name='deleteId']").val("");
		$("#deleteResult").html(data);
	});
}