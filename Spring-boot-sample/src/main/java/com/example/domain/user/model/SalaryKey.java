package com.example.domain.user.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class SalaryKey implements Serializable {
	private String userId;
	private String yearMonth;
}
