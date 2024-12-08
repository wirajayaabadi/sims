package com.example.sims.service;

import com.example.sims.entity.Balance;
import com.example.sims.entity.User;
import com.example.sims.model.request.ProfileUpdateRequest;
import com.example.sims.model.response.ProfileResponse;
import com.example.sims.util.JwtUtil;
import com.example.sims.util.PasswordValidator;
import com.example.sims.model.request.RegistrationRequest;
import com.example.sims.repository.BalanceRepository;
import com.example.sims.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

@Service
public class MembershipService {

  @Value("${file.upload-dir}")
  private String UPLOAD_DIR;

  @Value("${sufy-access-key}")
  private String SUFY_ACCESS_KEY;

  @Value("${sufy-url-source}")
  private String SUFY_URL_SOURCE;

  @Value("${sufy-secret-key}")
  private String SUFY_SECRET_KEY;

  @Value("${sufy-bucket}")
  private String SUFY_BUCKET;

  @Value("${sufy-bucket-key}")
  private String SUFY_BUCKET_KEY;

  @Value("${sufy-image-url}")
  private String SUFY_IMAGE_URL;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BalanceRepository balanceRepository;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private PasswordValidator passwordValidator;

  @Autowired
  private JwtUtil jwtUtil;

  @Transactional
  public void registerUser(RegistrationRequest request) {

    validationService.validate(request);

    if(userRepository.existsByEmail(request.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email sudah digunakan");
    }

    if(!passwordValidator.isValidPassword(request.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password tidak valid");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());

    user = userRepository.save(user);

    Balance balance = new Balance();
    balance.setUserId(user.getId());
    balance.setBalance(0.0);
    balanceRepository.save(balance);
  }

  public ProfileResponse getProfile(HttpServletRequest request) {
    String email = jwtUtil.getEmailFromToken(request);

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email tidak ditemukan"));

    return new ProfileResponse(user.getEmail(), user.getFirstName(), user.getLastName(), user.getProfileImage());
  }

  @Transactional
  public ProfileResponse updateProfile(HttpServletRequest request, ProfileUpdateRequest payload) {
    validationService.validate(payload);

    String email = jwtUtil.getEmailFromToken(request);

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email tidak ditemukan"));
    user.setFirstName(payload.getFirstName());
    user.setLastName(payload.getLastName());
    user = userRepository.save(user);

    return new ProfileResponse(user.getEmail(), user.getFirstName(), user.getLastName(), user.getProfileImage());
  }

  @Transactional
  public ProfileResponse uploadProfileImage(HttpServletRequest request, MultipartFile file) {
    if(file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File tidak diupload");
    }

    String email = jwtUtil.getEmailFromToken(request);
    String fileName = file.getOriginalFilename();
    String url = SUFY_IMAGE_URL + UPLOAD_DIR + fileName;

    final S3Client s3 = S3Client.builder().region(Region.of("ap-southeast-2"))
            .endpointOverride(URI.create(SUFY_URL_SOURCE))
            .credentialsProvider(
                    StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(SUFY_ACCESS_KEY, SUFY_SECRET_KEY)))
            .build();
    String bucketName = SUFY_BUCKET;
    String keyName = UPLOAD_DIR + fileName;

    try {
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
              .bucket(bucketName)
              .key(keyName)
              .build();

      s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

      User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email tidak ditemukan"));
      user.setProfileImage(url);
      user = userRepository.save(user);

      return new ProfileResponse(user.getEmail(), user.getFirstName(), user.getLastName(), user.getProfileImage());
    } catch (IOException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
