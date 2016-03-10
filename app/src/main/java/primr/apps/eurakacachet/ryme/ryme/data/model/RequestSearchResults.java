package primr.apps.eurakacachet.ryme.ryme.data.model;


import java.util.Arrays;

public class RequestSearchResults {

    private ArtistRequest[] data;

    public ArtistRequest[] getData ()
    {
        return data;
    }

    public void setData (ArtistRequest[] data)
    {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestSearchResults{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
