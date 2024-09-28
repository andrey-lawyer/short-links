package com.example.short_links.dto;

import com.example.short_links.model.ClickRecordModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsResponseDto {
	private List<ClickRecordModel> records;
	private long totalCount;
	
	public StatisticsResponseDto(List<ClickRecordModel> records, long totalCount) {
		this.records = records;
		this.totalCount = totalCount;
	}
}
