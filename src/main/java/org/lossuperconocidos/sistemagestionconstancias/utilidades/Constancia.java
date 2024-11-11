package org.lossuperconocidos.sistemagestionconstancias.utilidades;
//import com.aspose.words.Document;
//import com.aspose.words.SaveFormat;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.aspose.words.SaveFormat;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import com.aspose.words.Document;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Constancia {
    public boolean crearContancia() {
        try {
            selectTemplate();
            String pathDirectory = System.getProperty("user.dir");
            if (pathDirectory == null) {
                throw new Exception("No se encontró la ruta");
            }
            String pathTemplate = Paths.get(pathDirectory, "PantillaContancias", "Proyecto.docx").toString();
            String fileNameDestination = "Mi_plantilla.docx";
            String pathDestination = Paths.get(pathDirectory, "RutaDestino", fileNameDestination).toString();
            Files.copy(Paths.get(pathTemplate), Paths.get(pathDestination));
            System.out.println("Ruta del Word: " + pathTemplate);
            System.out.println("Ruta destino del PDF: " + pathDestination);
            changeValuesWord(pathDestination, Paths.get(pathDirectory, "RutaDestino").toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean changeValuesWord(String pathWord, String pathOutput) {
        if (pathWord == null) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(pathWord);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Diccionario con las etiquetas y sus respectivos valores a reemplazar
            Map<String, String> tagValues = new HashMap<>();
            tagValues.put("NombreDirector", "Juan Pérez");
            tagValues.put("NombreAcademico", "Nombre real de académico Innovador");
            tagValues.put("-", "01/01/2024 - 1999");

            // Reemplazo de texto
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        for (Map.Entry<String, String> entry : tagValues.entrySet()) {
                            if (text.contains(entry.getKey())) {
                                text = text.replace(entry.getKey(), entry.getValue());
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }

            // Guardar cambios en el archivo Word
            try (FileOutputStream fos = new FileOutputStream(pathWord)) {
                document.write(fos);
            }

            // Convierte el documento de Word modificado a PDF usando Aspose.Words
            useAsposeWords(pathOutput, pathWord);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void useAsposeWords(String pathOutput, String pathWord) {
        try {
            String rutaSalidaPDF = Paths.get(pathOutput, "MiPDF.pdf").toString();
            Document doc = new Document(pathWord);
            doc.save(rutaSalidaPDF, SaveFormat.PDF);
            System.out.println("El archivo ha sido convertido a PDF con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void selectTemplate() {
        // TODO: Implementar lógica de selección de plantilla si es necesario
    }
}
