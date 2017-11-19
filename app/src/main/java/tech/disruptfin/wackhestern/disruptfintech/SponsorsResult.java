package tech.disruptfin.wackhestern.disruptfintech;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SponsorsResult {
    @SerializedName("message")
    @Expose
    private List<SponsorsValue> message;

    public List<SponsorsValue> getTitle() {
        return message;
    }

    public void setTitle(List<SponsorsValue> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ""+message;
    }
}
