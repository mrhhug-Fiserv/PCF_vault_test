package com.fiserv.hug.vaultTester;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static String vault = "UNABLE TO PARSE VCAP_SERVICES";
    public static String token = "UNABLE TO PARSE VCAP_SERVICES";
    public static String root = "UNABLE TO PARSE VCAP_SERVICES";
    
    public static void main(String[] args) {
        try {
            parseVcapSerices();
        } catch (NullPointerException e) {
            System.out.println("This test app is gonna fail, but at least let it fail with a GUI");
            e.printStackTrace();
        }
        SpringApplication.run(Main.class, args);
    }
    private static void parseVcapSerices() {
        JsonObject o = new JsonParser().parse(System.getenv("VCAP_SERVICES")).getAsJsonObject();
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
        vault = creds.get("vault").getAsString();
        token = creds.get("token").getAsString();
        root = creds.get("root").getAsString();
        System.out.println("vault: " + vault);
        System.out.println("token: " + token);
        System.out.println("root: " + root);
    }
}
