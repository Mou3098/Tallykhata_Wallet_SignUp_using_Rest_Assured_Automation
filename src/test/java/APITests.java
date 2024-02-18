import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class APITests {

    private static final Properties properties = new Properties();
    static {
        try {
            // Load properties from application.properties file
            FileInputStream fileInputStream = new FileInputStream("src/test/java/application.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   // private String wallet_no="01787132671";
   String wallet_no = properties.getProperty("wallet_no");
    private String Token_glob;
    @Test
    void ResetNID(){

        Response response = get("http://10.9.0.77:6060/tallypay-backdoor-service/api/backend/nid/6904235154/reset-nid");

        System.out.println("Response : "+response.asString());
        System.out.println("Status Code : "+response.getStatusCode());
        System.out.println("Body : "+response.getBody().asString());
        System.out.println("Time Taken : "+response.getTime());
        System.out.println("Header : "+response.getHeader("content-type"));

        int statusCode= response.getStatusCode();
        Assert.assertEquals(statusCode,200);

    }
    @Test
    void ResetNID2(){
        given().
                get("http://10.9.0.77:6060/tallypay-backdoor-service/api/backend/nid/6904235154/reset-nid").
                then().
                statusCode(200);
    }


    @Test
    void signUp() throws ParseException, JSONException {
        JSONObject requestBody = new JSONObject();



        requestBody.put("device_id", "ed28056ae2cb48e5");
        requestBody.put("mobile_number", wallet_no);
        requestBody.put("name", wallet_no);
        requestBody.put("pin", "PIN");
        requestBody.put("role", "CUSTOMER");
        requestBody.put("uuid", "9d690ba4-8aa4-42c0-bc2c-2a666456b853");

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", "Basic cHJvZ290aV9xYTpwcjBnMHQxQDIwMnR3bw==").
                header("x-auth-token", "3MKhY5EZWZoZSemZpyWgyf8fKqLm8s9JYzAal6jb").
                header("x-device-id", "9d690ba4-8aa4-42c0-bc2c-2a666456b853").
                header("x-user-mobile", wallet_no).
                body(requestBody.toString()).
                when().
                post("https://stgqa.tallykhata.com/wallet/api/tp-proxy/wallet/signup");

        String signUpResponse = response.getBody().asString();

        System.out.println(signUpResponse);

        Assert.assertEquals(200, response.getStatusCode());

        JsonPath res=response.jsonPath();
        String token=res.get("token");
        System.out.println("token : "+token);
        Token_glob=token;
    }

    @Test
    void szNID_front_Pic() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();

        //String front_pic = new String(Files.readAllBytes(Paths.get("D://m/front.txt")));// to get data from local device
        String front_pic = properties.getProperty("nid_front");


        //requestBody.put("nidNo", null);
        requestBody.put("nidType", "LEGACY_NID");
        requestBody.put("requestId", wallet_no);
        requestBody.put("photoFrontSide",front_pic);

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", Token_glob).
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("User-Agent", "okhttp/4.9.2").
                header("np-uuid", "b0682f69-5feb-42ed-9758-072973b6ea94").
                header("Host", "stgnpapigw.nobopay.com").
                header("np-androidid", "ed28056ae2cb48e5").
                header("np-androididversion", "33").
                header("np-appversion", "6.3.1-test").
                header("np-appversioncode", "157").
                header("np-devicemodel", "M2101K7BI").
                body(requestBody.toString()).
                when().
                post("https://stgnpapigw.nobopay.com/api/v1/user/doc/nid-front");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    void szNID_z_back_Pic() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();

        //String back_pic = new String(Files.readAllBytes(Paths.get("D://m/back.txt")));
        String back_pic = properties.getProperty("nid_back");

        requestBody.put("nidNo", "6904235154");
        requestBody.put("nidType", "LEGACY_NID");
        requestBody.put("requestId", wallet_no);
        requestBody.put("photoBackSide",back_pic);

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", Token_glob).
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("User-Agent", "okhttp/4.9.2").
                header("np-uuid", "b0682f69-5feb-42ed-9758-072973b6ea94").
                header("Host", "stgnpapigw.nobopay.com").
                header("np-androidid", "ed28056ae2cb48e5").
                header("np-androididversion", "33").
                header("np-appversion", "6.3.1-test").
                header("np-appversioncode", "157").
                header("np-devicemodel", "M2101K7BI").
                body(requestBody.toString()).
                when().
                post("https://stgnpapigw.nobopay.com/api/v1/user/doc/nid-back");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    void szz_confirm_NID() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("addressPermanent", "Union: à¦ªà¦²à¦¾à¦¶à¦¬à¦¾à§œà§€, Upazilla: à¦ªà¦¾à¦°à§�à¦¬à¦¤à§€à¦ªà§�à¦°, Post-Code: à§«à§¨à§«à§¦, Post-Office: à¦†à¦‡à¦¨à§�à¦²à¦¹à§�à¦¦à¦¾, District: à¦¦à¦¿à¦¨à¦¾à¦œà¦ªà§�à¦°, Division: à¦°à¦‚à¦ªà§�à¦°,");
        requestBody.put("addressPresent", "Union: à¦ªà¦²à¦¾à¦¶à¦¬à¦¾à§œà§€, Upazilla: à¦ªà¦¾à¦°à§�à¦¬à¦¤à§€à¦ªà§�à¦°, Post-Code: à§«à§¨à§«à§¦, Post-Office: à¦†à¦‡à¦¨à§�à¦²à¦¹à§�à¦¦à¦¾, District: à¦¦à¦¿à¦¨à¦¾à¦œà¦ªà§�à¦°, Division: à¦°à¦‚à¦ªà§�à¦°,");
        requestBody.put("birthday", "10/10/1999");
        //requestBody.put("photoFrontSide",front_pic);
        requestBody.put("bloodGroup","B+");
        requestBody.put("customerNameBn","à¦®à§‹à¦¸à§�à¦¤à¦¾à¦°à¦¿à¦¨à¦¾ à¦®à¦¿à¦¤à§�");
        requestBody.put("customerNameEn","Mostarina Mitu");
        requestBody.put("fatherName","à¦®à§‹à¦ƒ à¦®à§‹à¦›à¦¾à¦¦à§�à¦¦à§‡à¦• à¦¹à§‹à¦¸à§‡à¦¨");
        requestBody.put("motherName","à¦¨à¦¾à¦¸à¦°à¦¿à¦¨ à¦¬à¦¾à¦¨à§�");
        requestBody.put("nidNo", "6904235154");

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", Token_glob).
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("User-Agent", "okhttp/4.9.2").
                header("np-uuid", "b0682f69-5feb-42ed-9758-072973b6ea94").
                header("Host", "stgnpapigw.nobopay.com").
                header("np-androidid", "ed28056ae2cb48e5").
                header("np-androididversion", "33").
                header("np-appversion", "6.3.1-test").
                header("np-appversioncode", "157").
                header("np-devicemodel", "M2101K7BI").
                body(requestBody.toString()).
                when().
                post("https://stgnpapigw.nobopay.com/api/v1/user/doc/confirm-nid-info");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }
    @Test
    void szz_face_pic() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();
        //String face_pic = new String(Files.readAllBytes(Paths.get("D://m/face.txt")));
        String face_pic = properties.getProperty("profile_picture");

        requestBody.put("profilePicture",face_pic);
        requestBody.put("requestId",wallet_no);
        requestBody.put("nidNo", "6904235154");

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", Token_glob).
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("User-Agent", "okhttp/4.9.2").
                header("np-uuid", "b0682f69-5feb-42ed-9758-072973b6ea94").
                header("Host", "stgnpapigw.nobopay.com").
                header("np-androidid", "ed28056ae2cb48e5").
                header("np-androididversion", "33").
                header("np-appversion", "6.3.1-test").
                header("np-appversioncode", "157").
                header("np-devicemodel", "M2101K7BI").
                body(requestBody.toString()).
                when().
                post("https://stgnpapigw.nobopay.com/api/v1/user/doc/face-image");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    void szz_pin_set() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("new_pin","1000");
        requestBody.put("wallet_no",wallet_no);
        requestBody.put("uuid", "bd5b6dbd-f479-433e-8fb3-d4eda8fa5906");

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", "Basic cHJvZ290aV9xYTpwcjBnMHQxQDIwMnR3bw==").
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("User-Agent", "PostmanRuntime/7.36.3").
                header("x-auth-token", "TlLc3wNWnad8aCiaBLVp3GpSFxWIhb1JleoPBbdZ").
                header("x-device-id", "bd5b6dbd-f479-433e-8fb3-d4eda8fa5906").
                header("x-user-mobile", wallet_no).
                body(requestBody.toString()).
                when().
                put("https://stgqa.tallykhata.com/wallet/api/tp-proxy/pin/set");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }


    @Test
    void szz_pz_login() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("password","1000");
        requestBody.put("mobile_number",wallet_no);
        requestBody.put("uuid", "bd5b6dbd-f479-433e-8fb3-d4eda8fa5906");
        requestBody.put("app_type","TALLYKHATA");
        requestBody.put("device_id","ed28056ae2cb48e5");
        requestBody.put("device_type","ANDROID");


        Response response = given().
                header("content-type", "application/json").
                header("Authorization", "Basic cHJvZ290aV9xYTpwcjBnMHQxQDIwMnR3bw==").
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("x-auth-token", "TlLc3wNWnad8aCiaBLVp3GpSFxWIhb1JleoPBbdZ").
                header("x-device-id", "bd5b6dbd-f479-433e-8fb3-d4eda8fa5906").
                header("x-user-mobile", wallet_no).
                body(requestBody.toString()).
                when().
                post("https://stgqa.tallykhata.com/wallet/api/tp-proxy/v2/user/login");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    void szz_pzz_MFS_bkash() throws JSONException, IOException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("otpVerified",true);
        requestBody.put("wallet_no",wallet_no);
        requestBody.put("accountType", "MFS");
        requestBody.put("account_type","MFS");
        requestBody.put("mfs_type","BKASH");

        Response response = given().
                header("content-type", "application/json").
                header("Authorization", Token_glob).
                header("X-Request-Id", "ed28056ae2cb48e5:TK_ANDROID:222542882762956").
                header("np-uuid", "147b99fd-f040-4617-8cf4-4f39cf46c32a").
                body(requestBody.toString()).
                when().
                post("https://stgnpapigw.nobopay.com/api/v1/account");

        String Response = response.getBody().asString();

        System.out.println(Response);

        Assert.assertEquals(200, response.getStatusCode());
    }


 //   @Test
//    /*void test3(){
//        Response response;
//       response = given()
//               .contentType("application/json")
//                .header("Authorization","Basic cHJvZ290aV9xYTpwcjBnMHQxQDIwMnR3bw==")
//                .header("X-auth-token","3MKhY5EZWZoZSemZpyWgyf8fKqLm8s9JYzAal6jb")
//                .header("X-device-id","9d690ba4-8aa4-42c0-bc2c-2a666456b853")
//                .header("X-user-mobile","01724485400")
//                .header("Connection","keep-alive")
//                .body("{\n" +
//                        "\"device_id\":\"ed28056ae2cb48e5\",\n" +
//                        "\"mobile_number\":\"01724485400\",\n" +
//                        "\"name\":\"01724485400\",\n" +
//                        "\"pin\":\"PIN\",\n" +
//                        "\"role\":\"CUSTOMER\",\n" +
//                        "\"uuid\":\"9d690ba4-8aa4-42c0-bc2c-2a666456b853\"\n" +
//                        "}")
//                .when()
//                .post("https://stgqa.tallykhata.com/wallet/api/tp-proxy/wallet/signup")
//                .then()
//                .assertThat().statusCode(400).extract().response();
//
//        JsonPath res=response.jsonPath();
//        String token=res.get("token");
//        System.out.println("Body : "+response.getBody().asString());
//        System.out.println("token : "+token);
//
//
//
//
//
//
//    }*/



}
