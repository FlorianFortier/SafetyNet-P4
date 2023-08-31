package com.safetyNet.alerts.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.alerts.api.entity.Firestation;
import com.safetyNet.alerts.api.entity.MedicalRecord;
import com.safetyNet.alerts.api.entity.Person;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import java.io.FileReader;
import java.io.IOException;



/**
 * ReadDataFromJson is a class that read data from a json file
 */
@Component
public class ReadDataFromJson implements IJsonReader {

    private Person Person;

    private Firestation FireStation;

    private MedicalRecord MedicalRecord;
    /**
     *
     * @param fileName the name of the file to read
     * @return a JSONObject
     */

    public JSONObject readJsonFile(String fileName) throws ParseException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader(fileName));
            // Changement Méthode de parcours du fichier JSON

            return (JSONObject) obj;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
