/**
 * ObjData.java Jan 16, 2024 12:30:56 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.satusehat.kyc;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "Tubagus Uun"
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("agent_name")
	private String agentName;
	
	@JsonProperty("agent_nik")
	private String agentNIk;
	
	@JsonProperty("public_key")
	private String pubKey;
	
}
