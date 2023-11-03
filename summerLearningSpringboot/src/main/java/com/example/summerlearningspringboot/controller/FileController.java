package com.example.summerlearningspringboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.example.summerlearningspringboot.common.AuthAccess;
import com.example.summerlearningspringboot.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/9/17 16:16
 */

@RestController
@RequestMapping("/file")
public class FileController {
    //fkxiaoyu

    //修改2.1

    @Value("${ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    private static final String fileDirPath = System.getProperty("user.dir") + File.separator + "files";//D:\VScodeProject\summerLearning\files


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result fileUpload (@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileMainName = FileUtil.mainName(originalFilename);
        String fileExtName = FileUtil.extName(originalFilename);
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        String fileRealPath = fileDirPath + File.separator + originalFilename; //D:\VScodeProject\summerLearning\files\{fileOriginalName}
        File saveFile = new File(fileRealPath);
        if (saveFile.exists()){
            originalFilename = System.currentTimeMillis() + "_" + originalFilename;
            fileRealPath = fileDirPath + File.separator + originalFilename;
            saveFile = new File(fileRealPath);
        }
        file.transferTo(saveFile);
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);
    }

    @AuthAccess
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    public void download (@PathVariable String fileName, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        String filePath = fileDirPath + File.separator + fileName;
        if (!FileUtil.exist(filePath)){
            return;
        }
        byte[] fileBytes = FileUtil.readBytes(filePath);
        response.setContentLength(fileBytes.length);
        response.setHeader("Content-Disposition", "attachment; filename=file.txt");


        // 设置响应的MIME类型
        response.setContentType("application/octet-stream");
        outputStream.write(fileBytes);
        outputStream.flush();
        outputStream.close();
    }

    @AuthAccess
    @GetMapping("/download2/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletResponse response) throws MalformedURLException {
        // 构建文件路径

        Path filePath = Paths.get(fileDirPath).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // 检查文件是否存在并可读
        if (!resource.exists() || !resource.isReadable()) {
            // 处理文件不存在或不可读的情况
            return ResponseEntity.notFound().build();
        }

        // 获取文件的MIME类型
        String contentType = "application/octet-stream"; // 默认的MIME类型

        // 根据文件名后缀设置MIME类型（可选）
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".txt")) {
            contentType = "text/plain";
        } // 添加更多的MIME类型...

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @AuthAccess
    @RequestMapping(value = "/wang/upload", method = RequestMethod.POST)
    public Map<String, Object> fileWangUpload (MultipartFile file, @RequestHeader String type) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileMainName = FileUtil.mainName(originalFilename);
        String fileExtName = FileUtil.extName(originalFilename);
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        String fileRealPath = fileDirPath + File.separator + originalFilename; //D:\VScodeProject\summerLearning\files\{fileOriginalName}
        File saveFile = new File(fileRealPath);
        if (saveFile.exists()){
            originalFilename = System.currentTimeMillis() + "_" + originalFilename;
            fileRealPath = fileDirPath + File.separator + originalFilename;
            saveFile = new File(fileRealPath);
        }
        file.transferTo(saveFile);
        Map<String, Object> res = new HashMap<>();
        if ("img".equals(type)){
            res.put("errno", 0);
            res.put("data", CollUtil.newArrayList(Dict.create().set("url", "http://" + ip + ":" + port + "/file/download/" + originalFilename)));
            return res;
        }else if ("video".equals(type)){
            res.put("errno", 0);
            res.put("data", Dict.create().set("url", "http://" + ip + ":" + port + "/file/download/" + originalFilename));
            return res;
        }
        return null;
    }


}
