import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Date;
import java.util.StringTokenizer;

public class CookieSaver {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeDriver driver = new ChromeDriver();

        driver.get("AUTHORIZATION LOGIN URL GOES HERE");


        Thread.sleep(20_000);

        System.out.println("Saving cookies!");

        // create file named Cookies to store Login Information
        File file = new File("NAME OF COOKIE FILE TO SAVE");
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for (Cookie cookie : driver.manage().getCookies()) {
                bufferedWriter.write((cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure()));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWrite.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        driver.close();
    }

}
