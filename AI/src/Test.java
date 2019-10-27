import java.io.*;
import java.util.Scanner;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class Test {
        static String  username,password,student_number,student_password,url,token,card,answer,hosid;
        static JSONObject body = new JSONObject();    //注册绑定
        static JSONObject  number = new JSONObject(); //登录
        static JSONObject pcard = new JSONObject();  //出牌
        static int flag = 0,id,listen;
    // String URL = "url";
    public static void main(String[] args) throws InterruptedException {
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入1：登录");
            System.out.println("输入2：注册加绑定");

            int res = scanner.nextInt();
            if (res == 1){
                log();
            }
            if (res == 2){
                register();
            }

            while (flag == 1){
                System.out.println("输入1：开启战局");
                System.out.println("输入2：出牌");
                System.out.println("输入3：注销");
                System.out.println("输入4：排行榜");
                System.out.println("输入5：历史记录");
                res = scanner.nextInt();
                //res = 1;
                if (res == 1){
                    star();
                    res = 2;
                }
                if (res == 2){
                    submit();
                    Thread.sleep(600);
                }
                if (res == 3){
                    logout();
                }
                if (res == 4){
                    rank();
                }
                if (res == 5){
                    int testid = 1;
                    while (testid != 0){
                        history();
                        System.out.println("输入对局id查看详情(0退出)：");
                        testid = scanner.nextInt();
                        if (testid != 0){
                            hosid = String.valueOf(testid);
                            gethistory();
                        }
                        System.out.println("输入1 返回游戏大厅：");
                        System.out.println("输入2 返回历史记录：");
                        testid = scanner.nextInt();
                        if (testid == 1){
                            break;
                        }
                    }
                }
            }

        }
    }


    public static void register(){
        String res = "";
        int status;
        url = "http://api.revth.com/auth/register2";
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("请输入用户名：");
//        username = scanner.nextLine();
//        System.out.print("请输入密码：");
//        password = scanner.nextLine();
//        System.out.print("请输入教务处学号:");
//        student_number = scanner.nextLine();
//        System.out.print("请输入教务处密码:");
//        student_password = scanner.nextLine();

        body.put("username",username);
        body.put("password",password);
        body.put("student_number",student_number);
        body.put("student_password",student_password);

        res = post(body,url);

        org.json.JSONObject jsonObject= strtojson(res);

        status = jsonObject.getInt("status");
        listen = status;
        if (status == 0){
            System.out.println("注册成功！");
        }

        if (status == 1001){
            System.out.println("用户名已被使用");
        }

        if (status == 1002){
            System.out.println("学号已绑定");
        }

        if (status == 1003){
            System.out.println("教务处认证失败");
        }
    }    //注册+绑定

    public static void log(){
        url = "http://api.revth.com/auth/login";
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("请输入用户名：");
//        username = scanner.nextLine();
//        System.out.print("请输入密码：");
//        password = scanner.nextLine();

        number.put("username",username);
        number.put("password",password);

        String res = post(number,url);
        org.json.JSONObject object = strtojson(res);
        int status = object.getInt("status");

        if (status == 0){
            System.out.println("登录成功！");
            org.json.JSONObject object1 = object.getJSONObject("data");
            token = object1.getString("token");
            flag = 1;
            listen = 0;
        }

        if (status == 1005){
            System.out.println("用户名或密码错误!");
            listen = 1005;
        }
    }       //登录

    public static void logout(){
        url = "http://api.revth.com/auth/logout";
        String res = post(token,url);
        org.json.JSONObject strtojson = strtojson(res);

        int status = strtojson.getInt("status");
        if (status == 0){
            System.out.println("注销成功！");
            flag = 0;
        }
    }      //注销

    public static void star(){
        url = "http://api.revth.com/game/open";
        String res = "";
        res = post(token,url);
        org.json.JSONObject strtojson = strtojson(res);
        org.json.JSONObject data = strtojson.getJSONObject("data");

        card = data.getString("card");
        id = data.getInt("id");
    }   //开启战局

    public static void submit(){
        url = "http://api.revth.com/game/submit";
        answer = Main.trycard(card);
        pcard.put("id",id);
        pcard.put("card",answer);
        String str = submitcard(pcard,url);
        org.json.JSONObject strtojson = strtojson(str);

        int status = strtojson.getInt("status");
        if (status == 0){
            System.out.println("success!");
        }
    }   //出牌

    public static void rank(){
        url = "http://api.revth.com/rank";
        String res = get(url);
        System.out.println(res);
    } //查看排行榜

    public static void history(){
        url = "http://api.revth.com/history";
        String res = get(url,token);
        System.out.println(res);
    }

    public static void gethistory(){
        url = "http://api.revth.com/history/"+hosid;
        String res = get(url,token);
        System.out.println(res);
    }

    public static String post(JSONObject json,String URL) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            /*if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            }
            else {
                System.out.println("请求服务端失败");
            }*/
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
        return result;
    }       //post

    public static String get(String url){
        String result = "";
        HttpGet get = new HttpGet(url);
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(get);
            result = getHttpEntityContent(response);

            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                result = "服务器异常";
            }
        } catch (Exception e){
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally{
            get.abort();
        }
        return result;
    }               // get

    public static String get(String url,String history){
        String result = "";
        HttpGet get = new HttpGet(url);
        get.setHeader("X-Auth-Token",history);
        try{

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(get);
            result = getHttpEntityContent(response);

            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                result = "服务器异常";
            }
        } catch (Exception e){
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally{
            get.abort();
        }
        return result;
    }           //history

    public static String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException {
        String result = "";
        HttpEntity entity = response.getEntity();
        if(entity != null){
            InputStream in = null;
            try {
                in = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuilder strber= new StringBuilder();
            String line = null;
            while((line = br.readLine())!=null){
                strber.append(line+'\n');
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            in.close();
            result = strber.toString();
        }

        return result;

    }

    public static org.json.JSONObject strtojson(String str){
        org.json.JSONObject jo = new org.json.JSONObject(new String(str));
        return jo;
    }

    public static String post(String json,String URL){

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        post.setHeader("X-Auth-Token", json);
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            /*if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            }
            else {
                System.out.println("请求服务端失败");
            }*/
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String submitcard(JSONObject json,String URL){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        post.setHeader("X-Auth-Token", token);
        post.addHeader("Content-Type", "application/json");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            /*if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            }
            else {
                System.out.println("请求服务端失败");
            }*/
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
        return result;
    }
}
