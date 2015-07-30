package com.mycompany.geocaller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.mycompany.geocaller.model.NumberLookupData;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@SpringBootApplication
@Controller
public class GeocallerApplication {
  
    private static final String NEXMO_NUMBER_API_URL = "https://api.nexmo.com/number/lookup/json?api_key={api_key}&api_secret={api_secret}&number={number}";
    private static final String NEXMO_SMS_API_URL = "https://rest.nexmo.com/sms/json?api_key={api_key}&api_secret={api_secret}&from={from}&to={to}&text={text}";
    
    @Value("${sendgrid.user}")
    private String sendGridUser;
    
    @Value("${sendgrid.password}")
    private String sendGridPassword;
    
    @Value("${nexmo.api.key}")
    private String nexmoApiKey;
    
    @Value("${nexmo.api.secret}")
    private String nexmoApiSecret;
    
    public static void main(String[] args) {
        SpringApplication.run(GeocallerApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplate();
    }
    
    @Bean
    public Map<String, Map<String, NumberLookupData>> clientPhoneData(){
      return new HashMap<String, Map<String, NumberLookupData>>();
    }
    
    @RequestMapping("/map/{client_id}")
    public String locateMyCaller() {
      return "find";  
    }
    
    
    @RequestMapping("/locate/{client_id}/{number}/lookup")
    @ResponseBody
    public int locate(@PathVariable("client_id") String clientId, @PathVariable("number") String number) {
      int status = 0;
      NumberLookupData response = restTemplate().getForObject(NEXMO_NUMBER_API_URL, NumberLookupData.class, nexmoApiKey,nexmoApiSecret,number);
      
      if(!response.getStatus().equals("0")){
        status = 1;
      }
      else {
        System.out.println(response.toString());
        if(clientPhoneData().containsKey(clientId)){
          clientPhoneData().get(clientId).put(number, response);
        }
        else{
          // brand new entry
          Map<String, NumberLookupData> numberDataMap = new HashMap<String, NumberLookupData>();
          
          numberDataMap.put(number, response);
     
          clientPhoneData().put(clientId, numberDataMap);
        }
        sendMail(clientId, response.toString());
      }
      System.out.println("Status is " + status);
      return status;
    }
    
    @RequestMapping("/locate/{client_id:.+}")
    @ResponseBody
    public Map<String, NumberLookupData> getNumberInfo(@PathVariable("client_id") String clientId){
      return clientPhoneData().get(clientId);
    }
    
    private void sendMail(String emailAddress, String message) {
      SendGrid sendgrid = new SendGrid(sendGridUser, sendGridPassword);

      SendGrid.Email email = new SendGrid.Email();
      email.addTo(emailAddress);
      email.setFrom("GeoCaller");
      email.setSubject("Caller Geolocation");
      email.setText(message);

      try {
        SendGrid.Response response = sendgrid.send(email);
        System.out.println(response.getMessage());
      }
      catch (SendGridException e) {
        System.out.println(e);
      }
    
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/text/{from}/{to}")
    @ResponseBody
    private void sendSMS(@PathVariable("from") String from, @PathVariable("to") String to, @RequestBody String message) {
      System.out.println("From is " + from + " and to is " + to + "Message is " + message);
      System.out.println(restTemplate().getForObject(NEXMO_SMS_API_URL, String.class, nexmoApiKey, nexmoApiSecret, from, to, message));
    }
}
