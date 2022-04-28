package com;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName Controller
 * @Author weijian
 * @Date 2022/3/8
 */

@RestController
@RequestMapping("/test")
public class Controller {

    @PostMapping("/test")
    public String test(@RequestPart("user") User user, @RequestPart(value = "file") MultipartFile file) {
        return "chenggong";
    }

    @PostMapping("/test1")
    public String test1() {
        return "chenggong";
    }
}
