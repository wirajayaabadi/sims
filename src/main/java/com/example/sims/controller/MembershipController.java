package com.example.sims.controller;

import com.example.sims.enums.StatusCode;
import com.example.sims.model.ApiResponse;
import com.example.sims.model.request.ProfileUpdateRequest;
import com.example.sims.model.request.RegistrationRequest;
import com.example.sims.model.response.ProfileResponse;
import com.example.sims.service.MembershipService;
import com.example.sims.validation.ValidImageFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
public class MembershipController {

  @Autowired
  private MembershipService membershipService;

  @PostMapping(
          path = "/api/v1/registration",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<Void>> registerUser(@RequestBody RegistrationRequest request) {
    membershipService.registerUser(request);
    return ResponseEntity.ok(ApiResponse.<Void>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Registrasi berhasil silahkan login")
            .data(null)
            .build());
  }

  @GetMapping(path = "/api/v1/profile")
  public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(HttpServletRequest request) {
    ProfileResponse profile = membershipService.getProfile(request);

    return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Sukses")
            .data(profile)
            .build());
  }

  @PutMapping(path = "/api/v1/profile/update",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(HttpServletRequest request, @RequestBody ProfileUpdateRequest payload) {
    ProfileResponse profile = membershipService.updateProfile(request, payload);

    return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Update Profile berhasil")
            .data(profile)
            .build());
  }

  @PutMapping(path = "/api/v1/profile/image",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfileImage(HttpServletRequest request, @RequestParam("file") @Valid @ValidImageFile MultipartFile file) {
    ProfileResponse profile = membershipService.uploadProfileImage(request, file);

    return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Update photo profil berhasil")
            .data(profile)
            .build());
  }
}
