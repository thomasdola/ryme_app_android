package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.support.annotation.Nullable;

public class SearchResult {

    private String message;

    @Nullable
    private ArtistSearchResults artists;

    @Nullable
    private RequestSearchResults requests;

    private String status;

    private String code;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Nullable
    public ArtistSearchResults getArtists ()
    {
        return artists;
    }


    public void setArtists (@Nullable ArtistSearchResults artists)
    {
        this.artists = artists;
    }

    @Nullable
    public RequestSearchResults getRequests ()
    {
        return requests;
    }

    public void setRequests (@Nullable RequestSearchResults requests)
    {
        this.requests = requests;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "message='" + message + '\'' +
                ", artists=" + artists +
                ", requests=" + requests +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
