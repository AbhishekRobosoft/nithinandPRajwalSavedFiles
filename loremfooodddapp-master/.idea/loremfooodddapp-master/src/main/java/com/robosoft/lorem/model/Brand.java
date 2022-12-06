package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Blob;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Brand
{
    private int brandId;
    private String brandName;
    private String description;
    private MultipartFile logo;
    private MultipartFile profilePic;
    private String brandOrigin;
    private String profileLink;
    private String logoLink;
}
