package org.pasetoSeed;

import dev.paseto.jpaseto.Version;
import dev.paseto.jpaseto.lang.Keys;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.KeyPair;

@Data
public class secretSeed {
    private static final Logger logger = LoggerFactory.getLogger(secretSeed.class);
    public SecretKey key= null;
    public KeyPair keypair = null;
    public secretSeed()
    {

    }
    @PostConstruct
    public void generateSeed()
    {
        key = Keys.secretKey();
        keypair = Keys.keyPairFor(Version.V1);
    }
}
