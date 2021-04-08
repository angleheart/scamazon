import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class AccountDriver {

    private String username;
    private int driverInstances;

    public AccountDriver(String username) {
        this.username = username;
    }

    private static ExecutorService executor = Executors.newCachedThreadPool();

    public void launch() {
        launch(1);
    }

    public void launch(int driverInstances) {
        this.driverInstances = driverInstances;

        for (int i = 1; i < driverInstances + 1; i++) {

            int finalI = i;
            executor.execute(()-> {
                try {
                    boxSelector(finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }


    public void boxSelector(int instance) throws IOException, InterruptedException, TimeoutException {
        WebDriver driver = startBrowser(instance);
        Scamazon scamazon = new Scamazon(driver);
        PageHandler pageHandler = new PageHandler(driver);

        int boxNumber = instance;


        for (int pageNumber = 1; pageNumber < 200; boxNumber += driverInstances) {

            try {

                if (boxNumber > 24) {
                    pageNumber++;
                    boxNumber = instance;
                }

                scamazon.getBox(pageNumber, boxNumber);

                if (pageHandler.handleStatus()) {
                    System.out.println("WINNER: " + username + " instance " + instance + " page " + pageNumber);
                    Thread.sleep(Long.MAX_VALUE);
                }


            } catch (TimeoutException e) {
                System.out.println("Timed out");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private WebDriver startBrowser(int instance) throws IOException, InterruptedException {

        System.out.println("Starting: " + username + " instance " + instance);
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--mute-audio");
        //chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        loadCookies(driver, instance);
        return driver;
    }

    private void loadCookies(WebDriver driver, int instance) throws IOException, InterruptedException {
        CookieUploader cookie = new CookieUploader(driver);
        //Halt thread if cookies do not upload to prevent failed login from running
        if (!cookie.uploadCookies("https://www.amazon.com/ref=ap_frn_logo", username + "-amzn-cookie.data")) {
            System.out.println("!!!FAILED TO UPLOAD COOKIES!!! for " + username + " instance " + instance);
            Thread.sleep(Integer.MAX_VALUE);
        } else
            System.out.println("Cookies uploaded for " + username + " instance " + instance);
    }


}
