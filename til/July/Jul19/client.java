import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cmp.PKIMessage;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.cmp.GeneralPKIMessage;
import org.bouncycastle.cert.cmp.ProtectedPKIMessage;
import org.bouncycastle.cert.cmp.ProtectedPKIMessageBuilder;
import org.bouncycastle.cert.crmf.*;
import org.bouncycastle.cert.crmf.jcajce.JcePKMACValuesCalculator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.Security;
import java.util.Date;

public class client {
    public static void main(String[] args) {
        try
        {
            System.out.println("In...");
            final BigInteger certReqId = BigInteger.valueOf(1);
            final byte[] senderNonce = "12345".getBytes();
            final byte[] transactionId = "23456".getBytes();
            KeyPairGenerator kpi = KeyPairGenerator.getInstance("RSA");
            kpi.initialize(2048);
            KeyPair keyPair = kpi.generateKeyPair();

            // Now on to the CMP
            CertificateRequestMessageBuilder msgbuilder = new CertificateRequestMessageBuilder(certReqId);
            X500Name issuerDN = new X500Name("CN=ManagementCA");
            X500Name subjectDN = new X500Name("CN=Node123");
            msgbuilder.setIssuer(issuerDN);
            msgbuilder.setSubject(subjectDN);
            final byte[]                  bytes = keyPair.getPublic().getEncoded();
            final ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);
            final ASN1InputStream dIn = new ASN1InputStream(bIn);
            final SubjectPublicKeyInfo keyInfo = new SubjectPublicKeyInfo((ASN1Sequence)dIn.readObject());
            dIn.close();
            msgbuilder.setPublicKey(keyInfo);
            GeneralName sender = new GeneralName(subjectDN);
            msgbuilder.setAuthInfoSender(sender);

            // RAVerified POP
           // msgbuilder.setProofOfPossessionRaVerified();

            Control control = new RegTokenControl("mypassword");
            msgbuilder.addControl(control);
            Security.addProvider(new BouncyCastleProvider());
            Provider prov = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
            org.bouncycastle.operator.ContentSigner popsigner = new JcaContentSignerBuilder("SHA256withRSA").setProvider(prov).build(keyPair.getPrivate());
            msgbuilder.setProofOfPossessionSigningKeySigner(popsigner);

            CertificateRequestMessage msg = msgbuilder.build();
            org.bouncycastle.asn1.crmf.CertReqMessages msgs = new org.bouncycastle.asn1.crmf.CertReqMessages(msg.toASN1Structure());
            org.bouncycastle.asn1.cmp.PKIBody pkibody = new org.bouncycastle.asn1.cmp.PKIBody(org.bouncycastle.asn1.cmp.PKIBody.TYPE_INIT_REQ, msgs);

            // Message protection and final message
            GeneralName recipient = new GeneralName(issuerDN);
            ProtectedPKIMessageBuilder pbuilder = new ProtectedPKIMessageBuilder(sender, recipient);
            pbuilder.setMessageTime(new Date());

            // senderNonce
            pbuilder.setSenderNonce(senderNonce);

            // TransactionId
            pbuilder.setTransactionID(transactionId);

            // Key Id used (required) by the recipient to do a lot of stuff
            pbuilder.setSenderKID("KeyID".getBytes());
            pbuilder.setBody(pkibody);
            JcePKMACValuesCalculator jcePkmacCalc = new JcePKMACValuesCalculator();
            final AlgorithmIdentifier digAlg = new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.3.14.3.2.26")); // SHA1
            final AlgorithmIdentifier macAlg = new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.2.7")); // HMAC/SHA1
            jcePkmacCalc.setup(digAlg, macAlg);
            PKMACBuilder macbuilder = new PKMACBuilder(jcePkmacCalc);
            MacCalculator macCalculator = macbuilder.build("47GKM7h06sfl".toCharArray());
          //  ProtectedPKIMessage message = pbuilder.build(macCalculator);

            ContentSigner msgsigner = new JcaContentSignerBuilder("SHA1withRSA").setProvider(prov).build(keyPair.getPrivate());
            ProtectedPKIMessage message = pbuilder.build(msgsigner);

            PKIMessage pkiMessage = message.toASN1Structure();
            byte[] new_bytes = sendCmpHttp(pkiMessage.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] sendCmpHttp(byte[] message ) throws IOException, IOException {
        // POST the CMP request

        //final String urlString = "endpoint";
         final String urlString = "http://localhost:8280/ejbca/publicweb/cmp";

        URL url = new URL(urlString);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/pkixcmp");
        con.connect();
        // POST it
        OutputStream os = con.getOutputStream();
        os.write(message);
        os.close();


        System.out.println("httpRespCode: " + con.getResponseCode());
        System.out.println("Content Type: " + con.getContentType());
        System.out.println("CacheControl:" + con.getHeaderField("Cache-Control"));
        System.out.println("Pragma:" + con.getHeaderField("Pragma"));
        System.out.println("Pragma:" + con.getResponseMessage());
// use this
        ASN1InputStream is = new ASN1InputStream(new ByteArrayInputStream(con.getInputStream().readAllBytes()));

        PKIMessage pkiMessage = PKIMessage.getInstance(is.readObject());

        GeneralPKIMessage generalPKIMessage = new GeneralPKIMessage(pkiMessage.getEncoded());

        System.out.println(generalPKIMessage);


        // Now read in the bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // This works for small requests, and CMP requests are small enough
        InputStream in = con.getInputStream();
        int b = in.read();
        while (b != -1) {
            baos.write(b);
            b = in.read();
        }
        baos.flush();
        in.close();
        byte[] respBytes = baos.toByteArray();
        System.out.println(baos.toString());
        // is Null respBytes);
        // respBytes.length > 0
        return respBytes;
    }
}
