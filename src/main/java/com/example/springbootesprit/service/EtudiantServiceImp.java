package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Etudiant;
import com.example.springbootesprit.repositories.EtudiantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Service
public class EtudiantServiceImp implements IEtudiantService{
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    private Environment environment;
    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public List<Etudiant> getEtudiant() {
        return etudiantRepository.findAll();
    }


    @Override
    public Optional<Etudiant> findById(Long id) {
        return  etudiantRepository.findById(id);
    }

    @Override
    public Etudiant update(Etudiant etudiant) {
        {
            Etudiant et = etudiantRepository.findById(etudiant.getIdEtudiant()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + etudiant.getIdEtudiant() + " was found!"));
            if (et!=null){
                etudiantRepository.save(etudiant);}
            return et;
        }
    }

    @Override
    public void delete(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).get();
    }
    public void getlistetudiantExcel() {
        try {
            LocalDate now = LocalDate.now();
            File myFile = new File("C://Users/jouida/Desktop/excel/Etudiant" + now.toString() + ".xlsx");
            FileOutputStream os = new FileOutputStream(myFile);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Liste des Etudiants");
            Row row1 = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16.0);
            style.setFont(font);
            this.createCell(row1, 0, "ID", style);
            this.createCell(row1, 1, "Nom", style);
            this.createCell(row1, 2, "Prénom", style);
            this.createCell(row1, 4, "Cin", style);
            this.createCell(row1, 4, "Ecole", style);

            AtomicInteger rowCount = new AtomicInteger(1);
            CellStyle style2 = workbook.createCellStyle();
            XSSFFont font2 = workbook.createFont();
            font2.setFontHeight(14.0);
            style2.setFont(font2);
            this.etudiantRepository.findAll().forEach((etudiant) -> {
                Row row = sheet.createRow(rowCount.getAndIncrement());
                int columnCount = 0;
                this.createCell(row, columnCount++, etudiant.getIdEtudiant(), style);
                this.createCell(row, columnCount++, etudiant.getNomEt(), style);
                this.createCell(row, columnCount++, etudiant.getPrenomEt(), style);
                this.createCell(row, columnCount++, etudiant.getCin(), style);
                this.createCell(row, columnCount++, etudiant.getEcole(), style);
            });
            workbook.write(os);
        } catch (FileNotFoundException var12) {
            throw new RuntimeException(var12);
        } catch (IOException var13) {
            throw new RuntimeException(var13);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((double)(Integer)value);
        } else if (value instanceof Long) {
            cell.setCellValue((double)(Long)value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue(String.valueOf((LocalDate)value));
        } else {
            cell.setCellValue((String)value);
        }

        cell.setCellStyle(style);
    }

    public String generecarteetudpdf(long idEtudiant){

        try {

            // Get your data source
            Connection conn=null ;
            String url = environment.getProperty("spring.datasource.url");
            String user = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            assert url != null;
            conn = DriverManager.getConnection(url,user,password);

            String reportPath = "C:\\carte";

            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager
                    .compileReport(new ClassPathResource("carte_etudiant.jrxml.xml")
                            .getInputStream());





            // Add parameters
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("id_etudiant", idEtudiant);

            // Fill the report
           JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                 conn);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);


            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\carte.pdf");

            return "Report successfully generated @path= " + reportPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error--> check the console log";
        }

    }
}
