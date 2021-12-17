package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.utils.FileNameUtil;
import com.tonpower.springbootweb.utils.QiniuUtils;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/28 20:18
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file) {
        // 构建随机文件名称
        String fileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(file.getOriginalFilename());
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
           return Result.success(QiniuUtils.url2 + fileName);
        }
        return Result.fail(ErrorCode.UPLOAD_ERROR.getCode(), ErrorCode.UPLOAD_ERROR.getMsg());
    }
}
