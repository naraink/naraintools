public class DroneController {
	String result;
	HttpResponse response;
	HttpRequest request;
	
	public String responseBody {get; set;}
	public String lat{get; set;}  
	public String lon{get; set;}
	
	public String north{get; set;}
	public String east{get; set;}
	public String up{get; set;}
	
    public void retrieveDrones() {
    	//http://droneapi.ddns.net:1235/vehicle/ef10b1cf
		Http http = new Http();
		request = new HttpRequest();
		request.setEndpoint('http://droneapi.ddns.net:1235/vehicle');
		request.setMethod('GET');
		response = http.send(request);
		System.debug(response.getStatusCode());
		if (response.getStatusCode() == 200) { 
				    // Deserialize the JSON string into collections of primitive data types.
		    Object translate = Json.deserializeUntyped(response.getBody());
			String prettyJson =	(String) new ReservedWordSerializer(translate).getAsString();
		    System.debug('Received the following vehicles:' + prettyJson);
		}
    }
    
    public void gotoRelativeCurrentDrone() {
    	//http://droneapi.ddns.net:1235/vehicle/ef10b1cf
    	takeOffDrone();
    	Http http = new Http();
		request.setEndpoint('http://droneapi.ddns.net:1235/vehicle/1ed98f14/action');
		request.setMethod('POST');
		request.setBody('{"north": ' + '50' + ', "east": ' + '20' + ', "up": ' + '0' + ', "name": "Goto-Relative-Current"}');
		response = http.send(request);
		responseBody += response.getBody();
		if (response.getStatusCode() == 800) { 
		    // Deserialize the JSON string into collections of primitive data types.
		    Object jsonResponse = Json.deserializeUntyped(response.getBody());
		    Map<String,Object> root = (Map<String,Object>)jsonResponse;
		    List<String> keys = new List<string>(root.keySet());
		    String key = keys[0];
		    //for (String key : keys){
            Map<String,Object> action = (Map<String,Object>)root.get('action');
		    keys = new List<string>(action.keySet());
		    key = 'coordinate';
		   // for (String k : keys){
           List<Object> coordinates = (List<Object>)action.get('coordinate');
		    //}
		    //String[] coordinates = (String[]) action.get('coordinate');
		    responseBody += response.getBody();
		    lat = String.valueOf(coordinates.get(0));
		    lon = String.valueOf(coordinates.get(1));
		  //  lon = coordinate[1];
		}
    }
     
	public void takeOffDrone() {		
		Http http = new Http();
		request = new HttpRequest();
		request.setEndpoint('http://droneapi.ddns.net:1235/vehicle/1ed98f14/action');
		request.setMethod('POST');
		request.setBody('{"height": 30, "name": "Takeoff"}');
		response = http.send(request);
		if (response.getStatusCode() == 200) { 
		    // Deserialize the JSON string into collections of primitive data types.
		    Object jsonResponse = Json.deserializeUntyped(response.getBody());
		    Map<String,Object> root = (Map<String,Object>)jsonResponse;
            Map<String,Object> action = (Map<String,Object>)root.get('action');
			List<Object> coordinates = (List<Object>)action.get('coordinate');
		    responseBody += response.getBody();
		    lat = String.valueOf(coordinates.get(0));
		    lon = String.valueOf(coordinates.get(1));
    	}
	}
    
    public String getResult(){
			return result;	
		}
}