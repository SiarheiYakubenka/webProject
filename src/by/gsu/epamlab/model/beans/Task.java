package by.gsu.epamlab.model.beans;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Task {
    int id;
    String description;
    Date completionsDate;
    boolean isFixed;
    boolean isDelMarked;
    String fileName;
    private final static SimpleDateFormat OUTPUT_DATE_FORMAT =
            new SimpleDateFormat("dd.MM.yyyy");

    public Task() {
    }

    public Task(int id, String description, Date completionsDate) {
        this.id = id;
        this.description = description;
        this.completionsDate = completionsDate;
        this.isFixed = false;
        this.isDelMarked = false;
    }

    public Task(int id, String description, Date completionsDate, boolean isFixed, boolean isDelMarked, String fileName) {
        this.id = id;
        this.description = description;
        this.completionsDate = completionsDate;
        this.isFixed = isFixed;
        this.isDelMarked = isDelMarked;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCompletionsDate() {
        return completionsDate;
    }

    public String getStringCompletionsDate() {
        return OUTPUT_DATE_FORMAT.format(completionsDate);
    }

    public void setCompletionsDate(Date completionsDate) {
        this.completionsDate = completionsDate;
    }

    public void setCompletionsDate(String completionsDate) {
        this.completionsDate = Date.valueOf(completionsDate);
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public boolean isDelMarked() {
        return isDelMarked;
    }

    public void setDelMarked(boolean delMarked) {
        isDelMarked = delMarked;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
