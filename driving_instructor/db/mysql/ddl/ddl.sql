/*
SQLyog Enterprise - MySQL GUI v7.02 
MySQL - 5.1.42-community : Database - driving
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table address */

CREATE TABLE address (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  address_type varchar(255) DEFAULT NULL,
  building_name varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  county varchar(255) DEFAULT NULL,
  post_code varchar(255) DEFAULT NULL,
  primary_phone_number varchar(255) DEFAULT NULL,
  secondary_phone_number varchar(255) DEFAULT NULL,
  street_address varchar(255) DEFAULT NULL,
  service_provider_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table audit_mail_log */

CREATE TABLE audit_mail_log (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  service_provider_id bigint(20) DEFAULT NULL,
  mail_to varchar(255) DEFAULT NULL,
  mail_cc varchar(200) DEFAULT NULL,
  mail_from varchar(255) DEFAULT NULL,
  mail_subject varchar(500) DEFAULT NULL,
  mail_body longtext,
  success_flag tinyint(1) NOT NULL DEFAULT '1',
  error_message longtext,
  event varchar(255) DEFAULT NULL,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

/*Table structure for table configuration */

CREATE TABLE configuration (
  id int(10) NOT NULL,
  property_name varchar(100) NOT NULL,
  property_value varchar(1000) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table package */

CREATE TABLE package (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  duration varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table package_enrolled */

CREATE TABLE package_enrolled (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  start_date datetime DEFAULT NULL,
  status int(11) DEFAULT NULL,
  package_id bigint(20) DEFAULT NULL,
  service_provider_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table post_codes */

CREATE TABLE post_codes (
  post_code varchar(255) NOT NULL,
  county varchar(255) DEFAULT NULL,
  latitude double DEFAULT NULL,
  longitude double DEFAULT NULL,
  town varchar(255) DEFAULT NULL,
  x bigint(20) DEFAULT NULL,
  y bigint(20) DEFAULT NULL,
  PRIMARY KEY (post_code)
);

/*Table structure for table service_area */

CREATE TABLE service_area (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  area_name varchar(255) DEFAULT NULL,
  post_code varchar(255) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

/*Table structure for table service_area_covered */

CREATE TABLE service_area_covered (
  service_area_id bigint(20) NOT NULL,
  service_provider_id bigint(20) NOT NULL,
  PRIMARY KEY (service_area_id,service_provider_id)
);

/*Table structure for table service_area_tracking */

CREATE TABLE service_area_tracking (
  id bigint(20) NOT NULL AUTO_INCREMENT,
   created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  service_area_id bigint(20) DEFAULT NULL,
  service_name varchar(150) DEFAULT NULL,
  no_result_found tinyint(1) DEFAULT NULL,   
  PRIMARY KEY (id)
);

/*Table structure for table service_extra_info */

CREATE TABLE service_extra_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  property varchar(255) DEFAULT NULL,
  value varchar(255) DEFAULT NULL,
  service_provider_service_detail_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table service_provider */

CREATE TABLE service_provider (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  company_name varchar(255) DEFAULT NULL,
  contact_preference varchar(255) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
   created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_of_birth date DEFAULT NULL,
  email_id varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  gender varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  licence_number varchar(255) DEFAULT NULL,
  member_type varchar(255) DEFAULT NULL,
  middle_name varchar(255) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  updated_date datetime DEFAULT NULL,
  date_of_registration date DEFAULT NULL,
  service_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table service_provider_other_details */

CREATE TABLE service_provider_other_details (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  about_us varchar(255) DEFAULT NULL,
  image longblob,
  service_provider_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);
  
/*Table structure for table service_provider_service_detail */

CREATE TABLE service_provider_service_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  is_nervous_driver tinyint(1) DEFAULT NULL,
  qualification varchar(255) DEFAULT NULL,
  service_start_date date DEFAULT NULL,
  sprciality varchar(255) DEFAULT NULL,
  timing varchar(255) DEFAULT NULL,
  vehicle_type varchar(255) DEFAULT NULL,
  service_provider_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table service_provider_tracking */

CREATE TABLE service_provider_tracking (
  id bigint(20) NOT NULL AUTO_INCREMENT,
   created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  service_provider_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table services */

CREATE TABLE services (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  service_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table special_offer */

CREATE TABLE special_offer (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_by varchar(255) DEFAULT NULL,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  description varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  updated_date datetime DEFAULT NULL,
  valid_up_to datetime DEFAULT NULL,
  service_provider_id bigint(20) DEFAULT NULL,
  end_date datetime DEFAULT NULL,
  start_date datetime DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Table structure for table testimonial */

CREATE TABLE testimonial (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_by varchar(255) DEFAULT NULL,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  description varchar(255) DEFAULT NULL,
  realationShip varchar(255) DEFAULT NULL,
  service_provider_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;