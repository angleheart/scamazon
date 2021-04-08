import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeoutException;

public class PageAnalyzer {

    private WebDriver driver;
    public PageAnalyzer(WebDriver driver){
        this.driver = driver;
    }


    public int getStatus() throws InterruptedException, TimeoutException {

        //Alert message
        if(!driver.findElements(By.className("a-alert-content")).isEmpty())
            return -2;

        //Phone requested
        if(!driver.findElements(By.cssSelector("a[href*='mobilephone']")).isEmpty())
            return -2;

        //Giveaway ended
        if(!driver.findElements(By.className("a-size-extra-large")).isEmpty() &&
                driver.findElement(By.className("a-size-extra-large")).getText().equalsIgnoreCase("Giveaway ended"))
            return -1;

        //Missing title?
        if(driver.findElements(By.className("prize-title")).isEmpty())
            return -1;

        //--------------------------------------------------

        //Winner
        if(driver.findElement(By.className("prize-title")).getText().contains("you won"))
            return 0;

        //Loser
        if(driver.findElement(By.className("prize-title")).getText().contains("didn't win"))
            return -1;

        //--------------------------------------------------

        //YouTube
        if(!driver.findElements(By.className("youtube-container")).isEmpty())
            return 1;

        //Amazon video
        if(!driver.findElements(By.className("video")).isEmpty())
            return 2;

        //Prize box
        if(!driver.findElements(By.className("box-click-area")).isEmpty())
            return 3;


        //Follow Author
        if (!driver.findElements(By.className("follow-author-continue-button")).isEmpty())
            return 4;


        return -2;
    }
}