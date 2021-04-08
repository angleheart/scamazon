import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeoutException;

public class Scamazon {

    private WebDriver driver;

    public Scamazon(WebDriver driver){
        this.driver = driver;

    }

    public void watchYoutubeVideo() throws InterruptedException, TimeoutException {
        Thread.sleep(500);
        WebElement youtubeVideo = waitForVisibility(By.className("youtube-video"));
        youtubeVideo.click();
        Thread.sleep(3000);
        youtubeVideo.click();
        Thread.sleep(3000);
        youtubeVideo.click();
        Thread.sleep(12_000);
        while (!driver.findElements(By.className("a-button-disabled")).isEmpty())
            Thread.sleep(250);
        driver.findElement(By.className("youtube-continue-button")).click();
    }

    public void watchAmazonVideo() throws InterruptedException, TimeoutException {
        WebElement amazonVideo = waitForVisibility(By.className("video"));
        amazonVideo.click();
        Thread.sleep(14_000);
        while (!driver.findElements(By.className("a-button-disabled")).isEmpty())
            Thread.sleep(250);
        driver.findElement(By.className("amazon-video-continue-button")).click();
    }

    public void clickPrizeBox(){
        driver.findElement(By.className("box-click-area")).click();
    }

    public void followAuthor(){
        driver.findElement(By.className("follow-author-continue-button")).click();
    }

    public void getBox(int page, int box) throws TimeoutException {
        driver.get("https://www.amazon.com/ga/giveaways/?pageId=" + page);
        WebElement boxWait = waitForVisibility(By.cssSelector("a[href*='_g" + box + "_']"));
        driver.findElement(By.cssSelector("a[href*='_g" + box + "_']")).click();
    }

    public void refresh(){
        driver.get(driver.getCurrentUrl());
    }

    public WebElement waitForVisibility(By by) throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }



}
