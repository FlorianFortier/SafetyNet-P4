package com.safetyNet.alerts.api.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface IJsonReader {

    /**
     * Read a json file and return a JSONObject
     * @param fileName the name of the file to read
     * @return a JSONObject
     */
    public JSONObject readJsonFile(String fileName) throws ParseException;
}
