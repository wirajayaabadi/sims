package com.example.sims.validator;

import com.example.sims.validation.ValidImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {
  private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};
  private static final String[] ALLOWED_MIME_TYPES = {"image/jpeg", "image/png"};

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if(file == null || file.isEmpty()) {
      return false;
    }

    String fileName = file.getOriginalFilename();
    String fileExtension = getFileExtension(fileName);
    if (!isValidExtension(fileExtension)) {
      return false;
    }

    String mimeType = file.getContentType();
    return isValidMimeType(mimeType);
  }

  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if(dotIndex == -1) {
      return "";
    }
    return fileName.substring(dotIndex + 1).toLowerCase();
  }

  private boolean isValidExtension(String extension) {
    for(String allowedExtension : ALLOWED_EXTENSIONS) {
      if(allowedExtension.equalsIgnoreCase(extension)) {
        return true;
      }
    }
    return false;
  }

  private boolean isValidMimeType(String mimeType) {
    for(String allowedMimeType : ALLOWED_MIME_TYPES) {
      if(allowedMimeType.equalsIgnoreCase(mimeType)) {
        return true;
      }
    }
    return false;
  }
}
