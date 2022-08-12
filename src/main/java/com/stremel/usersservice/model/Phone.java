package com.stremel.usersservice.model;

import com.stremel.usersservice.dto.PhoneDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String number;
    private String citycode;
    private String countrycode;

    public Phone(){};
    public Phone(final long id, final String number, final String citycode, final String countrycode) {
        this.id = id;
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }

    public Phone(final PhoneDTO phoneDTO) {
        this.number = phoneDTO.getNumber();
        this.citycode = phoneDTO.getCitycode();
        this.countrycode = phoneDTO.getCountrycode();
    }

    // Getters & Setters


    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(final String citycode) {
        this.citycode = citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(final String countrycode) {
        this.countrycode = countrycode;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", citycode='" + citycode + '\'' +
                ", countrycode='" + countrycode + '\'' +
                '}';
    }
}
