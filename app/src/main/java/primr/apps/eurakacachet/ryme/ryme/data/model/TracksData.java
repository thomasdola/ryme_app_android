package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.support.annotation.Nullable;

import java.util.Arrays;

public class TracksData {

    @Nullable
    private Track[] data;

    @Nullable
    public Track[] getData ()
    {
        return data;
    }

    public void setData (@Nullable Track[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "TracksData [data = "+ Arrays.toString(data) +"]";
    }

}
