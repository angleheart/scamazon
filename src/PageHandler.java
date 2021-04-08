import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeoutException;

public class PageHandler {

    private Scamazon scamazon;
    private PageAnalyzer pageAnalyzer;

    public PageHandler(WebDriver driver){
        scamazon = new Scamazon(driver);
        pageAnalyzer = new PageAnalyzer(driver);
    }

    public boolean handleStatus() throws InterruptedException, TimeoutException {
        return handleStatus(0);
    }


    public boolean handleStatus(int attempts) throws InterruptedException, TimeoutException {

        if(attempts > 10) return false;

        Thread.sleep(2000);

        switch(pageAnalyzer.getStatus()){

        //Alert message or phone requested
            case -2:
                scamazon.refresh();
                break;
            case -1:
                return false;
        //Winner
            case 0:
                return true;
        //YouTube video
            case 1:
                scamazon.watchYoutubeVideo();
                break;
        //Amazon video
            case 2:
                scamazon.watchAmazonVideo();
                break;
        //Prize box
            case 3:
                scamazon.clickPrizeBox();
                break;
        //Follow author
            case 4:
                scamazon.followAuthor();
                break;
        }
        Thread.sleep(2500);
        return handleStatus(++attempts);
    }

}
