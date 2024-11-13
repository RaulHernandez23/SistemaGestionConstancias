package org.lossuperconocidos.sistemagestionconstancias.utilidades;
//import com.aspose.words.Document;
//import com.aspose.words.SaveFormat;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;




import com.aspose.words.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GeneradorConstancia {
    public static final String NOMBRE_ARCHIVO_PROYECTO = "Proyecto.docx";
    public static final String NOMBRE_ARCHIVO_JURADO = "Jurado.docx";
    public static final String NOMBRE_ARCHIVO_PLADEA = "PLADEA.docx";
    public static final String NOMBRE_ARCHIVO_IMPARTICIONEE = "ImparticionEE.docx";
    public boolean crearContancia(String rutaDeLaPlantilla, String pathDestination) throws FileAlreadyExistsException {
        try {
            selectTemplate();
            //TODO: Si tuvieron las plantillas en una carpeta especifica
            //String pathTemplate = Paths.get(pathDirectory, "Pantillas", NOMBRE_ARCHIVO_PROYECTO).toString();
            String nuevoArchivoNombre = "Mi_plantilla.docx";

            String rutaArchivoDestination = Paths.get(pathDestination, nuevoArchivoNombre).toString();
            Files.copy(Paths.get(rutaDeLaPlantilla), Paths.get(rutaArchivoDestination), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Ruta del Word: " + rutaDeLaPlantilla);
            System.out.println("Ruta destino del PDF: " + pathDestination);
            changeValuesWord(rutaArchivoDestination , pathDestination);
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

    private static boolean changeValuesWord(String pathWord, String pathOutput) throws FileNotFoundException {
        if (pathWord == null) {
            return false;
        }

        try {
            // Cargar el documento con Aspose.Words
            Document document = new Document(pathWord);

            // Diccionario con las etiquetas y sus valores
            Map<String, String> tagValues = new HashMap<>();
            tagValues.put("NombreDirector", "Juan Pérez");
            tagValues.put("NombreAcademico", "Nombre");
            tagValues.put("Duracion", "01/01/2024 - 199aa9");
            tagValues.put("ProyectoRealizado", "proyec realizadoooo");
            tagValues.put("Lugar", "Aquiii");
            //IMPLEMENTACION LA MAS FACIL Y RAPIDA
//            // Reemplazar las etiquetas en el documento
//            for (Map.Entry<String, String> entry : tagValues.entrySet()) {
//                document.getRange().replace(entry.getKey(), entry.getValue(), new com.aspose.words.FindReplaceOptions());
//            }
            //IMPLEMENTACION 1
            // Reemplazar las etiquetas en el documento
//            for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) document.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true)) {
//                String tag = sdt.getTag(); // Obtener el tag del control estructurado
//                if (tag != null && tagValues.containsKey(tag)) {
//                    String value = tagValues.get(tag);
//
//                    // Obtener los nodos hijos del SDT
//                    NodeCollection childNodes = sdt.getChildNodes(NodeType.ANY, false);
//                    if (childNodes.getCount() > 0) {
//                        Node sdtContentNode = childNodes.get(0); // Obtener el primer nodo hijo
//                        if (sdtContentNode != null && sdtContentNode.isComposite()) {
//                            CompositeNode sdtContent = (CompositeNode) sdtContentNode;
//
//                            // Eliminar todos los nodos hijos existentes en sdtContent
//                            sdtContent.removeAllChildren();
//
//                            // Crear el nuevo contenido según el tipo de SDT
//                            if (sdt.getSdtType() == SdtType.PLAIN_TEXT) {
//                                // Para texto plano, agregar un Run directamente
//                                Run run = new Run(document, value);
//                                sdtContent.appendChild(run);
//                            } else if (sdt.getSdtType() == SdtType.RICH_TEXT) {
//                                // Para texto enriquecido, agregar un Paragraph con un Run
//                                Paragraph para = new Paragraph(document);
//                                Run run = new Run(document, value);
//                                para.appendChild(run);
//                                sdtContent.appendChild(para);
//                            }
//                            // Manejar otros tipos de SDT si es necesario
//                        }
//                    }
//                }
//            }

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

//            // Recorrer todos los SDT y reemplazar las etiquetas
//            for (StructuredDocumentTag sdt : allSdts) {
//                String tag = sdt.getTag();
//
//                // Registro de depuración
//                System.out.println("Procesando SDT con etiqueta: '" + tag + "'");
//
//                if (tag != null && tagValues.containsKey(tag)) {
//                    String value = tagValues.get(tag);
//                    System.out.println("Reemplazando etiqueta '" + tag + "' con el valor: '" + value + "'");
//
//                    // Obtener todos los nodos Run dentro del SDT
//                    NodeCollection runs = sdt.getChildNodes(NodeType.RUN, true);
//
//                    // Crear una lista separada de los nodos Run
//                    List<Run> runList = new ArrayList<>();
//
//                    for (int i = 0; i < runs.getCount(); i++) {
//                        runList.add((Run) runs.get(i));
//                    }
//
//                    // Actualizar el texto de cada nodo Run
//                    if (!runList.isEmpty()) {
//                        for (Run run : runList) {
//                            run.setText(value);
//                        }
//                    } else {
//                        // Si no hay nodos Run, crear un nuevo párrafo con un Run
//                        Paragraph para = new Paragraph(document);
//                        Run run = new Run(document, value);
//                        para.appendChild(run);
//                        sdt.appendChild(para);
//                    }
//                } else {
//                    System.out.println("No se encontró valor para la etiqueta: '" + tag + "'");
//                }
//            }

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


            // Guardar el documento Word modificado
            document.save(pathWord);

            // Convertir el documento Word modificado a PDF
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

    // Método auxiliar para obtener todos los SDT en un CompositeNode
    private static List<StructuredDocumentTag> getAllSdtsInCompositeNode(CompositeNode node) {
        List<StructuredDocumentTag> sdtList = new ArrayList<>();
        // Obtain all SDTs in the node
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
