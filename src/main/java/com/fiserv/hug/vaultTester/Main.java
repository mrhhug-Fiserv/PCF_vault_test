package com.fiserv.hug.vaultTester;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static String vault = "http://localhost:8200";
    public static String token = "UNABLE-TO-PARSE-VCAP_SERVICES";
    public static String root = "UNABLE-TO-PARSE-VCAP_SERVICES";
    
    public static void main(String[] args) {
        try {
            parseVcapSerices();
        } catch (NullPointerException e) {
            System.out.println("This test app is gonna fail, but at least let it fail with a GUI");
            //e.printStackTrace();
        }
        System.out.println("vault: " + vault);
        System.out.println("token: " + token);
        System.out.println("root: " + root);
        SpringApplication.run(Main.class, args);
    }
    private static void parseVcapSerices() {
        String vcap_services = System.getenv("VCAP_SERVICES");
        JsonObject o = new JsonParser().parse(vcap_services).getAsJsonObject();
        String vaultKeyName = null;
        Set<String> keys = o.keySet();
        for( String i : keys) {
            if( i.toLowerCase().contains("vault")) {
                vaultKeyName = i;
            }
        }
        JsonObject creds = o.get(vaultKeyName)
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("credentials")
                .getAsJsonObject();
        try {
            vault = creds.get("vault").getAsString();
        } catch (NullPointerException e) {
            //are you using the hashicorp broker?
            vault = creds.get("address").getAsString();
        }
        try {
            root = creds.get("root").getAsString();
        } catch (NullPointerException e) {
            //are you using the hashicorp broker?
            root = creds.get("backends").getAsJsonObject().get("generic").getAsString();
        }
        try {
            token = creds.get("token").getAsString();
        } catch (NullPointerException e) {
            //are you using the hashicorp broker?
            token = creds.get("auth").getAsJsonObject().get("token").getAsString();
        }
    }
}
