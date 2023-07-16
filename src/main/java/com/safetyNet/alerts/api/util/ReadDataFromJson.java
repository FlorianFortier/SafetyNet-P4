package com.safetyNet.alerts.api.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadDataFromJson implements IJsonReader {

    /**
     *
     * @param fileName the name of the file to read
     * @return a JSONObject
     */
    public JSONObject readJsonFile(String fileName) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileName);
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }
}
