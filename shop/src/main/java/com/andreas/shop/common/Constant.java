package com.andreas.shop.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author andreaszhou
 * @ClassName Constant
 * @Description TODO
 * @date 2021/1/27 13:03
 * @Version 1.0
 */
@Component
@Configuration
public class Constant {
    public static final String SALT = "@!#!&!#&*!#!#*HDJHSASW##!#EH";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");

    }


}
