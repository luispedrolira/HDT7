/**
    Esta clase representa un programa controlador para una aplicación de traducción. Carga un diccionario de traducciones de palabras,
    permite a los usuarios elegir opciones como mostrar diccionarios ordenados o traducir texto, y proporciona funcionalidades
    para cargar diccionarios y archivos de texto.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Diccionario {

    /**
     * Metodo main que controla la aplicación de traducción. Carga un diccionario de traducciones de palabras, permite a los usuarios
     *
     * @param args 
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

        String file = "./src/files/diccionario.txt", OGsentence, sentenceTrans, palabraNueva, inputLanguage, outputLanguage, fileTexto = "./src/files/texto.txt";
        ArrayList<ArrayList<String>> dictionary;
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> inputText , translateText =new ArrayList<>();
        int opcion;
        boolean continuar=true;

        IWalk<ArrayList<String>> printWalk = new PrintWalk();

        ITree<String, ArrayList<String>> englishTree = new BinarySearchTree<>(Comparator.comparing(String::toString));
        ITree<String, ArrayList<String>> spanishTree = new BinarySearchTree<>(Comparator.comparing(String::toString));
        ITree<String, ArrayList<String>> frenchTree = new BinarySearchTree<>(Comparator.comparing(String::toString));
        ITree<String, ArrayList<String>> arbolUsar = null;

        dictionary = loadDictionary(file);

        for (int i = 0; i < dictionary.size(); i++) {
            englishTree.insert(dictionary.get(i).get(0), dictionary.get(i));
            spanishTree.insert(dictionary.get(i).get(1), dictionary.get(i));
            frenchTree.insert(dictionary.get(i).get(2), dictionary.get(i));
        }

        inputText = loadTexto(fileTexto);

        int contadorIngles = 0;
        int contadorEspanol = 0;
        int contadorFrances = 0;

        for (String palabraTexto : inputText) {
            for (ArrayList<String> entradaDiccionario : dictionary) {
                if (palabraTexto.equalsIgnoreCase(entradaDiccionario.get(0))) {
                    contadorIngles++;
                }
                if (palabraTexto.equalsIgnoreCase(entradaDiccionario.get(1))) {
                    contadorEspanol++;
                }
                if (palabraTexto.equalsIgnoreCase(entradaDiccionario.get(2))) {
                    contadorFrances++;
                }
            }
        }

        if (contadorIngles > contadorEspanol + contadorFrances) {
            inputLanguage="Inglés";
        } else if (contadorEspanol > contadorIngles + contadorFrances) {
            inputLanguage="Español";
        } else if (contadorFrances > contadorIngles + contadorEspanol) {
            inputLanguage="Francés";
        } else {
            inputLanguage="Undefined";
        }

        while (continuar) {

            System.out.println("Elija una opción:\n1)Mostrar diccionario ordenado\n2)Traducir el texto");
            opcion = scanner.nextInt();scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("------------------------------------------------");
                    System.out.println("INGLÉS\n");
                    englishTree.InOrderWalk(printWalk);
                    System.out.println("------------------------------------------------");
                    System.out.println("ESPAÑOL\n");
                    spanishTree.InOrderWalk(printWalk);
                    System.out.println("------------------------------------------------");
                    System.out.println("FRANCÉS\n");
                    frenchTree.InOrderWalk(printWalk);
                    System.out.println("------------------------------------------------");

                    continuar=verMenu(scanner);

                    break;

                case 2:
                    translateText.clear();

                    if (inputLanguage.equals("Undefined")) {
                        System.out.println("No se reconoce el idioma");
                    }

                    System.out.println("Idioma: " + inputLanguage);

                    System.out.println("Ingrese el idioma al que quiere traducir");
                    outputLanguage = scanner.nextLine();


                    int idiomaIndex = -1;
                    switch(inputLanguage){
                        case "Inglés":
                            arbolUsar = englishTree;
                            break;
                        case "Español":
                            arbolUsar = spanishTree;
                            break;
                        case "Francés":
                            arbolUsar = frenchTree;
                            break;
                    }

                    switch (outputLanguage) {
                        case "Ingles":
                            idiomaIndex = 0;
                            break;
                        case "Espanol":
                            idiomaIndex = 1;
                            break;
                        case "Frances":
                            idiomaIndex = 2;
                            break;
                        default:
                            System.out.println("No se pudo reconocer el idioma, por favor intentelo de nuevo");
                            break;
                    }

                    for (String palabra : inputText) {
                        ArrayList<String> traduccion = null;
                        traduccion = arbolUsar.find(palabra);
                        if (traduccion != null && idiomaIndex != -1) {
                            palabraNueva = traduccion.get(idiomaIndex);
                            translateText.add(palabraNueva);
                        } else {
                            translateText.add("'"+palabra+"'");
                        }
                    }

                    OGsentence = String.join(" ", inputText);
                    sentenceTrans = String.join(" ", translateText);

                    System.out.println(OGsentence);
                    System.out.println(sentenceTrans);

                    continuar=verMenu(scanner);

                    break;

                default:
                    System.out.println("¡¡Opción no existe!!");
                    continuar=false;
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Muestra un mensaje al usuario preguntando si desea volver al menú principal o no.
     *
     * @param scanner
     * @return true si el usuario desea volver al menú principal, false de lo contrario
     */
    public static boolean verMenu(Scanner scanner){
        String opcion;
        System.out.println("Desea regresar al menú? (Si/No)");
        opcion = scanner.nextLine();
        switch (opcion) {
            case "Si":
                return true;
            case "No":
                return false;
            default:
                return false;
        }
    }

    /**
     * Carga texto desde un archivo especificado.
     *
     * @param fileName El nombre del archivo que contiene el texto
     * @return Un ArrayList de cadenas que representan el texto
     * @throws IOException Si ocurre un error de E/S mientras se lee el archivo.
     */
    public static ArrayList<ArrayList<String>> loadDictionary(String file) throws IOException {
        ArrayList<ArrayList<String>> dictionary = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String linea = reader.readLine();
            while (linea != null) {
                String[] words = linea.split(",");
                ArrayList<String> lineArray = new ArrayList<>();
                for (String word : words) {
                    lineArray.add(word.toLowerCase().trim());
                }
                dictionary.add(lineArray);
                linea = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return dictionary;
    }

    /**
     * Carga texto desde un archivo especificado.
     *
     * @param fileName El nombre del archivo que contiene el texto
     * @return Un ArrayList de cadenas que representan el texto
     * @throws IOException Si ocurre un error de E/S mientras se lee el archivo.
     */
    public static ArrayList<String> loadTexto(String file) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String linea = reader.readLine();
            while (linea != null) {
                String[] palabrasDeLaLinea = linea.split("\\s+");
                for (String palabra : palabrasDeLaLinea) {
                    String palabraLimpia = palabra.trim().toLowerCase();
                    if (!palabraLimpia.isEmpty()) {
                        words.add(palabraLimpia);
                    }
                }
                linea = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return words;
    }
}