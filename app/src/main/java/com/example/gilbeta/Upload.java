package com.example.gilbeta;

public class Upload {
    private String Breed, SizeDog, City, FullName, PhoneNumber, Age, Email, Description;
    private boolean tame, Vaccinated;
    public Upload(){}
    public Upload( String Breed, String SizeDog, String City,  boolean tame, boolean Vaccinated, String Age, String FullName, String PhoneNumber, String Email, String Description){
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
}
