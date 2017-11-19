package tech.disruptfin.wackhestern.disruptfintech;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("message")
    @Expose
    private String message;

    public String getTitle() {
        return message;
    }

    public void setTitle(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message.toString();
    }
}