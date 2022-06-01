package com.example.mynotesapplication;
import android.util.JsonWriter;
import androidx.annotation.NonNull;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class Note implements Serializable {
    private final String title;
    private final String description;
    private String date;

    Note() {
        this.title = "No title";
        this.description = "None";
        long day = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date finaldate = new Date(day);

        this.date = sdf.format(finaldate);
    }

    Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        String title = this.title;
        return title;
    }

    public String getDesc() {
        String description = this.description;
        return description;
    }

    public String getDate() {
        String date = this.date;
        return date;
    }

    public void setDate() {
        long d = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date finaldate = new Date(d);
        this.date = sdf.format(finaldate);
    }

    @NonNull
    public String toString() {
        try {
            StringWriter strWrite = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(strWrite);
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject();
            jsonWriter.name("title").value(getTitle());
            jsonWriter.name("date").value(getDate());
            jsonWriter.name("description").value(getDesc());
            jsonWriter.endObject();
            jsonWriter.close();
            return strWrite.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed";
    }
}
