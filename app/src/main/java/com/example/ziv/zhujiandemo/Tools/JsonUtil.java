package com.example.ziv.zhujiandemo.Tools;

import android.graphics.Bitmap;

import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过给定Json文件的URL地址，通过HttpURLConnection的方法读取到本地
 * 在readParse里用输入流的形式转化为String并在Analysis中调用Gson进行解析
 * 最终返回List<Recipe>
 */

public class JsonUtil {

    private JsonUtil() {
    }

    static JsonUtil instance = new JsonUtil();

    public static JsonUtil getInstance() {
        return instance;
    }

    public static List<Recipe> readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String jsonStr = new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
//        System.out.println(jsonStr);
        List<Recipe> result = Analysis(jsonStr);
        return result;
    }



    /* 解析
     *
     * @throws JSONException
     */
    private static List<Recipe> Analysis(String jsonStr) {
        /******************* 解析 ***********************/
        // 初始化list数组对象
        List<Recipe> list = new ArrayList<>();
        JsonArray jsonArray = new JsonParser().parse(jsonStr).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Recipe recipe = new Recipe();
            recipe.setTitle(jsonObject.get("title").getAsString());
            recipe.setDescription(jsonObject.get("description").getAsString());
            recipe.setImage(jsonObject.get("img").getAsString());
            recipe.setRecommend(jsonObject.get("is_recommend").getAsBoolean());
            recipe.setSlide(jsonObject.get("is_slide").getAsBoolean());
            recipe.setCategorie(jsonObject.get("categorie").getAsString());

            //获取食材列表
            ArrayList<String> materialList = new ArrayList<>();
            JsonArray materials = jsonObject.get("material").getAsJsonArray();
            for (JsonElement material : materials) {
                materialList.add(material.getAsString());
            }
            recipe.setMaterial(materialList);


            //获取步骤说明列表
            ArrayList<String> stepDescriptionList = new ArrayList<>();
            JsonArray step_descriptions = jsonObject.get("step_description").getAsJsonArray();
            for (JsonElement step_description : step_descriptions) {
                stepDescriptionList.add(step_description.getAsString());
            }
            recipe.setStepDescription(stepDescriptionList);


            //获取步骤图片列表
            ArrayList<String> stepImgList = new ArrayList<>();
            JsonArray step_imgs = jsonObject.get("step_img").getAsJsonArray();
            for (JsonElement step_img : step_imgs) {
                stepImgList.add(step_img.getAsString());
            }
            recipe.setStepImg(stepImgList);



            list.add(recipe);
        }
        return list;
    }


    public static void main(String[] args) {
        String jsonString;
        List<Recipe> lstRecipes = new ArrayList<>();
        try {
            lstRecipes = JsonUtil.readParse("https://zhujian-1253572416.cos.ap-guangzhou.myqcloud.com/recipejson/recipe.json");
            for (Recipe lstRecipe : lstRecipes) {
                System.out.println(lstRecipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
