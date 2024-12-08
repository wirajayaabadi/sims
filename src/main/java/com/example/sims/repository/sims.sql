CREATE DATABASE IF NOT EXISTS sims;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       profile_image TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE balances (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          balance DOUBLE NOT NULL DEFAULT 0.0,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) references users(id)
);

CREATE TABLE balance_logs (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              balance_id BIGINT NOT NULL,
                              transaction_type ENUM('credit', 'debit') NOT NULL,
                              amount DOUBLE NOT NULL,
                              description TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (balance_id) REFERENCES balances(id)
);

CREATE TABLE banners (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         banner_name VARCHAR(255) NOT NULL,
                         banner_image TEXT NOT NULL,
                         description TEXT,
                         status ENUM('active', 'inactive') DEFAULT 'active',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE services (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          service_code VARCHAR(50) UNIQUE NOT NULL,
                          service_name VARCHAR(255) NOT NULL,
                          service_icon TEXT,
                          service_tariff DOUBLE NOT NULL DEFAULT 0.0,
                          status ENUM('active', 'inactive') DEFAULT 'active',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE history_transactions (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id BIGINT NOT NULL,
                                      service_code VARCHAR(50) NULL,
                                      service_name VARCHAR(255) NULL,
                                      invoice_number VARCHAR(50) UNIQUE NOT NULL,
                                      transaction_type ENUM('PAYMENT', 'TOPUP') NOT NULL,
                                      description TEXT,
                                      total_amount DOUBLE NOT NULL DEFAULT 0.0,
                                      status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
                                      created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO banners (banner_name, banner_image, description, status)
VALUES
    ('Banner 1', 'https://nutech-integrasi.app/dummy.jpg', 'Lorem Ipsum Dolor Sit amet', 'active'),
    ('Banner 2', 'https://nutech-integrasi.app/dummy.jpg', 'Lorem Ipsum Dolor Sit amet', 'active'),
    ('Banner 3', 'https://nutech-integrasi.app/dummy.jpg', 'Lorem Ipsum Dolor Sit amet', 'active'),
    ('Banner 4', 'https://nutech-integrasi.app/dummy.jpg', 'Lorem Ipsum Dolor Sit amet', 'active'),
    ('Banner 5', 'https://nutech-integrasi.app/dummy.jpg', 'Lorem Ipsum Dolor Sit amet', 'active'),
    ('Banner 6', 'https://nutech-integrasi.app/dummy.jpg', 'Lorem Ipsum Dolor Sit amet', 'active');

INSERT INTO services (service_code, service_name, service_icon, service_tariff, status)
VALUES
    ('PAJAK', 'Pajak PBB', 'https://nutech-integrasi.app/dummy.jpg', 40000, 'active'),
    ('PLN', 'Listrik', 'https://nutech-integrasi.app/dummy.jpg', 10000, 'active'),
    ('PDAM', 'PDAM Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 40000, 'active'),
    ('PULSA', 'Pulsa', 'https://nutech-integrasi.app/dummy.jpg', 40000, 'active'),
    ('PGN', 'PGN Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000, 'active'),
    ('MUSIK', 'Musik Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000, 'active'),
    ('TV', 'TV Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000, 'active'),
    ('PAKET_DATA', 'Paket data', 'https://nutech-integrasi.app/dummy.jpg', 50000, 'active'),
    ('VOUCHER_GAME', 'Voucher Game', 'https://nutech-integrasi.app/dummy.jpg', 100000, 'active'),
    ('VOUCHER_MAKANAN', 'Voucher Makanan', 'https://nutech-integrasi.app/dummy.jpg', 100000, 'active'),
    ('QURBAN', 'Qurban', 'https://nutech-integrasi.app/dummy.jpg', 200000, 'active'),
    ('ZAKAT', 'Zakat', 'https://nutech-integrasi.app/dummy.jpg', 300000, 'active');