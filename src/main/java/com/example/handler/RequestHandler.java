package com.example.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;



@Controller
@SpringBootApplication
public class RequestHandler {
	
	private PlayerSQLDatabasseConnection databaseConnect = new PlayerSQLDatabasseConnection();
	
	@GetMapping("/")
	String index() {
		return "index.html";
	}
	/*
    private String handleCreatePlayerRequest(JsonNode newPlayer)
    {
    	Player p;
		try {
			p = new ObjectMapper().treeToValue(newPlayer, Player.class);
	    	
    		if (this.databaseConnect.AddPlayerToDatabase(p))
    		{
				return "True";

    		}
    		else
    		{
				return "False";

    		}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "False";
		}
    }
    
    private String handleGetDatabaseRequest()
    {
    	String JSONDatabaseReponse = "";
    	try
    	{
    		JSONDatabaseReponse = this.databaseConnect.toJSON();
    	}
    	catch (JsonProcessingException e)
    	{
    		e.printStackTrace();
    		return "";
    	}

        if (!(JSONDatabaseReponse.equals("")))
        {
            return JSONDatabaseReponse;	
        }
        else
        {
        	return "";
        }

    }
    
    private String handleDeletePlayersFromDatabase(JsonNode requetsFromClient) throws IOException
    {   
    	String returnToClient = "";
		ArrayNode playersToDelete = (ArrayNode) requetsFromClient.get("Players");
		if (playersToDelete.isArray())
		{
			for (JsonNode playerIDNode : playersToDelete)
			{
				String playerID = playerIDNode.textValue();
				if(this.databaseConnect.deleteRecord(playerID)) 
				{
					returnToClient += "Successfully Removed " + playerID + "\n";
				}
				else
				{
					returnToClient += "Failed to remove " + playerID + "\n";

				}
			}
		}
		return returnToClient;
    }
	
	@PostMapping("/createPlayer")
	@ResponseBody
    private String doPost(HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException  {    	
    	BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream()));
    	String test = rd.readLine();

    	ObjectMapper m = new ObjectMapper();
    	JsonNode n = m.readTree(test);
    	
    	String requestType = n.get("Request").textValue();

    	if (requestType.equals("CreatePlayer"))
    	{
    		return handleCreatePlayerRequest(n.get("Player"));

    	}
    	else if (requestType.equals("GetPlayerDatabase")) {
    		return handleGetDatabaseRequest();
		}
    	else if (requestType.equals("DeletePlayer"))
    	{
    		return handleDeletePlayersFromDatabase(n);
    	}
    	else
    	{
    		return "Invalid Request";
    	}
		
    }
	
	public static void main(String[] args) {
		SpringApplication.run(RequestHandler.class, args);
	}
	*/
}
