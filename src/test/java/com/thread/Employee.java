package com.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private int id;
	private String name;
	private boolean newJoinee;
	private boolean isCompleted;
}
