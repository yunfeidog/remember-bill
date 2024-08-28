package com.cxk.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "/Users/houyunfei/文档/竞赛/服务外包大赛/源代码/keepaccount_back/src/main/java/com/cxk/test/ticket_receipt_acg_0.jpg";
        // 商铺小票识别
        String url = "https://api.textin.com/robot/v1.0/api/receipt";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-app-id
        // 示例代码中 x-ti-app-id 非真实数据
        String appId = "aaa";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-secret-code
        // 示例代码中 x-ti-secret-code 非真实数据
        String secretCode = "aaa";
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";
        try {
            byte[] imgData = readfile(path); // image
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            out = new DataOutputStream(conn.getOutputStream());
            out.write(imgData);
            out.flush();
            out.close();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(result);
    }

    public static byte[] readfile(String path) {
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
