package org.lossuperconocidos.sistemagestionconstancias.utilidades;
import com.aspose.words.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

public class GeneradorConstancia {
    public boolean crearContancia(String rutaDeLaPlantilla, String rutaDestinoSelecionada, Plantilla plantilla) throws FileAlreadyExistsException {
        try {
            selectTemplate();
            //TODO: Si tuvieron las plantillas en una carpeta especifica
            //String pathTemplate = Paths.get(pathDirectory, "Pantillas", NOMBRE_ARCHIVO_PROYECTO).toString();
            String nuevoArchivoNombre = "Mi_plantilla.docx";
            String rutaArchivoDestination = Paths.get(rutaDestinoSelecionada, nuevoArchivoNombre).toString();
            Files.copy(Paths.get(rutaDeLaPlantilla), Paths.get(rutaArchivoDestination), StandardCopyOption.REPLACE_EXISTING);
            changeValuesWord(rutaArchivoDestination , rutaDestinoSelecionada, plantilla);
            return true;
        }catch (FileAlreadyExistsException  e){
            throw e;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean changeValuesWord(String pathWord, String pathOutput, Plantilla plantilla) throws FileNotFoundException {
        if (pathWord == null) {
            return false;
        }

        try {
            Document document = new Document(pathWord);

            Map<String, String> tagValues = plantilla.getValores();

            // Crear una lista para almacenar todos los SDT encontrados
            List<StructuredDocumentTag> allSdts = new ArrayList<>();
            // Recorrer todas las secciones del documento
            for (Section section : document.getSections()) {
                // Añadir SDT en el cuerpo de la sección
                allSdts.addAll(getAllSdtsInCompositeNode(section.getBody()));

                // Añadir SDT en encabezados y pies de página
                for (HeaderFooter headerFooter : section.getHeadersFooters()) {
                    allSdts.addAll(getAllSdtsInCompositeNode(headerFooter));
                }
            }
            // Recorrer todos los SDT y reemplazar las etiquetas
            for (StructuredDocumentTag sdt : allSdts) {
                String tag = sdt.getTag();

                // Registro de depuración
                System.out.println("Procesando SDT con etiqueta: '" + tag + "'");

                if (tag != null && tagValues.containsKey(tag)) {
                    String value = tagValues.get(tag);
                    System.out.println("Reemplazando etiqueta '" + tag + "' con el valor: '" + value + "'");

                    // Eliminar todos los nodos hijos existentes en el SDT
                    sdt.removeAllChildren();

                    if (sdt.getLevel() == MarkupLevel.INLINE) {
                        // Si el SDT es de nivel inline, agregar un Run directamente
                        Run run = new Run(document, value);
                        sdt.appendChild(run);
                    } else if (sdt.getLevel() == MarkupLevel.BLOCK) {
                        // Si el SDT es de nivel block, agregar un Paragraph con un Run
                        Paragraph para = new Paragraph(document);
                        Run run = new Run(document, value);
                        para.appendChild(run);
                        sdt.appendChild(para);
                    } else {
                        // Manejo de otros niveles si es necesario
                        System.out.println("SDT con etiqueta '" + tag + "' tiene un nivel no esperado.");
                    }
                } else {
                    System.out.println("No se encontró valor para la etiqueta: '" + tag + "'");
                }
            }
            document.save(pathWord);
            useAsposeWords(pathOutput, pathWord);
        }catch (FileNotFoundException e){
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;

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

    private static List<StructuredDocumentTag> getAllSdtsInCompositeNode(CompositeNode node) {
        List<StructuredDocumentTag> sdtList = new ArrayList<>();
        NodeCollection sdts = node.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (int i = 0; i < sdts.getCount(); i++) {
            Node sdtNode = sdts.get(i);
            if (sdtNode instanceof StructuredDocumentTag) {
                sdtList.add((StructuredDocumentTag) sdtNode);
            }
        }
        return sdtList;
    }
}
