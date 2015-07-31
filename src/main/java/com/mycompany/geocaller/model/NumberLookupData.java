package com.mycompany.geocaller.model;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumberLookupData {
  
  @JsonProperty("status")
  protected String status;
  
  @JsonProperty("status_message")
  protected String statusMessage;
  
  @JsonProperty("international_format_number")
  protected String intlNumberFormat;
  
  @JsonProperty("national_format_number")
  protected String ntlNumberFormat;
  
  @JsonProperty("country_code")
  protected String countryCode;
  
  @JsonProperty("country_prefix")
  protected String countryPrefix;
  
  @Override
  public String toString() {
    return "International Number Format = " + intlNumberFormat + " - " + ntlNumberFormat
        + "\n Country Code = " + countryCode + "\nCountry Prefix = " + countryPrefix + "\n Carrier Related Data= "
        + carrierData.toString();
  }

  @JsonProperty("current_carrier")
  protected CarrierData carrierData;
  
  @JsonProperty("original_carrier")
  protected CarrierData originalCarrier;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getIntlNumberFormat() {
    return intlNumberFormat;
  }

  public void setIntlNumberFormat(String intlNumberFormat) {
    this.intlNumberFormat = intlNumberFormat;
  }

  public String getNtlNumberFormat() {
    return ntlNumberFormat;
  }

  public void setNtlNumberFormat(String ntlNumberFormat) {
    this.ntlNumberFormat = ntlNumberFormat;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryPrefix() {
    return countryPrefix;
  }

  public void setCountryPrefix(String countryPrefix) {
    this.countryPrefix = countryPrefix;
  }

  public CarrierData getCarrierData() {
    return carrierData;
  }

  public void setCarrierData(CarrierData carrierData) {
    this.carrierData = carrierData;
  }

  public CarrierData getOriginalCarrier() {
    return originalCarrier;
  }

  public void setOriginalCarrier(CarrierData originalCarrier) {
    this.originalCarrier = originalCarrier;
  }
  
  
}
