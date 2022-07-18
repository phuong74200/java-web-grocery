/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import sample.google.GoogleDTO;

/**
 *
 * @author phuon
 */
public class GoogleUtiils {

    public static GoogleDTO getInfo(String urlParameters) throws MalformedURLException, IOException {
        URL url = new URL("https://accounts.google.com/o/oauth2/token");
        URLConnection urlConn = url.openConnection();
        urlConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(
                urlConn.getOutputStream());
        writer.write(urlParameters);
        writer.flush();

        //get output in outputString 
        String line, outputString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString += line;
        }

        //get Access Token 
        JsonObject json = (JsonObject) new JsonParser().parse(outputString);
        String access_token = json.get("access_token").getAsString();

        //get User Info 
        url = new URL(
                "https://www.googleapis.com/oauth2/v1/userinfo?access_token="
                + access_token);
        urlConn = url.openConnection();
        outputString = "";
        reader = new BufferedReader(new InputStreamReader(
                urlConn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString += line;
        }
        System.out.println(outputString);

        // Convert JSON response into Pojo class
        GoogleDTO data = new Gson().fromJson(outputString, GoogleDTO.class);
        System.out.println(data);
        writer.close();
        reader.close();

        return data;
    }
}
