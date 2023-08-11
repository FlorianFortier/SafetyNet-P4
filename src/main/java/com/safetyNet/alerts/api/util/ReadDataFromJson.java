package com.safetyNet.alerts.api.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ReadDataFromJson is a class that read data from a json file
 */
public class ReadDataFromJson implements IJsonReader {

    /**
     *
     * @param fileName the name of the file to read
     * @return a JSONObject
     */

    public JSONObject readJsonFile(String fileName) throws ParseException {

        Object obj = null;
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject jsonObject = (JSONObject) obj;
            JSONParser jsonParser = new JSONParser();
            obj = jsonParser.parse(fileName);
            JSONArray record = (JSONArray) obj;
            record.forEach(item -> parseMedicalRecord((JSONObject) item));
//            record.forEach(item -> parseMedicalRecord((JSONObject) item));
//            record.forEach(item -> parseMedicalRecord((JSONObject) item));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return (JSONObject) obj;
    }

    private static void parseMedicalRecord(JSONObject medicalRecord)
    {
        JSONObject dataObject = (JSONObject) medicalRecord.get("medicalrecords");

        String firstName = (String) dataObject.get("firstName");

        String lastName = (String) dataObject.get("lastName");

        String birthdate = (String) dataObject.get("birthdate");

        String[] medications = (String[]) dataObject.get("medications");

        String[] allergies = (String[]) dataObject.get("allergies");


    }
}
