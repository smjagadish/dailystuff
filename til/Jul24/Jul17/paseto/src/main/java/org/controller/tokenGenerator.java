package org.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paseto.jpaseto.Claims;
import dev.paseto.jpaseto.PasetoException;
import dev.paseto.jpaseto.Pasetos;
import dev.paseto.jpaseto.io.DeserializationException;
import dev.paseto.jpaseto.io.Deserializer;
import org.claimModel.claim;
import org.claimModel.localClaimBuilder;
import org.clientModel.sharedTokenModel;
import org.pasetoSeed.secretSeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class tokenGenerator {
    private static final Logger logger = LoggerFactory.getLogger(tokenGenerator.class);
    @Autowired
    public secretSeed seed;
    @GetMapping(path = "/default", produces = "application/json")
    public ResponseEntity<String> getDefaultToken() {
        logger.info("processing request for default local token");
        if (seed == null || seed.getKey() == null) {
            logger.error("Secret seed or key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or key is null");
        }
        String token = Pasetos.V1.LOCAL.builder()
                .setSubject("Joe")
                .setSharedSecret(seed.getKey())
                .compact();
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

    @GetMapping(path = "/shared", produces = "application/json")
    public ResponseEntity<String> getSharedToken() {
        logger.info("processing request for default shared token");
        if (seed == null || seed.getKeypair() == null || seed.getKeypair().getPrivate() == null) {
            logger.error("Secret seed or keypair is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or keypair is null");
        }
        String token = Pasetos.V1.PUBLIC.builder()
                .setSubject("app")
                .setAudience("aud1")
                .setPrivateKey(seed.getKeypair().getPrivate())
                .compact();
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

    @GetMapping(path = "/publickey", produces = "application/json")
    public ResponseEntity<String> getpublicKey() {
        if (seed == null || seed.getKeypair() == null || seed.getKeypair().getPublic() == null) {
            logger.error("Secret seed or public key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or public key is null");
        }
        PublicKey key = seed.getKeypair().getPublic();
        logger.info(key.getAlgorithm());
        String res = Base64.getEncoder().encodeToString(key.getEncoded());
        return ResponseEntity.of(Optional.of(res));
    }

    @GetMapping(path = "/local", produces = "application/json")
    public ResponseEntity<String> getlocalKey() {
        logger.info("processing request for default local token");
        if (seed == null || seed.getKey() == null) {
            logger.error("Secret seed or key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or key is null");
        }
        localClaimBuilder claimBuilder = localClaimBuilder.builder()
                .app_version(1)
                .app_Zone("az1")
                .app_Name("first_app")
                .build();
        String token = Pasetos.V1.LOCAL.builder()
                .setSubject("Joe")
                .setSharedSecret(seed.getKey())
                .claim("claim_info", claimBuilder)
                .compact();
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

    @GetMapping(path = "/v2/local", produces = "application/json")
    public ResponseEntity<String> getlocalV2() {
        logger.info("processing request for default local token with extra claims");
        if (seed == null || seed.getKey() == null) {
            logger.error("Secret seed or key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or key is null");
        }
        String token = Pasetos.V1.LOCAL.builder()
                .setSubject("Joe")
                .setAudience("appv2")
                .setExpiration(Instant.now())
                .setSharedSecret(seed.getKey())
                .claim("c1", "v1")
                .claim("c2", "v2")
                .compact();
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

    @PostMapping(path = "/v2/validateLocal", produces = "application/json")
    public ResponseEntity<String> validateLocalV2(@RequestBody String token) {
        logger.info("validating v2 local with extra claims");
        if (seed == null || seed.getKey() == null) {
            logger.error("Secret seed or key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or key is null");
        }
        try {
            Claims claims = Pasetos.parserBuilder()
                    .setSharedSecret(seed.getKey())
                    .setAllowedClockSkew(Duration.ofMinutes(2))
                    .build()
                    .parse(token)
                    .getClaims();
            claims.entrySet()
                    .forEach(e -> logger.info("key:" + e.getKey() + " with value:" + e.getValue()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body("validated claims successfully");
        } catch (PasetoException e) {
            logger.info(e.getLocalizedMessage());
        }
        return ResponseEntity.of(Optional.of("validation failed"));
    }

    @PostMapping(path = "/validateLocal", produces = "application/json")
    public ResponseEntity<String> validateLocal(@RequestBody String token) {
        logger.info("validating");
        if (seed == null || seed.getKey() == null) {
            logger.error("Secret seed or key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or key is null");
        }
        try {
            Claims claims = Pasetos.parserBuilder().setSharedSecret(seed.getKey())
                    .setDeserializer(new Deserializer<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> deserialize(byte[] bytes) throws DeserializationException {
                            try {
                                claim claimData = new ObjectMapper().readValue(bytes, claim.class);
                                return Map.of("claim_info", claimData);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
                    .build()
                    .parse(token)
                    .getClaims();
            claim obj = claims.get("claim_info", claim.class);
            logger.info(obj.getClaim_info().toString());
            return ResponseEntity.of(Optional.of("validation successful"));
        } catch (PasetoException e) {
            logger.info(e.getLocalizedMessage());
        }
        return ResponseEntity.of(Optional.of("validation failed"));
    }

    @PostMapping(path = "/validateShared", produces = "application/json")
    public ResponseEntity<String> validateSharedToken(@RequestBody sharedTokenModel data) {
        try {
            String keys = data.getPkey();
            byte[] publicKeyBytes = Base64.getDecoder().decode(keys);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // Or the appropriate algorithm
            PublicKey key = keyFactory.generatePublic(publicKeySpec);
            Pasetos.parserBuilder()
                    .setPublicKey(key)
                    .build()
                    .parse(data.getToken())
                    .getClaims()
                    .getSubject()
                    .equals("app");
            return ResponseEntity.of(Optional.of("validated claims successfully"));
        } catch (PasetoException e) {
            logger.info(e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.of(Optional.of("claim validation failed"));
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<String> validateDefaultToken(@RequestBody String token) {
        if (seed == null || seed.getKey() == null) {
            logger.error("Secret seed or key is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Secret seed or key is null");
        }
        try {
            boolean res = Pasetos.parserBuilder()
                    .setSharedSecret(seed.getKey())
                    .build()
                    .parse(token)
                    .getClaims()
                    .getSubject()
                    .equals("Joe");
            if (res)
                return ResponseEntity.of(Optional.of("validated claims successfully"));
        } catch (PasetoException e) {
            logger.info(e.getLocalizedMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("validation failed");
    }

}
