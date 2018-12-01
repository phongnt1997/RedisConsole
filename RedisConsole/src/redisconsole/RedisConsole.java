/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redisconsole;

import com.google.gson.Gson;
import java.util.List;
import phongnt.Daos.ProductDAO;
import phongnt.model.Product;
import redis.clients.jedis.Jedis;

/**
 *
 * @author PhongNT
 */
public class RedisConsole {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Runnable redis = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ProductDAO dao = new ProductDAO();
                    List<Product> products = null;
                    try {
                        products = dao.getAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (products != null) {
                        Gson gson = new Gson();
                        Jedis jedis = new Jedis("localhost");
                        jedis.del("products");
                        for (Product st : products) {
                            String json = gson.toJson(st);
                            jedis.append("products", json + ",");
                            System.out.println(json);
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread redisThread  = new Thread(redis);
        redisThread.start();
    }

}
