package com.example.chanhyunguniversity;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@Component
@RequiredArgsConstructor
public class ExcelPasswordConverter implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String inputFile = "C:/study/ChanhyungUniversity/src/main/resources/static/files/student.xlsx";  // 변환할 파일경로
        String outputFile = "C:/study/ChanhyungUniversity/src/main/resources/static/files/out_Student.xlsx";  // 변환된 파일

        try(FileInputStream fis = new FileInputStream(inputFile);
                Workbook workbook = new XSSFWorkbook(fis)){


            Sheet sheet = workbook.getSheetAt(0);
            int passwordIndex = 3;

            for(Row row : sheet){
                if(row.getRowNum() == 0) continue;

                Cell passwordCell = row.getCell(passwordIndex);

                String Password = getString(passwordCell);


                // password 변환
                String hashPassword = passwordEncoder.encode(Password);
                passwordCell.setCellValue(hashPassword);


                //저장
                try(FileOutputStream fos = new FileOutputStream(outputFile)) {
                    workbook.write(fos);
                }
            }
        }
    }

    private static String getString(Cell passwordCell) {
        String Password;

        // 셀 유형 확인
        if(passwordCell == null){
            throw new IllegalStateException("password 셀이 비어있음");
        }
        // password가 숫자면 string으로 변환
        if(passwordCell.getCellType() == CellType.NUMERIC){
            Password = String.format("%.0f", passwordCell.getNumericCellValue());
        }

        else if (passwordCell.getCellType() == CellType.STRING) {
            Password = passwordCell.getStringCellValue();
        } else{
            throw new IllegalStateException("password 셀이 숫자가 아닙니다! 숫자로 바꿔주세요");
        } return Password;
    }
}