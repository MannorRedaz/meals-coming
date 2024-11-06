package com.mannor.reggie_take_out.controller;

import com.mannor.reggie_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.imgPath}")
    private String basePath;

    /**
     * 文件的上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        log.info("文件上传参数：file={}", file.toString());
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        //判断目录存在否，不存在就创建一个
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file.transferTo(new File(basePath + fileName));  //转存文件
        log.info("fileName:"+fileName);
        return R.success(fileName);
    }

    /**
     * 文件的下载
     *
     * @param name
     * @param response
     */
  @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //输入流，读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输入流，将文件写回前端，用于展示
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg"); //返回的文件格式


            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();  //刷新
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
