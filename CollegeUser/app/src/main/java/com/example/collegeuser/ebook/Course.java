package com.example.collegeuser.ebook;

public class Course {
    private String pdfTitle;
    private String pdfUrl;

    public Course(String pdfTitle, String pdfUrl) {
        this.pdfTitle = pdfTitle;
        this.pdfUrl = pdfUrl;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }
}
