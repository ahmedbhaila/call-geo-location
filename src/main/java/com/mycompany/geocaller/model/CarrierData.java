package com.mycompany.geocaller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierData {
  
  @Override
  public String toString() {
    return "CarrierData \n Name = " + name + "\nCountry = " + country + " \nType=" + type;
  }
  @JsonProperty("name")
  protected String name;
  
  @JsonProperty("country")
  protected String country;
  
  @JsonProperty("network_type")
  protected String type;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
}
