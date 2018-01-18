public class Vehicles {
	public static void consumeObject(JSONParser parser) {
		Integer depth = 0;
		do {
			JSONToken curr = parser.getCurrentToken();
			if (curr == JSONToken.START_OBJECT || 
				curr == JSONToken.START_ARRAY) {
				depth++;
			} else if (curr == JSONToken.END_OBJECT ||
				curr == JSONToken.END_ARRAY) {
				depth--;
			}
		} while (depth > 0 && parser.nextToken() != null);
	}

	public class Vehicle {
		public String vehicleType {get;set;} 
		public links links {get;set;} 
		public String id {get;set;} 
		public String name {get;set;} 

		public Vehicle(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'vehicleType') {
							vehicleType = parser.getText();
						} else if (text == 'links') {
							links = new links(parser);
						} else if (text == 'id') {
							id = parser.getText();
						} else if (text == 'name') {
							name = parser.getText();
						} else {
							System.debug(LoggingLevel.WARN, 'Vehicle consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
	public class TypeZ {
		public List<String> listOfValues {get;set;} 

		public TypeZ(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'listOfValues') {
							listOfValues = new List<String>();
							while (parser.nextToken() != JSONToken.END_ARRAY) {
								listOfValues.add(parser.getText());
							}
						} else {
							System.debug(LoggingLevel.WARN, 'Type_Z consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
	public class actions {
		public String name {get;set;} 
		public String method {get;set;} 
		public String title {get;set;} 
		public String href {get;set;} 
		public List<Fields> fields {get;set;} 

		public actions(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'name') {
							name = parser.getText();
						} else if (text == 'method') {
							method = parser.getText();
						} else if (text == 'title') {
							title = parser.getText();
						} else if (text == 'href') {
							href = parser.getText();
						} else if (text == 'fields') {
							fields = new List<Fields>();
							while (parser.nextToken() != JSONToken.END_ARRAY) {
								fields.add(new Fields(parser));
							}
						} else {
							System.debug(LoggingLevel.WARN, '_actions consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
	public class Fields {
		public String name {get;set;} 
		public TypeZ typeZ {get;set;} // in json: type

		public Fields(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'name') {
							name = parser.getText();
						} else if (text == 'type') {
							typeZ = new TypeZ(parser);
						} else {
							System.debug(LoggingLevel.WARN, 'Fields consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
	public class links {
		public Self self {get;set;} 

		public links(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'self') {
							self = new Self(parser);
						} else {
							System.debug(LoggingLevel.WARN, '_links consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
	public class embedded {
		public List<Vehicle> vehicle {get;set;} 

		public embedded(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'vehicle') {
							vehicle = new List<Vehicle>();
							while (parser.nextToken() != JSONToken.END_ARRAY) {
								vehicle.add(new Vehicle(parser));
							}
						} else {
							System.debug(LoggingLevel.WARN, '_embedded consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
	public class Self {
		public String href {get;set;} 
		public String title {get;set;} 

		public Self(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == 'href') {
							href = parser.getText();
						} else if (text == 'title') {
							title = parser.getText();
						} else {
							System.debug(LoggingLevel.WARN, 'Self consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	}
	
		public embedded embedded {get;set;} 
		public List<actions> actions {get;set;} 
		public links links {get;set;} 

		public Vehicles(JSONParser parser) {
			while (parser.nextToken() != JSONToken.END_OBJECT) {
				if (parser.getCurrentToken() == JSONToken.FIELD_NAME) {
					String text = parser.getText();
					if (parser.nextToken() != JSONToken.VALUE_NULL) {
						if (text == '_embedded') {
							embedded = new embedded(parser);
						} else if (text == 'actions') {
							actions = new List<actions>();
							while (parser.nextToken() != JSONToken.END_ARRAY) {
								actions.add(new actions(parser));
							}
						} else if (text == 'links') {
							links = new links(parser);
						} else {
							System.debug(LoggingLevel.WARN, 'Vehicles consuming unrecognized property: '+text);
							consumeObject(parser);
						}
					}
				}
			}
		}
	
	
	public static Vehicles parse(String json) {
		return new Vehicles(System.JSON.createParser(json));
	}
}