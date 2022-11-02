function loadXml(xml, region) {
	var table = "<tr><th>ID</th><th>Title</th><th>Year</th><th>Director</th>" +
			"<th>Stars</th><th>Review</th></tr>";

	$(xml).find("film").each(function(){
	    var id = $(this).find('id').text();
	    var title = $(this).find('title').text();
	    var year = $(this).find('year').text();
	    var director = $(this).find('director').text();
	    var stars = $(this).find('stars').text();
	    var review = $(this).find('review').text();

	    table += "<tr><td>" + id + "</td><td>" + title + "</td><td>" + year +
	    		"</td><td>" + director + "</td>" + "<td>" + stars +
	    		"</td><td>" + review + "</td></tr>"

	});
	$(region).html(table);
}

function loadJson(json, region) {
	var tbl_body = "";
	var tbl_head = "";
	$.each(json[0], function(k, v) {
		tbl_head += "<th>" + k + "</th>";
	});
	tbl_body += "<tr>"+ tbl_head +"</tr>";
	$.each(json, function() {
		var tbl_row = "";
		$.each(this, function(k, v) {
				tbl_row += "<td>"+v+"</td>";
		})
		tbl_body += "<tr>"+tbl_row+"</tr>";             
	});
	$(region).html(tbl_body);
}

function loadString(string, region) {
	var rawData = string.responseText;
    var rowStrings = rawData.split(/[\n\r]+/);
    var headings = rowStrings[0].split("#");
    var rows = new Array(rowStrings.length-1);
    for(var i=1; i<rowStrings.length; i++) {
      rows[i-1] = rowStrings[i].split("#");
    }
    var table = getTable(headings, rows);
	$(region).html(table);
}


function getTable(headings, rows) {
  var table = "<table border='1' class='ajaxTable'>\n" +
              getTableHeadings(headings) +
              getTableBody(rows) +
              "</table>";
  return(table);
}

function getTableHeadings(headings) {
  var firstRow = "  <tr>";
  for(var i=0; i<headings.length; i++) {
    firstRow += "<th>" + headings[i] + "</th>";
  }
  firstRow += "</tr>\n";
  return(firstRow);
}

function getTableBody(rows) {
  var body = "";
  for(var i=0; i<rows.length; i++) {
    body += "  <tr>";
    var row = rows[i];
    for(var j=0; j<row.length; j++) {
      body += "<td>" + row[j] + "</td>";
    }
    body += "</tr>\n";
  }
  return(body);
}

function htmlInsert(htmlData) {
	  document.getElementById("demo").innerHTML = htmlData;
}

function populateEditXml(xml) {
	$("#updateResult").html("");
	$(xml).find("film").each(function(){
		$("#populateEdit").prop('disabled', true);
		$("input[name='editId']").prop('readonly', true);
		$("input[name='editTitle']").prop('readonly', false).val($(this)
				.find('title').text());
		$("input[name='editYear']").prop('readonly', false).val($(this)
				.find('year').text());
		$("input[name='editDirector']").prop('readonly', false).val($(this)
				.find('director').text());
		$("input[name='editStars']").prop('readonly', false).val($(this)
				.find('stars').text());
		$("input[name='editReview']").prop('readonly', false).val($(this)
				.find('review').text());
		$("#editFilm").prop('disabled', false);
	});
}

$(document).ready(function(){
	  $("#clearEdit").click(function() {
			$("#populateResult").html("");
			$("#populateEdit").prop('disabled', false);
			$("input[name='editId']").prop('readonly', false).val("");
			$("input[name='editTitle']").prop('readonly', true).val("");
			$("input[name='editYear']").prop('readonly', true).val("");
			$("input[name='editDirector']").prop('readonly', true).val("");
			$("input[name='editStars']").prop('readonly', true).val("");
			$("input[name='editReview']").prop('readonly', true).val("");
			$("#editFilm").prop('disabled', true);
			$("#updateResult").html("");
	  });
});