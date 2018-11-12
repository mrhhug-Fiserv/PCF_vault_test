package com.fiserv.hug.vaultTester;

import java.net.URI;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

/**
 * @author michael.hug@fiserv.com
 * Fiserv Internal Software
 */

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {
    
    @Override
    public VaultEndpoint vaultEndpoint() {
	System.out.println(Main.vault);
	System.out.println(Main.vault);
        URI uri = URI.create(Main.vault);
	System.out.println("This is is: " + uri.toString());
	System.out.println(Main.vault);
	System.out.println(Main.vault);
        return VaultEndpoint.from(uri);
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(Main.token);
    }
}
