package com.blog.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

@Service
public class CloudinaryService {

    public String uploadURL(String url) {
        try {
            Dotenv dotenv = Dotenv.load();
            Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
            cloudinary.config.secure = true;

            return cloudinary.uploader().upload(url, ObjectUtils.asMap(
                    "folder", dotenv.get("CLOUDINARY_FOLDER"))).get("secure_url").toString();
        } catch (IOException e) {
            return "";
        }
    }

    public String uploadFile(MultipartFile file) {
        try {
            Dotenv dotenv = Dotenv.load();
            Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
            cloudinary.config.secure = true;

            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", dotenv.get("CLOUDINARY_FOLDER"))).get("secure_url").toString();
        } catch (IOException e) {
            System.out.println(e);
            return "";
        }
    }

    public void deleteURL(String url) {
        try {
            Dotenv dotenv = Dotenv.load();
            String folder = dotenv.get("CLOUDINARY_FOLDER");
            System.out.println(folder);
            String[] resultSplit = url
                    .split(folder);
            int len = resultSplit.length;
            int indexDot = (folder + resultSplit[len - 1]).indexOf(".");
            String publicId = (folder + resultSplit[len - 1]).substring(0, indexDot);
            Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap());
        } catch (Exception e) {
        }
    }
}
