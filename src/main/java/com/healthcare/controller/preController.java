package com.healthcare.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RequestMapping("/Pre")
public class preController {
    @ApiOperation("对垃圾照片进行分类")
    @PostMapping("/getPre")
    public String getAllBin(@ApiParam("垃圾照片") @RequestParam("picture") String picture) {
        String ans = "未知";
        String property = System.getProperty("user.dir");
        String arguments = property + "/pre/pre.exe";
//        String arguments = "src/main/resources/static/pre/pre.exe";
        String path = "https://img.zcool.cn/community/013f905666c58b6ac725b2c82e782d.jpg";//与执行文件路径对应的参数
        ProcessBuilder processBuilder = new ProcessBuilder(arguments, picture);
        StringBuilder stringBuilder = new StringBuilder();
        processBuilder.redirectErrorStream(true);
        Process process = null;
        try {
            // 获取程序执行后返回的结果
            //执行这个.exe程序
            process = processBuilder.start();
//            process = Runtime.getRuntime().exec("cmd /c start " + arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
//                System.out.println(line);
                stringBuilder.append(line + System.lineSeparator());
            }
            in.close();
            //java代码中的process.waitFor()返回值为0表示我们执行.exe文件成功，
            //返回值为1表示执行.exe文件失败，这和我们通常意义上见到的0与1定义正好相反
            int re = process.waitFor();
            ans = stringBuilder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return ans.substring(6);
    }
}
