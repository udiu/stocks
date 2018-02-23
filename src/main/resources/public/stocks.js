$(document).ready(function() {

	$("#stocks-list").ready(function() {
		// load items
		getList();
	});

	$("#addSubmit").on('click', function(event) {
		event.preventDefault();
		var stock = {}
		stock["name"] = $("#name").val();
		stock["amount"] = $("#amount").val();
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : 'http://localhost:8080/api/stocks',
			data : JSON.stringify(stock),
			dataType : 'json'
		}).then(function(data) {
			getList();
		}).fail(function(data) {
			alert("Stock couldn't be added!");
		})
	});

	$("#editForm").ready(function() {
		var urlParams = new URLSearchParams(window.location.search);
		$.ajax({
			url : "http://localhost:8080/api/stocks/" + urlParams.get('id')
		}).then(function(data) {
			$("#editForm #name").val(data.name);
			$("#editForm #amount").val(data.amount);
		});
	});

	$("#editSubmit").on('click', function(event) {
		event.preventDefault();
		var stock = {}
		stock["name"] = $("#name").val();
		stock["amount"] = $("#amount").val();
		// send ajax
		var urlParams = new URLSearchParams(window.location.search);
		var elem_id = urlParams.get('id');
		$.ajax({
			type : "PUT",
			contentType : "application/json",
			url : 'http://localhost:8080/api/stocks/' + elem_id,
			data : JSON.stringify(stock),
			dataType : 'json'
		}).then(function(data) {
			window.location.href = "http://localhost:8080/stocks.html";
		}).fail(function(data) {
			alert("Stock couldn't be added!");
		})
	});
});

function getList() {
	$.ajax({
		url : "http://localhost:8080/api/stocks"
	}).then(function(data) {
		renderList(data);
	});
}

function renderList(list) {
	$('.stocks-list').empty();
	$
			.each(
					list,
					function(i, obj) {
						var $newLine = $('<div class="form-inline"></div>');
						$newLine.appendTo($('.stocks-list'));
						var $delete = $('<input class="deleteStock" type="button" value="X" id="'
								+ obj.id + '"/>');
						var $text = $('<span class="editStock btn btn-link" id="'
								+ obj.id
								+ '">'
								+ obj.name
								+ ' - '
								+ obj.amount
								+ ' EUR </span>');
						$text.appendTo($newLine);
						$delete.appendTo($newLine);
					});
	$(".editStock")
			.click(
					function() {
						var elem_id = $(this).attr('id');
						window.location.href = "http://localhost:8080/stocksEdit.html?id="
								+ elem_id;
					});
	$(".deleteStock").click(function() {
		var elem_id = $(this).attr('id');
		$.ajax({
			type : 'DELETE',
			url : 'http://localhost:8080/api/stocks/' + elem_id,
			data : 'id=' + elem_id,
			success : function(data) {
				getList();
			}
		});
	});
}
