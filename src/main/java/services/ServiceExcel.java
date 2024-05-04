package services;

import javafx.scene.control.Alert;
import models.Contrat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class ServiceExcel {

    public ServiceExcel() {}

    public void writeToExcel(List<Contrat> contrats, String pathToFile) {
        Workbook workbook = new XSSFWorkbook(); // Create a new workbook
        Sheet sheet = workbook.createSheet("Contrats"); // Create a sheet named "Contrats"

        // Define header row
        String[] columns = {"Date Début", "Date Fin", "Adresse", "Numéro", "Nom", "Prénom", "Email"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Populate data rows
        int rowNum = 1;
        for (Contrat contrat : contrats) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contrat.getDate_debut_contrat().toString());
            row.createCell(1).setCellValue(contrat.getDatefin_contrat().toString());
            row.createCell(2).setCellValue(contrat.getAdresse_assur());
            row.createCell(3).setCellValue(contrat.getNumero_assur());
            row.createCell(4).setCellValue(contrat.getNom());
            row.createCell(5).setCellValue(contrat.getPrenom());
            row.createCell(6).setCellValue(contrat.getEmail());
        }

        // Auto-size columns for better readability
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file and close the workbook
        try (FileOutputStream fileOut = new FileOutputStream(pathToFile)) {
            workbook.write(fileOut);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Excel file has been generated successfully!");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate Excel file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error while closing the workbook: " + e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}