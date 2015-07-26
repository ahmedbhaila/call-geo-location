package com.mycompany.geocaller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.mycompany.geocaller.model.NumberLookupData;

@SpringBootApplication
@Controller
public class GeocallerApplication {

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
      NumberLookupData response = restTemplate().getForObject("https://api.nexmo.com/number/lookup/json?api_key=6cbfd7d3&api_secret=a203d2bd&number=" + number, NumberLookupData.class);
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
        sendMail(clientId);
      }
      System.out.println("Status is " + status);
      return status;
    }
    
    @RequestMapping("/locate/{client_id}")
    @ResponseBody
    public Map<String, NumberLookupData> getNumberInfo(@PathVariable("client_id") String clientId){
      return clientPhoneData().get(clientId);
    }
    
    private void sendMail(String email) {
      
    }
    
}
