package com.healthcare.service.impl;

import com.healthcare.config.UploadProperties;
import com.healthcare.service.dao.UploadService;
import com.healthcare.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadProperties uploadProperties;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if(!uploadProperties.getAllowTypes().contains(file.getContentType())){
            throw new IOException("文件上传类型错误！");
        }
        String fileName = UploadUtils.generateFileName(file.getOriginalFilename());
        file.transferTo(new File(uploadProperties.getPath()+fileName));
        return fileName;
    }
}
