package com.example.mikael.mikaelhagfeldt_petshare;

/*
    En enkel klass för att "moddelera" ett element i bloggen. Denna klass används främst inom klass
    Adapter, som tar denna data och kopplar den samman med en layout (cardview). Klassen Adapter har en lista
    av Blog objekt.
 */

public class Blog
{
    private String fieldStrTitle;
    private String fieldStrRandomText;
    private String fieldStrImageText;
    private String fieldStrDateTime;
    private String fieldStrUserID;

    public Blog()
    {

    }

    public Blog(String fieldStrTitle, String fieldStrRandomText, String fieldStrImageText, String fieldStrDateTime, String fieldStrUserID)
    {
        this.fieldStrTitle = fieldStrTitle;
        this.fieldStrRandomText = fieldStrRandomText;
        this.fieldStrImageText = fieldStrImageText;
        this.fieldStrDateTime = fieldStrDateTime;
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

    public String getFieldStrRandomText()
    {
        return fieldStrRandomText;
    }

    public void setFieldStrRandomText(String fieldStrRandomText)
    {
        this.fieldStrRandomText = fieldStrRandomText;
    }

    public String getFieldStrImageText()
    {
        return fieldStrImageText;
    }

    public void setFieldStrImageText(String fieldStrImageText)
    {
        this.fieldStrImageText = fieldStrImageText;
    }

    public String getFieldStrDateTime()
    {
        return fieldStrDateTime;
    }

    public void setFieldStrDateTime(String fieldStrDateTime)
    {
        this.fieldStrDateTime = fieldStrDateTime;
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
