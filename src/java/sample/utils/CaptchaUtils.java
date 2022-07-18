/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.env.env;
import sample.google.CaptchaDTO;
/**
 *
 * @author phuon
 */
public class CaptchaUtils {
    private static final Logger logger = LogManager.getLogger(CaptchaUtils.class);

    public static boolean veriry(String gRecaptchaResponse) {
        String veryfyURL = "https://www.google.com/recaptcha/api/siteverify";

        boolean check = false;
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            check = false;
        } else {
            try {
                URL verifyUrl = new URL(veryfyURL);

                HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String postParams = "secret=" + env.CAPTCHA_SECRET + "&response=" + gRecaptchaResponse;

                conn.setDoOutput(true);

                OutputStream outStream = conn.getOutputStream();
                outStream.write(postParams.getBytes());

                outStream.flush();
                outStream.close();

                int responseCode = conn.getResponseCode();

                InputStream inputStream = conn.getInputStream();

                String line, outputString = "";

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    outputString += line;
                }

                CaptchaDTO data = new Gson().fromJson(outputString, CaptchaDTO.class);

                logger.info(data.isSuccess());
                logger.info(outputString);

                check = data.isSuccess();
                
                System.out.println(outputString);

                reader.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return check;
    }
}