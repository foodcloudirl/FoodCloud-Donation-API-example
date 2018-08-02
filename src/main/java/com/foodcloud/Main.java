package com.foodcloud;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.awt.*;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.text.html.parser.Entity;
import java.lang.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;

import static org.apache.http.protocol.HTTP.USER_AGENT;


//collection class
class Collection {
    private String MessageType;
    private String Retailer;
    private int StoreNumber;
    private String Date;
    private String CollectedBy;
    private String CollectedAt;
    private Product[] Products;

    //constructor
    public Collection(String _MessageType, String _Retailer, int _StoreNumber, String _Date, String _CollectedBy, String _CollectedAt, Product[] _Products) {
        System.out.println("Hello");
        MessageType = _MessageType;
        Retailer = _Retailer;
        StoreNumber = _StoreNumber;
        Date = _Date;
        CollectedBy = _CollectedBy;
        CollectedAt = _CollectedAt;
        Products = _Products;
    }

    //getters and setters
    public String getMessageType(){
        return MessageType;
    }
    public void setMessageType(String MessageType) {
        this.MessageType = MessageType;
    }


    public String getRetailer(){
        return Retailer;
    }
    public void setRetailer(String Retailer) {
        this.Retailer = Retailer;
    }


    public int getStoreNumber(){
        return StoreNumber;
    }
    public void setStoreNumber(int StoreNumber) {
        this.StoreNumber = StoreNumber;
    }


    public String getDate(){
        return Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }


    public String getCollectedBy(){
        return CollectedBy;
    }
    public void setCollectedBy(String CollectedBy) {
        this.CollectedBy = CollectedBy;
    }


    public String getCollectedAt(){
        return CollectedAt;
    }
    public void setCollectedAt(String CollectedAt) {
        this.CollectedAt = CollectedAt;
    }

}


//product class
class Product {
    private String Name;
    private int Code;
    private int Quantity;
    private String Currency;
    private String Weight;
    private String Category;
    private String AuthPrice;
    private String FinalPrice;

    //constructor
    public Product(String _Name, int _Code, int _Quantity, String _Currency, String _Weight, String _Category, String _AuthPrice, String _FinalPrice) {
        System.out.println("Yo");
        Name = _Name;
        Code = _Code;
        Quantity = _Quantity;
        Currency = _Currency;
        Weight = _Weight;
        Category = _Category;
        AuthPrice = _AuthPrice;
        FinalPrice = _FinalPrice;
    }

    //getters and setters
    public String getName(){
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }


    public int getCode(){
        return Code;
    }
    public void setCode(int Code) {
        this.Code = Code;
    }


    public int getQuantity(){
        return Quantity;
    }
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }


    public String getCurrency(){
        return Currency;
    }
    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }


    public String getWeight(){
        return Weight;
    }
    public void setWeight(String Weight) {
        this.Weight = Weight;
    }


    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category) {
        this.Category = Category;
    }


    public String getAuthPrice(){
        return AuthPrice;
    }
    public void setAuthPrice(String AuthPrice) {
        this.AuthPrice = AuthPrice;
    }


    public String getFinalPrice(){
        return FinalPrice;
    }
    public void setFinalPrice(String FinalPrice) {
        this.FinalPrice = FinalPrice;
    }

}


public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to FoodCloud! \n We believe in a world where no good food goes to waste. \n");

        //setting up the url to get info
        HttpGet get = new HttpGet("https://jsonplaceholder.typicode.com/posts/1");
        System.out.println(get.getURI());
        //CloseebleHttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //request the info
            CloseableHttpResponse response = client.execute(get);
            try {
                //checking if the request worked
                System.out.println(response.getStatusLine());
                //get the response
                HttpEntity r = response.getEntity();
                //show the response as a string
                String s = EntityUtils.toString(response.getEntity());
                System.out.println(s);

                //how to turn this InputStream into a JSONObject
                JSONObject result= new JSONObject(s);

                System.out.println(result);

                //get one value specifically
                Integer secondResult = result.getInt("userId");

                System.out.println(secondResult);
                sendCollectionData();
            }
            finally {
                response.close();
            }
        }
        catch (IOException ec) {
            System.out.println(ec);
        }

    }

    public static void sendCollectionData() throws IOException {

        Product p = new Product("Baker Pat White Bread", 1234567890, 20, "GBP, UOM, : EA", "0.15", "Bakery; Bread", "2.0", "3.0");
        System.out.println(p.getName());
        System.out.println(p.getCode());
        System.out.println(p.getQuantity());
        System.out.println(p.getCurrency());
        System.out.println(p.getWeight());
        System.out.println(p.getCategory());
        System.out.println(p.getAuthPrice());
        System.out.println(p.getFinalPrice());

        //initialising product array
        Product[] products = {p};

        Collection c = new Collection("DonationCollected", "TESCO", 1234, "2016-05-04T13:15:54.423378+00:00", "John", "2016-05-04T13:15:54.423378+00:00", products);
        System.out.println(c.getMessageType());
        System.out.println(c.getDate());
        System.out.println(c.getRetailer());
        System.out.println(c.getStoreNumber());

        //turning collection data into json
        Gson gson = new Gson();
        String json = gson.toJson(c);
        System.out.println(json);
        sendPost(json);
    }

    // HTTP POST request
    private static void sendPost(String json) throws IOException {

        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost("https://plaza-c.food.cloud/donation/collection");
        postMethod.setEntity(requestEntity);

        CloseableHttpClient client = HttpClients.createDefault();

        try {
            //request the info
            CloseableHttpResponse response = client.execute(postMethod);
            try {
                //checking if the request worked
                System.out.println(response.getStatusLine());
                String s = EntityUtils.toString(response.getEntity());
                System.out.println(s);
            } finally {
                response.close();
            }
        }
        catch (IOException ec) {
            System.out.println(ec);
        }

    }
}