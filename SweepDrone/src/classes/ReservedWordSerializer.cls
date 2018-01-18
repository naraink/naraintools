public class ReservedWordSerializer {
    
    //true for pretty printing
    JsonGenerator g = Json.createGenerator(true);
    
    /**
     * Usage:
     * new ReservedWordSerializer(obj).getAsString();
     */
    public ReservedWordSerializer(Object obj) {
        if (obj == null) {
            g.writeNull();
        } else if (obj instanceof Map<String,Object>) {
            traverseMap((Map<String,Object>)obj);
        } else if (obj instanceof List<Object>) {
            traverseList((List<Object>)obj);
        } else {
            g.writeObject(obj);
        }
    }
    
    public String getAsString() {
        return g.getAsString();
    }
    
    void traverseMap(Map<String,Object> obj) {
        List<String> keys = new List<String>(obj.keySet());
        keys.sort();
        
        g.writeStartObject();
        for (String key : keys) {
            Object value = obj.get(key);
            String myKey = key.replace('_','');
            g.writeFieldName(myKey);
            
            if (value == null) {
                g.writeNull();
            } else if (value instanceof Map<String,Object>) {
                traverseMap((Map<String,Object>)value);
            } else if (value instanceof List<Object>) {
                traverseList((List<Object>)value);
            } else {
                g.writeObject(value);
            }
        }
        g.writeEndObject();
    }
    
    void traverseList(List<Object> objs) {
        g.writeStartArray();
        for (Object obj : objs) {
            if (obj == null) {
                g.writeNull();
            } else if (obj instanceof Map<String,Object>) {
                traverseMap((Map<String,Object>)obj);
            } else if (obj instanceof List<Object>) {
                traverseList((List<Object>)obj);
            } else {
                g.writeObject(obj);
            }
        }
        g.writeEndArray();
    }
    
}