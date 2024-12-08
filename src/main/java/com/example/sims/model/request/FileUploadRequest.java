package com.example.sims.model.request;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(HttpServletRequest request, MultipartFile file){
}
