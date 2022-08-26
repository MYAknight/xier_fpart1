package com.healthcare.service.dao;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    String uploadImage(MultipartFile file) throws Exception;
}
