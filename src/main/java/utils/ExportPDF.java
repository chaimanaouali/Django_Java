package utils;

import models.Post;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.List;

public class ExportPDF {

    public void generatePDF(String filePath, List<Post> posts) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Add title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("Your Title Here");
                contentStream.endText();

                // Add paragraph of text
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Your paragraph of text goes here. You can add multiple lines of text to provide more information about the document.");
                contentStream.endText();

                // Add logo
                PDImageXObject logoImage = PDImageXObject.createFromFile("C:\\Users\\garal\\IdeaProjects\\Django2\\src\\main\\resources\\image\\logo-removebg-preview.png", document);
                contentStream.drawImage(logoImage, 100, 650);

                // Draw table header
                drawTableHeader(contentStream);

                // Draw table data
                drawTableData(contentStream, posts);
            }

            document.save(filePath);
        }
    }

    private void drawTableHeader(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(100, 600); // Set position
        contentStream.showText("Titre    Description    Catégorie    Image"); // Text content
        contentStream.endText();
    }

    private void drawTableData(PDPageContentStream contentStream, List<Post> posts) throws IOException {
        int y = 580; // Initial Y position for the first row
        for (Post post : posts) {
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y); // Set position
            String rowData = post.getTitre() + "    " + post.getDescription() + "    " + post.getCategorie() + "    " + post.getImage_name();
            contentStream.showText(rowData);
            contentStream.endText();
            y -= 20; // Adjust Y position for the next row
        }
    }
}
