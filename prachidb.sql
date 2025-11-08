CREATE DATABASE bloodbankdb;

USE bloodbankdb;

CREATE TABLE donor (
  donor_id INT PRIMARY KEY,
  name VARCHAR(100),
  blood_group VARCHAR(10),
  age INT,
  gender VARCHAR(10),
  contact_no VARCHAR(15),
  city VARCHAR(50)
);
