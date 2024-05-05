package services;

import javafx.scene.control.Alert;
import models.Contrat;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utils.DBconnection;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
public class ServiceExcel {

    public ServiceExcel() {}

    public void writeToExcel(List<Contrat> contrats) throws IOException {


        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Détails Contrat");
        HSSFRow header = sheet.createRow(0);
        header.createCell(1).setCellValue("Nom ");
        header.createCell(2).setCellValue("Prenom ");
        header.createCell(3).setCellValue("email");
        header.createCell(4).setCellValue("numero");
        header.createCell(5).setCellValue("adresse");
        header.createCell(6).setCellValue("date debut");
        header.createCell(7).setCellValue("date fin");
        int index = 1;
        for (Contrat resultSet: contrats
             ) {

            HSSFRow row = sheet.createRow(index);
            row.createCell(1).setCellValue(resultSet.getNom());
            row.createCell(2).setCellValue(resultSet.getPrenom());
            row.createCell(3).setCellValue(resultSet.getEmail());
            row.createCell(4).setCellValue(resultSet.getNumero_assur());
            row.createCell(5).setCellValue(resultSet.getAdresse_assur());
            row.createCell(6).setCellValue(resultSet.getDate_debut_contrat());
            row.createCell(7).setCellValue(resultSet.getDatefin_contrat());

            index++;
        }

        FileOutputStream fileOut = new FileOutputStream("C:/Users/amena/Downloads/Django_Java-userrrrrr/Django_Java-user/src/main/java/EXCEL/Contrat.xls");
        wb.write(fileOut);
        fileOut.close();

        JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succés");

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}