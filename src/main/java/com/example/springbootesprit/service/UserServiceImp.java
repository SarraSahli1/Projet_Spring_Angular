package com.example.springbootesprit.service;

import com.example.springbootesprit.controller.MessagingException;
import com.example.springbootesprit.entities.EnumRole;
import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.entities.User;
import com.example.springbootesprit.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
@Service
@RequiredArgsConstructor
public class UserServiceImp implements IUserService {

    private final JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return  userRepository.findById(id);    }

    @Override
    public User update(User user) {
        User user1= userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("No user with id " + user.getId() + " was found!"));
        if (user1!=null){
            userRepository.save(user);}
        return user1;    }

    @Override
    public User updateById(User user) {
        User user1= userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("No user with id " + user.getId() + " was found!"));
        if (user1!=null){
            userRepository.save(user);}
        return user1;    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).get();    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user,passwordToken);
    }
    public void sendPasswordResetVerificationEmail(String url, User theUser) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
        String subject = "Verification changement de mot de passe                                                        ";
        String senderName = "SAJJELNI Service utilisateurs";
        if (theUser != null && theUser.getEmail() != null) {

            String mailContent = "<p> Bonjour, "+ theUser.getFirstname()+ ", </p>"+
                "<p><b>Vous avez demandé de changer votre mot de passe </b>"+"" +
                "Veuillez suivre ce lien pour terminer ce processus</p>"+
                "<a href=\"" +url+ "\">Changer votre mot de passe</a>"+
                "<p>SAJJELNI Service utilisateurs";
        MimeMessage message = this.mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("universityspring123@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
            }

    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        return passwordResetTokenService.validatePasswordResetToken(passwordResetToken);
    }

    @Override
    public User findUserByPasswordToken(String passwordResetToken) {
        return passwordResetTokenService.findUserByPasswordToken(passwordResetToken).get();
    }

    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    //Rayen
    public void getlistUserExcel() {
        try {
            LocalDate now = LocalDate.now();
            File myFile = new File("C://Users/Asus/Desktop/excel/Etudiant" + now.toString() + ".xlsx");
            FileOutputStream os = new FileOutputStream(myFile);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Liste des Utilisateurs");
            Row row1 = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16.0);
            style.setFont(font);
            createCell(row1, 0, "ID", style);
            createCell(row1, 1, "Firstname", style);
            createCell(row1, 2, "Lastname", style);
            createCell(row1, 3, "Email", style);
            createCell(row1, 4, "Role", style);

            AtomicInteger rowCount = new AtomicInteger(1);
            CellStyle style2 = workbook.createCellStyle();
            XSSFFont font2 = workbook.createFont();
            font2.setFontHeight(14.0);
            style2.setFont(font2);
            userRepository.findAll().forEach((user) -> {
                Row row = sheet.createRow(rowCount.getAndIncrement());
                int columnCount = 0;
                createCell(row, columnCount++, user.getId(), style);
                createCell(row, columnCount++, user.getFirstname(), style);
                createCell(row, columnCount++, user.getLastname(), style);
                createCell(row, columnCount++, user.getEmail(), style);
                createCell(row, columnCount, user.getRole(), style);
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
        if (columnCount == 0 && value instanceof Integer) {
            cell.setCellValue((int) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof EnumRole) {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }
    public String genereCarteUserPdf(Integer id){

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

            parameters.put("id", id);

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

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
