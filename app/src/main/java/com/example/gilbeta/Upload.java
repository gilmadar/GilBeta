package com.example.gilbeta;

public class Upload {
    private String Breed, SizeDog, City, FullName, PhoneNumber, Age;
    private boolean tame, Vaccinated;
    public Upload(){}
    public Upload( String Breed, String SizeDog, String City,  boolean tame, boolean Vaccinated, String Age){
        /*this.FullName = FullName;
        this.PhoneNumber = PhoneNumber;*/
        this.Breed = Breed;
        this.SizeDog = SizeDog;
        this.City = City;
        this.tame = tame;
        this.Vaccinated = Vaccinated;
        this.Age = Age;


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
    public Boolean setVaccinated(){
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
}
