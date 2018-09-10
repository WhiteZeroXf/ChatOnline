package com.zero.springboot.controller;

import com.zero.springboot.config.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hxf
 * @version V1.0
 * @Title:
 * @Description: TODO
 * @date 2018/9/:27
 */
@RestController
public class TestController {

    @RequestMapping(value = "test")
    public Map index(MultipartFile[] file, String name, String pwd, HttpServletRequest request) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        if (file.length == 0)
            return map;
        //map.put("length", file.length);
        for (MultipartFile multipartFile : file) {
            if (Objects.equals(multipartFile.getOriginalFilename(), ""))
                break;
            multipartFile.transferTo(new File(Constant.UPLOAD_PATH + multipartFile.getOriginalFilename()));
            map.put(String.valueOf(new Date().getTime()), "/upload/" + multipartFile.getOriginalFilename());
        }
        return map;
    }

}
