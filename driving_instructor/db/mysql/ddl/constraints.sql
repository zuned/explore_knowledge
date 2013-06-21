-- address
 ALTER TABLE address ADD CONSTRAINT ADDRESS_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);
 
-- driving_detail
 ALTER TABLE driving_detail ADD CONSTRAINT driving_detail_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);
 
-- service_provider_other_details 
 ALTER TABLE service_provider_other_details ADD CONSTRAINT service_provider_other_details_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);
 
 -- service_provider_service_detail 
 ALTER TABLE service_provider_service_detail ADD CONSTRAINT CONSTRAINT service_provider_service_detail_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);

-- service_provider_tracking 
 ALTER TABLE service_provider_tracking ADD CONSTRAINT service_provider_tracking_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);

-- package_enrolled 
 ALTER TABLE package_enrolled ADD CONSTRAINT package_enrolled_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);
 ALTER TABLE package_enrolled ADD CONSTRAINT package_enrolled_package_FK FOREIGN KEY (package_id) REFERENCES package (id);

-- service_area_tracking 
 ALTER TABLE service_area_tracking ADD CONSTRAINT service_area_tracking_DI_FK FOREIGN KEY (service_area_id) REFERENCES service_area (id);

-- special_offer 
 ALTER TABLE special_offer ADD CONSTRAINT special_offer_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);

--  testimonial 
 ALTER TABLE testimonial ADD CONSTRAINT testimonial_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);
 
 -- service_area_covered
 ALTER TABLE service_area_covered ADD CONSTRAINT service_area_covered_DI_FK FOREIGN KEY (service_provider_id) REFERENCES service_provider (id);
 ALTER TABLE service_area_covered ADD CONSTRAINT service_area_covered_SA_FK FOREIGN KEY (service_area_id) REFERENCES service_area (id);
 
  -- service_extra_info
 ALTER TABLE service_extra_info ADD CONSTRAINT service_extra_info_FK FOREIGN KEY (service_provider_service_detail_id) REFERENCES service_provider_service_detail (id);

  
  