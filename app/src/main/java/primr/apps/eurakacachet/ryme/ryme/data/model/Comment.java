package primr.apps.eurakacachet.ryme.ryme.data.model;


import java.util.ArrayList;
import java.util.List;

public class Comment {

    private String mUserName;
    private String mCommentText;
    private String mCommentTime;
    private static List<Comment> commentList;

    public String getUserName() {
        return mUserName;
    }

    public String getCommentText() {
        return mCommentText;
    }

    public String getCommentTime() {
        return mCommentTime;
    }

    public Comment(String username, String commentTime, String commentText){
        mCommentText = commentText;
        mUserName = username;
        mCommentTime = commentTime;
    }

    public static List<Comment> getComments(){
        commentList = new ArrayList<>();
        commentList.add(new Comment("thomas", "2s", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                "tempor incididunt ut labore et dolore magna aliqua. "));
        commentList.add(new Comment("son", "3s", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                "tempor incididunt ut labore et dolore magna aliqua. "));
        commentList.add(new Comment("tino", "4s", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                "tempor incididunt ut labore et dolore magna aliqua. "));

        return commentList;
    }
}
