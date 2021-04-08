import java.util.ArrayList;
import java.util.Stack;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<String> accounts = new ArrayList<String>();
        //Add the emails for the accounts you want to run to this list.
        //This must correspond with the filenames you saved your cookies under

        for (String username : accounts) {
            AccountDriver account = new AccountDriver(username);
            account.launch(1);
            Thread.sleep(5000);
        }

        Thread.sleep(Long.MAX_VALUE);
    }


}
