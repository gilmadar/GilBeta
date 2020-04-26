package com.example.gilbeta;

import android.graphics.Bitmap;
import android.net.Uri;

public class Upload {
    private String Breed, SizeDog, City, FullName, PhoneNumber, Age, Email, Description,DogName, UID;
    private boolean tame, Vaccinated,act;
    Long SerialNumbe;
    long viewers;
    public Upload(){}
    public Upload(String Breed, String SizeDog, String City, boolean tame, boolean Vaccinated,
                  String Age, String FullName, String PhoneNumber, String Email,
                  String Description, String DogName, String UID, Long SerialNumbe, boolean act, long viewers){
        this.FullName = FullName;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Breed = Breed;
        this.SizeDog = SizeDog;
        this.City = City;
        this.tame = tame;
        this.Vaccinated = Vaccinated;
        this.Age = Age;
        this.Description = Description;
        this.DogName = DogName;
        this.UID = UID;
        this.SerialNumbe = SerialNumbe;
        this.act = act;
        this.viewers = viewers;


    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getSizeDog() {
        return SizeDog;
    }

    public void setSizeDog(String sizeDog) {
        SizeDog = sizeDog;
    }
    public Boolean gettame(){
        return tame;
    }

    public void setTame(boolean tame) {
        this.tame = tame;
    }
    public Boolean getVaccinated(){
        return Vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        Vaccinated = vaccinated;
    }


    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDogName() {
        return DogName;
    }

    public void setDogName(String dogName) {
        DogName = dogName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Long getSerialNumbe() {
        return SerialNumbe;
    }

    public void setSerialNumbe(Long serialNumbe) {
        SerialNumbe = serialNumbe;
    }

    public boolean isAct() {
        return act;
    }

    public void setAct(boolean act) {
        this.act = act;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public void copyUpload(Upload upload){
        this.FullName = upload.getFullName();
        this.PhoneNumber = upload.getPhoneNumber();
        this.Email = upload.getEmail();
        this.Breed = upload.getBreed();
        this.SizeDog = upload.getSizeDog();
        this.City = upload.getCity();
        this.tame = upload.gettame();
        this.Vaccinated = upload.getVaccinated();
        this.Age = Age;
        this.Description = upload.getDescription();
        this.DogName = upload.getDogName();
        this.UID = upload.getUID();
        this.SerialNumbe = upload.getSerialNumbe();

    }
}
