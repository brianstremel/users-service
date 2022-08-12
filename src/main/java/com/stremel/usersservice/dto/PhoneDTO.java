package com.stremel.usersservice.dto;

public class PhoneDTO {

    private String number;
    private String citycode;
    private String countrycode;

    public PhoneDTO() {}

    public PhoneDTO(final String number, final String citycode, final String countrycode) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }

    public String getNumber() {
        return number;
    }

    public String getCitycode() {
        return citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }
}
