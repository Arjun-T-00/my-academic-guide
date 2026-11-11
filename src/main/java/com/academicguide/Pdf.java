package com.academicguide;

public class Pdf {

    private int id;
    private String title;
    private String fileName;
    private int subjectId;

    public Pdf() {
    }

    public Pdf(int id, String title, String fileName, int subjectId) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}