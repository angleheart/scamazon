import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

public class CookieUploader {

    private WebDriver driver;

    public CookieUploader(WebDriver driver){
        this.driver = driver;
    }

    public boolean uploadCookies(String loginURL, String cookieName) throws IOException, InterruptedException {

        try {
            System.out.println("Attempting to upload " + cookieName + " to " + loginURL);
            driver.get(loginURL);
            Thread.sleep(1000);
            File file = new File(cookieName);
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;
                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        expiry = new Date(val);
                    }
                    Boolean isSecure = Boolean.valueOf(token.nextToken());
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    System.out.println(ck);
                    driver.manage().addCookie(ck); // This will add the stored cookie to your current session
                    driver.get(loginURL);
                    //System.out.println(cookieName + " cookie uploaded to " + loginURL);
                    Thread.sleep(100);
                }
            }
        }catch(Exception e){
            System.out.println("Failed to upload " + cookieName + " to " + loginURL);
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
