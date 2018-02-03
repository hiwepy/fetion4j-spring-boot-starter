/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.demo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import net.apexes.fetion4j.core.sipc.RequestMessage;
import net.apexes.fetion4j.core.sipc.Sipc;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.sipc.SipcMessageReader;
import net.apexes.fetion4j.core.util.XmlElement;

/**
 *
 * @author HeDYn<hedyn@foxmail.com>
 */
public class Fetion4jTest {

    private static final String GET_SYSTEM_CONFIG_URL = "http://nav.fetion.com.cn/nav/getsystemconfig.aspx";
    /**
     * 协议版本
     */
    //public static final String PROTOCOL_VERSION = "4.1.0740";
    public static final String PROTOCOL_VERSION = "4.0.0";
    private static final String PK = "B4621B7F3459D8345CD228A62F1BA50DD7C04F2AA0CEE6EAFC63077F4CC3C707AF9E28AD3CE2ABC1614D363EEA88965DB92B0D5B4D7312E9729ED215F9783328CC0A12FB1B1C874B970672C9963EAFD4BFE5D0F876EABE539AAA158D041CD0E4B8535496CFE98B8E8102452E2768716613BC18249F4E4C5DEE1E62C3996BCFC7010001";
    private static final String NONCE = "1DBA40C5337F33821E5B4C2C168A34CB";

    public static void main(String[] args) throws Exception {
        //getSystemConfig();
        //ssi();
        sipc();
        /*
        AuthGeneratorV4 auth = new AuthGeneratorV4(); 
        String passHex = PasswordEncrypterV4.encryptV4(472371591,"123456"); 
        //String aeskey = ConvertHelper.byte2HexStringWithoutSpace(DigestHelper.createAESKey());
        String aeskey = "16124BA186BE0868BD9215D60A4A4DD6A8F910AB601E0B4E0126DCEA26C41B6F";
        String response = auth.generate(PK, passHex, NONCE, aeskey);
        System.out.println("response:" + response);
        */
    }

    private static void getSystemConfig() throws Exception {
        URL url = new URL(GET_SYSTEM_CONFIG_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.addRequestProperty("User-Agent", "IIC2.0/PC " + PROTOCOL_VERSION);
        conn.addRequestProperty("Content-Type", "text/xml");
        conn.addRequestProperty("Connection", "Keep-Alive");
        conn.addRequestProperty("Cache-Control", "no-cache");

        String accountType = "mobile-no=\"15817120000\"";

        String content = "<config><user " + accountType + " /><client type=\"PC\" version=\""
                + PROTOCOL_VERSION + "\" platform=\"W5.1\" />"
                /*
                 * + "<servers version=\"0\" />" + "<service-no version=\"0\"
                 * /><parameters version=\"0\" />" + "<hints version=\"0\"
                 * /><http-applications version=\"0\" />" + "<client-config
                 * version=\"0\" /><services version=\"0\" />"
                //
                 */
                //*
                + "<servers version=\"183\" />"
                + "<service-no version=\"90\"/>"
                + "<parameters version=\"124\" />"
                + "<hint sversion=\"96\" />"
                + "<http-applications version=\"141\" />"
                + "<client-config version=\"272\" />"
                + "<services version=\"18\" />"
                //*/
                + "</config>";

        OutputStream out = conn.getOutputStream();
        out.write(content.getBytes());
        out.flush();
        InputStream in = conn.getInputStream();

        XmlElement xmlEl = new XmlElement();
        xmlEl.parseFromReader(new InputStreamReader(in));
        System.out.println(xmlEl.toString());
    }
    
    private static final String SSI_URL = "https://uid.fetion.com.cn/ssiportal/SSIAppSignInV4.aspx?"
            + "mobileno=15817120000&domains=fetion.com.cn%3bm161.com.cn%3bwww.ikuwa.cn&"
            + "v4digest-type=1&v4digest=7099C9FDDC2A561296869AD7C49776549868FE56";

    private static void ssi() throws Exception {
        URL doUrl = new URL(SSI_URL);
        HttpsURLConnection conn = (HttpsURLConnection) doUrl.openConnection();
        int status = conn.getResponseCode();
        System.out.println(status);
    }

    private static void sipc() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("221.176.31.225", 8080));
        
        startRead(socket);

        Random random = new Random();
        String cn = Integer.toHexString(random.nextInt()).toUpperCase();

        RequestMessage request = new RequestMessage(Sipc.METHOD_R);
        request.setCallId(1);
        request.setSequence(1);
        request.addField(Sipc.FIELD_F, "847570000");
        //request.addField(Sipc.FIELD_I, "1");
        //request.addField(Sipc.FIELD_Q, "1 R");
        request.addField(Sipc.FIELD_CN, "591C9C30");
        request.addField(Sipc.FIELD_CL, "type=\"PCSmart\",version=\"1.0.0000\"");
        
        System.out.print(request);

        OutputStream out = socket.getOutputStream();
        out.write(request.toString().getBytes());
        out.flush();
    }

    private static void startRead(final Socket socket) throws Exception {
        Runnable readRunner = new Runnable() {

            public void run() {
                try {
                    SipcMessageReader reader = new SipcMessageReader(socket.getInputStream());
                    
                    while (true) {
                        SipcMessage message = reader.read();
                        
                        if (message == null) {
                            System.out.println("Connection closed by server..");
                            break;
                        } else {
                            System.out.print(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread readThread = new Thread(readRunner);
        readThread.start();
    }

}