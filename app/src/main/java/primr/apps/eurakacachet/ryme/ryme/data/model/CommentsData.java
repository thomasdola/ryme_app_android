package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.support.annotation.Nullable;

import java.util.Arrays;

public class CommentsData {

    @Nullable
    private Comment[] data;

    @Nullable
    public Comment[] getData ()
    {
        return data;
    }

    public void setData (@Nullable Comment[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "TracksData [data = "+ Arrays.toString(data) +"]";
    }

}
