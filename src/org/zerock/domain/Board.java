package org.zerock.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class Board {
	
	private int bn;
	private String title;
	private String contnd;
	private String writer;
	private Date regdate;
	

}
