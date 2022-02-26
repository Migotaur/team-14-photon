<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Team-14 Webapp </title>
	</head>
	<body>
		<h1> Database for Team 14</h1>
		<div>
			<table id="team-14_database_table">
				<tbody>
					 <tr className="headerRow">
					 	<th>Delete</th>
					   <th>Player ID</th>
					   <th>First Name</th>
					   <th>Last Name</th>
					   <th>Username</th>
					 </tr>				
				</tbody>
			</table>
		</div>
		<br>
		<button onclick="deletePlayers()" id="deleteButton"> Delete </button>
		
		<br><br><br>
		<div>
			 <label>First name:</label><br>
			 <input type="text" id="firstName"><br>
			 <label>Last name:</label><br>
			 <input type="text" id="lastName"><br>
			 <label>User name:</label><br>
			 <input type="text" id="codename" ><br><br>
			 <button onclick="createPlayer()" id="createPlayerButton">Create Player</button>
		</div>
				
		<script>
			function handleServerReply(serverReply)
			{
				console.log(serverReply);
			}
		
			function createConnectionToWebServer()
			{
				var serverCreatePlayerURL = "http://localhost:8080/createPlayer";
				var httpRequest = new XMLHttpRequest();
				httpRequest.open("POST", serverCreatePlayerURL, false);
				httpRequest.onload = function () {
					handleServerReply(this.responseText);
				}
				return httpRequest;
			}
		
			function createPlayer()
			{
				var serverConnection = createConnectionToWebServer();
				var firstName = document.getElementById("firstName").value;
				var lastName = document.getElementById("lastName").value;
				var codename = document.getElementById("codename").value;
				
				var data = JSON.stringify(
						{"Request": "CreatePlayer",
						"Player" : {
							"firstName" : String(firstName),
							"lastName" : String(lastName),
							"codename" : String(codename) }});
				
				serverConnection.setRequestHeader("Accept", "application/json");
				serverConnection.setRequestHeader("Content-Type", "application/json");
				serverConnection.onload = function () {
					handleServerReply(this.responseText);
					getCurrentDatabase();
				}
				
				serverConnection.send(data);

				
			}
			
			function enableDeleteButton()
			{
				if (getCheckedPlayers().length > 0)
				{
					deleteButton.removeAttribute("disabled");
				}
				else
				{
					deleteButton.setAttribute("disabled", "disabled");
				}
			}
			
			function enableCreatePlayerButton()
			{
				if (getTextFieldsNotEmpty().length == 3)
				{
					createPlayerButton.removeAttribute("disabled");
				}
				else
				{
					createPlayerButton.setAttribute("disabled", "disabled");
				}
			}
			
			function addEventListnertoTextFields()
			{
				var textFields = document.querySelectorAll('input[type=text]');
				textFields.forEach(function(textField, index, arr)
						{
							textField.addEventListener('input', enableCreatePlayerButton);
						});
			}

			
			function getCheckedPlayers()
			{
				var checkedBoxes = document.querySelectorAll('input[name=PlayerFieldCheckbox]:checked');
				var playersIDToDelete = new Array();
				checkedBoxes.forEach(function (checkedBox, index, arr) {
					var playerID = [checkedBox.parentElement.parentElement.children][0][1].innerText;
					playersIDToDelete.push(playerID);
				})
				return playersIDToDelete;
			}
			
			function getTextFieldsNotEmpty()
			{
				var textFields = document.querySelectorAll('input[type=text]');
				var nonEmptyTextFields = new Array();
				textFields.forEach(function (textField, index, arr) {
					if (textField.value != "")
					{
						nonEmptyTextFields.push(textField);
					}
				});
				return nonEmptyTextFields;
			}
			
			function deletePlayers()
			{
				var playerIDToDelete = getCheckedPlayers();
				var serverConnection = createConnectionToWebServer();
				var data = JSON.stringify(
						{
							"Request" : "DeletePlayer",
							"Players" : playerIDToDelete
						}
				);
				serverConnection.onload = function () {
					handleServerReply();
					getCurrentDatabase();
				}
				serverConnection.send(data);

			}
			
			function clearTextFields()
			{
				var textFields = document.querySelectorAll('input[type=text]');
				textFields.forEach(function (textField, index, arr) {
					textField.value = '';
				});
			}
			
			function getCurrentDatabase ()
			{
				var serverConnection = createConnectionToWebServer();
				data = JSON.stringify(
					{"Request" : "GetPlayerDatabase"}		
				);
				serverConnection.onload = function () {
					playerTable = this.responseText;
					
					function deleteAllRowsInTable(table)
					{
						var rows = Array.from(table['children'][0]['children']);
						rows.forEach(function (row, index, arr)
							{
								if (row.getAttribute("name") == "playerRow")
								{
									row.remove();
								}
							})
					}
					
					function refreshTable()
					{
						var htmltable = document.getElementById("team-14_database_table");
						if (htmltable['children'][0]['children'].length > 1)
						{
							deleteAllRowsInTable(htmltable);
						}
						
						var players = JSON.parse(playerTable)['Players'];
						players.forEach(function (playerObj, index, arr) {
							var row = htmltable.insertRow(-1);
							row.setAttribute("name", "playerRow");
							var x = document.createElement("INPUT");
							x.setAttribute("type", "checkbox");
							x.setAttribute("name", "PlayerFieldCheckbox");
							x.addEventListener('click', enableDeleteButton)
							row.insertCell(0).appendChild(x);
							row.insertCell(1).innerHTML = playerObj['userID']
							row.insertCell(2).innerHTML = playerObj['firstName']
							row.insertCell(3).innerHTML = playerObj['lastName']
							row.insertCell(4).innerHTML = playerObj['codeName']
							

						})
						
					}
					refreshTable();
					clearTextFields();
					enableDeleteButton();
					enableCreatePlayerButton();
				}
				serverConnection.send(data);
			}
			
			var playerTable;
			var deleteButton = document.getElementById("deleteButton");
			var createPlayerButton = document.getElementById("createPlayerButton");
			addEventListnertoTextFields();
			enableDeleteButton();
			enableCreatePlayerButton();
			getCurrentDatabase();

		</script>
	</body>
</html>