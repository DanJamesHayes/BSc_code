/**
 * Films passed in xml format are placed into html table and inserted into the 
 * specified html element
 * 
 * @param xml			List of films in xml format
 * @param region		Element where table is to be placed
 * @returns				n/a
 */
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


/**
 * Films passed in JSON format are placed into html table and inserted into the 
 * specified html element
 * 
 * @param json			List of films in xml format
 * @param region		Element where table is to be placed
 * @returns				n/a
 */
function loadJson(json, region) {
	
	var tbl_body = "";
	var tbl_head = "";
	
	// Take keys from the first film to create table head
	$.each(json[0], function(k, v) {
		tbl_head += "<th>" + k + "</th>";
	});
	tbl_body += "<tr>"+ tbl_head +"</tr>";
	
	// Iterate over each film to create remaining table rows
	$.each(json, function() {
		var tbl_row = "";
		$.each(this, function(k, v) {
				tbl_row += "<td>"+v+"</td>";
		})
		tbl_body += "<tr>"+tbl_row+"</tr>";             
	});
	$(region).html(tbl_body);
}


/**
 * Films passed in string format are placed into html table and inserted into 
 * the specified html element
 * 
 * @param string		List of films in string format
 * @param region		Element where table is to be placed
 * @returns				n/a
 */
function loadString(string, region) {
	
	// split string into matrix of films for table
	var rawData = string.responseText;
    var rowStrings = rawData.split(/[\n\r]+/); 
    
    // Create matrix of 1 vector containing headings from first vector in films
    // matrix
    var headings = new Array(1)
    headings[0] = rowStrings[0].split("#");
    
    // Create new matrix of films without first vector which contains headings
    var rows = new Array(rowStrings.length-1);
    for(var i=1; i<rowStrings.length; i++) {
    	rows[i-1] = rowStrings[i].split("#");
    }
    
    // Create table from heading and row arrays and insert into html element
    var table = getTableRows(headings, "th") + getTableRows(rows, "td");
	$(region).html(table);
}

/**
 * Create table rows from passed data
 * 
 * @param data			Defines cells of rows
 * @param element		Defines whether table body or header
 * @returns	HTML		Table rows
 */
function getTableRows(data, element) {
	
	var rows = "";
	
	// iterate over vectors of passed data matrix
	for(var i=0; i<data.length; i++) {
		rows += "<tr>";
		var row = data[i];
		// iterate over each element in vector
    	for(var j=0; j<row.length; j++) {
    		rows += "<" + element + ">" + row[j] + "</" + element + ">";
    	}
    	rows += "</tr>";
	}
	return(rows);
}


/**
 * Populate edit input fields with film data make fields not read only
 * 
 * @param xml		Film data to be amended
 * @returns			n/a
 */
function populateEditXml(xml) {
	
	$("#updateResult").html("");
	
	$(xml).find("film").each(function(){
		
		$("#populateEdit").prop('disabled', true);
		$("#editFilm").prop('disabled', false);
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
	});
}

/**
 * Function to clear edit film input fields if user no longer wishes to make
 * amendments
 * 
 * @returns			n/a
 */
$(document).ready(function(){
	
	  $("#clearEdit").click(function() {
		  
			$("#populateResult").html("");
			$("#updateResult").html("");
			
			$("#populateEdit").prop('disabled', false);
			$("#editFilm").prop('disabled', true);
			
			$("input[name='editId']").prop('readonly', false).val("");
			$("input[name='editTitle']").prop('readonly', true).val("");
			$("input[name='editYear']").prop('readonly', true).val("");
			$("input[name='editDirector']").prop('readonly', true).val("");
			$("input[name='editStars']").prop('readonly', true).val("");
			$("input[name='editReview']").prop('readonly', true).val("");
	  });
});