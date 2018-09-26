package com.fiserv.hug.vaultTester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michael.hug@fiserv.com
 * Fiserv Internal Software
 */

@RestController("/api/vault")
public class WebController {
    
    @Autowired
    private VaultTemplate vaultTemplate;

    @PutMapping("/api/vault/{huntName}/{startingCity}/{numberOfPaces}")
    public void set(@PathVariable String huntName, @PathVariable String startingCity, @PathVariable String numberOfPaces) {
        TreasureHunt th = new TreasureHunt(startingCity, numberOfPaces);
        vaultTemplate.write(Main.root + "/" + huntName, th);
    }
    
    @GetMapping("/api/vault/{huntName}")
    public VaultResponseSupport<TreasureHunt> get(@PathVariable String huntName) {
        VaultResponseSupport<TreasureHunt> ret = vaultTemplate.read(Main.root + "/" + huntName, TreasureHunt.class);
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("", "");
        ret.setAuth(myMap);
        /*
        I am not exactly sure why but jackson exploded when auth was null
        {
    "timestamp": "2018-09-24T00:44:08.051+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Could not write JSON: Auth field is empty; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Auth field is empty (through reference chain: org.springframework.vault.support.VaultResponseSupport[\"requiredAuth\"])",
    "path": "/api/vault/MichaelsHunt"
}
2018-09-23 20:44:08.037  WARN 11032 --- [nio-8080-exec-3] .w.s.m.s.DefaultHandlerExceptionResolver : Failed to write HTTP message: org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Auth field is empty; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Auth field is empty (through reference chain: org.springframework.vault.support.VaultResponseSupport["requiredAuth"])
        */
        return ret;
    }

    @GetMapping("/api/vault/list")
    public List<String> listKeys() {
        return vaultTemplate.list(Main.root);
    }
    
    @DeleteMapping("/api/vault/{huntName}")
    public void deletHunt(@PathVariable String huntName) {
        vaultTemplate.delete(Main.root + "/" + huntName);
    }
    
    @GetMapping("/api/vault/vcap")
    public Map<String,String> listVcapVars() {
        Map<String, String> ret = new HashMap<>();
        ret.put("Vault Enpoint", Main.vault);
        ret.put("Vault Root Path", Main.root);
        ret.put("Vault Token", Main.token);
        return ret;
    }
    /*
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String regexPatternString = "\"token\":\".*?\"";
        Pattern p = Pattern.compile(regexPatternString);
        Matcher m = p.matcher(vcap_services);
        if (!m.find()) {
            System.err.println(vcap_services);
            System.err.println(regexPatternString);
            throw new NotYetBoundException();
        }
        headers.add("X-Vault-Token", m.group().replaceAll("\"", "").replace("token:", ""));
        return headers;
    }
    private String urlBase() {
        Pattern p = Pattern.compile("\"vault\":\".*?\"");
        Matcher m = p.matcher(vcap_services);
        if (!m.find()) {
            throw new NotYetBoundException();
        }
        return m.group().replaceAll("\"", "").replace("vault:", "");
    }
*/
}