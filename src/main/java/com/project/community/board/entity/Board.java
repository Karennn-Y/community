package com.project.community.board.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	long categoryId;
	String categoryName;

	String nickname;
	String userId;
	String subject;
	@Lob
	String contents;

	LocalDateTime regDt; // registerDate
	LocalDateTime udtDt; // updateDate
}
