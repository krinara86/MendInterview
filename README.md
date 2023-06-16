# Mend Interview REST API Shortest path with one or zero red edges

# Build instructions

Use Gradle to build
./gradlew clean build

Use gradle to run
./gradlew bootRun

Shortest Path REST API Documentation

The Shortest Path REST API provides endpoints to calculate the shortest paths in a graph based on the provided input. It utilizes the Spring Boot framework to handle HTTP requests and responses.

The base URL for accessing the Shortest Path API is: http://localhost:8080

Please make sure to replace localhost:8080 with the appropriate host and port if your application is running on a different address.


API Endpoints
Calculate Shortest Paths

Endpoint: /api/v1/graphs/shortestPaths

Method: POST

This endpoint calculates the shortest paths in a graph based on the provided input. It takes a JSON payload representing the graph structure and a query parameter specifying the starting node ID.
Request Body

The request body should contain a JSON object representing the graph structure. The JSON structure should adhere to the following format:

	{
	  "nodes": [
	    {
	      "id": "1",
	      "edges": [
	        {
	          "destination": "2",
	          "color": "RED"
	        },
	        {
	          "destination": "3",
	          "color": "BLUE"
	        }
	      ]
	    },
	    {
	      "id": "2",
	      "edges": []
	    },
	    {
	      "id": "3",
	      "edges": []
	    }
	  ]
	}

nodes: An array of node objects in the graph.
    id: The unique identifier for the node.
    edges: An array of edge objects representing the connections from the current node to other nodes.
        destination: The ID of the destination node connected to the current node.
        color: The color of the edge. Possible values are "RED" or "BLUE".

Query Parameter

startNodeId: The ID of the starting node for calculating the shortest paths.

Example Usage

bash

	curl -X POST -H "Content-Type: application/json" -d @graph.json 'http://localhost:8080/api/v1/graphs/shortestPaths?startNodeId=1'

Note: Replace graph.json with the path to your own JSON file representing the graph structure.
Response

The API will respond with a JSON object containing the shortest distances from the starting node to other nodes in the graph. The response format is as follows:

	json

	{
	  "nodeId1": shortestDistance1,
	  "nodeId2": shortestDistance2,
	  ...
	}

nodeId: The ID of a node in the graph.
shortestDistance: The shortest distance from the starting node to the corresponding node ID.

Example Request using cURL

To demonstrate how to use the API, let's consider an example:

Save the JSON file representing the graph structure as graph.json:

	json

	{
	  "nodes": [
	    {
	      "id": "1",
	      "edges": [
	        {
	          "destination": "2",
	          "color": "RED"
	        },
	        {
	          "destination": "3",
	          "color": "BLUE"
	        }
	      ]
	    },
	    {
	      "id": "2",
	      "edges": []
	    },
	    {
	      "id": "3",
	      "edges": []
	    }
	  ]
	}

Open a terminal or command prompt and execute the following cURL command to calculate the shortest paths:

bash

curl -X POST -H "Content-Type: application/json" -d @graph.json 'http://localhost:8080/api/v1/graphs/shortestPaths?startNodeId=1'
