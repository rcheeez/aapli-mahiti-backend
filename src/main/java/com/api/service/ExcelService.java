package com.api.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.People;
import com.api.repository.PeopleRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelService {
	
	@Autowired
	private PeopleRepository repository;

	public void generateExcelFile(HttpServletResponse response) throws IOException  {
		List<People> peoples = repository.findAll();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("People_Data");

		Row rowHeader = sheet.createRow(0);
		String[] columnHeaders = {"ID", "FullName", "PhoneNumber", "Address", "Zone"};
		for (int i = 0; i < columnHeaders.length; i++) {
			Cell cell = rowHeader.createCell(i);
			cell.setCellValue(columnHeaders[i]);
		}

		int rowNum = 1;
		for(People p: peoples) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(p.getId());
			row.createCell(1).setCellValue(p.getFullName());
			row.createCell(2).setCellValue(p.getPhoneNumber());
			row.createCell(3).setCellValue(p.getAddress());
			row.createCell(4).setCellValue(p.getZone());
		}

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=people_data.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
