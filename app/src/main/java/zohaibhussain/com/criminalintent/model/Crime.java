package zohaibhussain.com.criminalintent.model;

import java.util.UUID;

/**
 * Created by zohaibhussain on 2015-12-12.
 */
public class Crime {

    private UUID mID;
    private  String mTitle;

    public Crime(){
        mID = UUID.randomUUID();
    }

    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
