package com.example.mikael.mikaelhagfeldt_petshare;

/*
    En enkel klass för att "moddelera" ett element i bloggen. Denna klass används främst inom klass
    Adapter, som tar denna data och kopplar den samman med en layout (cardview). Klassen Adapter har en lista
    av Blog objekt.
 */

public class Blog
{
    private String fieldStrTitle;
    private String fieldStrDescription;
    private String fieldStrImage;
    private String fieldStrTimeStamp;
    private String fieldStrUserID;

    public Blog()
    {

    }

    public Blog(String fieldStrTitle, String fieldStrDescription, String fieldStrImage, String fieldStrTimeStamp, String fieldStrUserID)
    {
        this.fieldStrTitle = fieldStrTitle;
        this.fieldStrDescription = fieldStrDescription;
        this.fieldStrImage = fieldStrImage;
        this.fieldStrTimeStamp = fieldStrTimeStamp;
        this.fieldStrUserID = fieldStrUserID;
    }

    public String getFieldStrTitle()
    {
        return fieldStrTitle;
    }

    public void setFieldStrTitle(String fieldStrTitle)
    {
        this.fieldStrTitle = fieldStrTitle;
    }

    public String getFieldStrDescription()
    {
        return fieldStrDescription;
    }

    public void setFieldStrDescription(String fieldStrDescription)
    {
        this.fieldStrDescription = fieldStrDescription;
    }

    public String getFieldStrImage()
    {
        return fieldStrImage;
    }

    public void setFieldStrImage(String fieldStrImage)
    {
        this.fieldStrImage = fieldStrImage;
    }

    public String getFieldStrTimeStamp()
    {
        return fieldStrTimeStamp;
    }

    public void setFieldStrTimeStamp(String fieldStrTimeStamp)
    {
        this.fieldStrTimeStamp = fieldStrTimeStamp;
    }

    public String getFieldStrUserID()
    {
        return fieldStrUserID;
    }

    public void setFieldStrUserID(String fieldStrUserID)
    {
        this.fieldStrUserID = fieldStrUserID;
    }
}
