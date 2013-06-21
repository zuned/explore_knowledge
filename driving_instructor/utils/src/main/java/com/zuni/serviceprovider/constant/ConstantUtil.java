package com.zuni.serviceprovider.constant;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Zuned Ahmed
 *
 */
@Component
public class ConstantUtil {
	
	@Value("${splitToken:,}")
	private String splitTpken;
	
	@Value("${gender}")
	private String genderConstant;
	
	@Value("${relationship}")
	private String relationshipConstant;
	
	@Value("${addressType}")
	private String addressTypeConstant;
	
	@Value("${packageDuration}")
	private String packageDurationConstant;
	
	@Value("${dateFormatPatter}")
	private String dateFormatPatterConstant;
	
	@Value("${memberShipType}")
	private String memberShipTypeConstant;
	
	private String[] GENDER;
	
	private String[] MEMBERSHIP_TYPE;
	
	private String[] RELATIONSHIP;
	
	private String[] ADDRESS_TYPE;
	
	private String[] PACKAGE_DURATION;
	
	private  SimpleDateFormat dateFormatter;
	
	@PostConstruct
	public void intBeans(){
		GENDER =  genderConstant.split(splitTpken);
		RELATIONSHIP = relationshipConstant.split(splitTpken);
		ADDRESS_TYPE = addressTypeConstant.split(splitTpken);
		PACKAGE_DURATION = packageDurationConstant.split(splitTpken);
		MEMBERSHIP_TYPE = memberShipTypeConstant.split(splitTpken);
		dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public static enum STATUS { INACTIVE, ACTIVE }

	public String getSplitTpken() {
		return splitTpken;
	}

	public String getGenderConstant() {
		return genderConstant;
	}

	public String getRelationshipConstant() {
		return relationshipConstant;
	}

	public String getAddressTypeConstant() {
		return addressTypeConstant;
	}

	public String getPackageDurationConstant() {
		return packageDurationConstant;
	}

	public String getDateFormatPatterConstant() {
		return dateFormatPatterConstant;
	}

	public String[] getGENDER() {
		return GENDER;
	}

	public String[] getRELATIONSHIP() {
		return RELATIONSHIP;
	}

	public String[] getADDRESS_TYPE() {
		return ADDRESS_TYPE;
	}

	public String[] getPACKAGE_DURATION() {
		return PACKAGE_DURATION;
	}

	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}

	public String getMemberShipTypeConstant() {
		return memberShipTypeConstant;
	}

	public String[] getMEMBERSHIP_TYPE() {
		return MEMBERSHIP_TYPE;
	}

	public boolean isValidGender(String sex) {
		for (String g : GENDER) {
			if (g.equalsIgnoreCase(sex)) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidrelationship(String relationship) {
		for (String r : RELATIONSHIP) {
			if (r.equalsIgnoreCase(relationship)) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidAddressType(String addressType) {
		for (String a : ADDRESS_TYPE) {
			if (a.equalsIgnoreCase(addressType)) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidPackageDuration(String packageDuration) {
		for (String p : PACKAGE_DURATION) {
			if (p.equalsIgnoreCase(packageDuration)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidMemberShipType(String memberShipType) {
		for (String m : MEMBERSHIP_TYPE) {
			if (m.equalsIgnoreCase(memberShipType)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isFreeMemberShipType(String memberShipType) {
		return MEMBERSHIP_TYPE[0].equalsIgnoreCase(memberShipType);
	}
	
}
