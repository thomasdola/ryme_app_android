package primr.apps.eurakacachet.ryme.ryme.data.model;


import java.util.Arrays;

public class ArtistSearchResults {

    private Artist[] data;

    public Artist[] getData ()
    {
        return data;
    }

    public void setData (Artist[] data)
    {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ArtistSearchResults{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
