package distantcontrolclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Gestionnaire de JSON avec typage.
 * @author Johan
 * @param <T> type reel des objets JSON
 */
public abstract class GenericJsonManager<T> {
    public static final String DEFAULT_DIRECTORY = "";
    private String directory;
    private String fileName;
    
    /**
     * Constructeur du gestionnaire de JSON.
     * @param directory chemin du repertoire utilise
     * @param fileName nom du fichier json
     */
    public GenericJsonManager(String directory, String fileName) {
        setDirectory(directory);
        setFileName(fileName);
    }
    
    /**
     * Constructeur du gestionnaire de JSON.
     * @param fileName nom du fichier json
     */
    public GenericJsonManager(String fileName) {
        this(DEFAULT_DIRECTORY, fileName);
    }
    
    /**
     * Cree l'objet correspondant au json donne.
     * @param json le format json de l'objet a recreer
     * @return l'objet correspondant au json
     */
    public abstract T createObject(JSONObject json);
    
    /**
     * Retourne le chemin du repertoire utilise.
     * @return le chemin du repertoire utilise
     */
    public String getDirectory() {
        return this.directory;
    }
    
    /**
     * Retourne le nom du fichier properties utilise.
     * @return le nom du fichier properties utilise
     */
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * Modifie le repertoire utilise.
     * @param directory le path correspondant au nouveau repertoire
     */
    public void setDirectory(String directory) {
        this.directory = directory;
        if (!this.directory.isEmpty() && (this.directory.charAt(this.directory.length()-1) != File.separator.toCharArray()[0])) this.directory += File.separator;
    }
    
    /**
     * Modifie le nom du fichier properties utilise.
     * @param fileName le nouveau nom du fichier
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
        if (!this.fileName.contains(".")) { //extension oubliée
            this.fileName += ".json";
        }
    }
    
    /**
     * Retourne le chemin de stockage.
     * @return le chemin de stockage
     */
    public String getPath() {
        return getPath(this.fileName);
    }
    
    /**
     * Retourne le chemin de stockage pour le fichier donne.
     * @param fileName nom du fichier
     * @return le chemin de stockage
     */
    public String getPath(String fileName) {
        return this.directory + fileName;
    }
    
    /**
     * Retourne true si le fichier donne contient l'objet donne, false sinon.
     * @param json objet a chercher
     * @param fileName nom du fichier
     * @return true si le fichier donne contient l'objet donne, false sinon
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     */
    public boolean contains(JSONObject json, String fileName) throws FileNotFoundException {
        for (JSONObject o : readAsJSON(fileName)) {
            if (o.toString().equals(json.toString())) return true;
        }
        return false;
    }
    
    /**
     * Retourne true si le fichier contient l'objet donne, false sinon.
     * @param json objet a chercher
     * @return true si le fichier donne contient l'objet donne, false sinon
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     */
    public boolean contains(JSONObject json) throws FileNotFoundException {
        return contains(json, this.fileName);
    }
    
    /**
     * Retourne true si le fichier donne contient l'objet donne, false sinon.
     * @param object objet a chercher
     * @param fileName nom du fichier
     * @return true si le fichier donne contient l'objet donne, false sinon
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     */
    public boolean contains(T object, String fileName) throws FileNotFoundException {
        for (T storedObject : read(fileName)) {
            if (storedObject.equals(object)) return true;
        }
        return false;
    }
    
    /**
     * Retourne true si le fichier contient l'objet donne, false sinon.
     * @param object objet a chercher
     * @return true si le fichier donne contient l'objet donne, false sinon
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     */
    public boolean contains(T object) throws FileNotFoundException {
        return contains(object, this.fileName);
    }
    
    /**
     * Cree le fichier json s'il n'existe pas deja.
     * @throws IOException exception levee quand il est impossible de creer le fichier json
     */
    public void createFile() throws IOException {
        File dir = new File(this.directory);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(getPath());
        if (!file.exists()) {
            file.createNewFile();
        } else if (!file.isFile()) {
            throw new IOException("The file '" + file.getAbsolutePath() + "' is a directory, impossible to create the json file");
        }
    }
    
    /**
     * Retourne sous le format JSON la liste des objets stockes dans le fichier donne.
     * @param fileName fichier dans lequel lire les objets
     * @return la liste des objets stockes
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas ou n'est pas lisible
     */
    public ArrayList<JSONObject> readAsJSON(String fileName) throws FileNotFoundException {
        JSONTokener tokener = new JSONTokener(new FileReader(getPath(fileName)));
        ArrayList<JSONObject> result = new ArrayList<>();
        JSONArray objectsStored;
        try {
            objectsStored = new JSONObject(tokener).getJSONArray("Objects");
            for (int i=0; i<objectsStored.length(); i++) {
                result.add(objectsStored.getJSONObject(i));
            }
        } catch (JSONException je) {
            throw new FileNotFoundException(je.getMessage());
        }
        return result;
    }
    
    /**
     * Retourne la liste des objets stockes dans le fichier donne.
     * @param fileName fichier dans lequel lire les objets
     * @return la liste des objets stockes
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas ou n'est pas lisible
     */
    public ArrayList<T> read(String fileName) throws FileNotFoundException {
        JSONTokener tokener = new JSONTokener(new FileReader(getPath(fileName)));
        ArrayList<T> result = new ArrayList<>();
        JSONArray objectsStored;
        try {
            objectsStored = new JSONObject(tokener).getJSONArray("Objects");
            for (int i=0; i<objectsStored.length(); i++) {
                T storedObject = createObject(objectsStored.getJSONObject(i));
                if (storedObject != null) result.add(createObject(objectsStored.getJSONObject(i)));
            }
        } catch (JSONException je) {
            throw new FileNotFoundException(je.getMessage());
        }
        return result;
    }
    
    /**
     * Retourne la liste des objets stockes.
     * @return la liste des objets stockes
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas ou n'est pas lisible
     */
    public ArrayList<T> read() throws FileNotFoundException {
        return read(this.fileName);
    }
    
    /**
     * Ecrit l'objet donne en memoire dans le fichier donne.
     * @param json objet json a persister
     * @param fileName nom du fichier de stockage
     * @throws IOException exception levee quand l'ecriture a echouee
     */
    public void writeJSON(JSONObject json, String fileName) throws IOException {
        if ((json == null) || contains(json)) return;
        createFile();
        try (FileWriter writer = new FileWriter(getPath(fileName))) {
            writer.write(json.toString(1));
        }
    }
    
    
    
    /**
     * Stock les JSONObject donnes dans le fichier donne.
     * @param jsonList liste des JSONObject a stocker
     * @param fileName nom du fichier de stockage
     * @throws IOException exception levee quand l'ecriture a echouee
     */
    public void writeJSON(List<JSONObject> jsonList, String fileName) throws IOException {
        JSONObject json = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        json.put("Objects", jSONArray); //JSONException impossible car clé jamais null
        jsonList.forEach(o -> {
            jSONArray.put(o);
        });
        writeJSON(json, fileName);
    }
    
    /**
     * Stock les objets donnes dans le fichier donne.
     * @param objectsList liste des objets a stocker
     * @param fileName nom du fichier de stockage
     * @throws IOException exception levee quand l'ecriture a echouee
     */
    public void write(List<T> objectsList, String fileName) throws IOException {
        JSONObject json = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        json.put("Objects", jSONArray); //JSONException impossible car clé jamais null
        objectsList.forEach(o -> {
            jSONArray.put(new JSONObject(o));
        });
        writeJSON(json, fileName);
    }
    
    /**
     * Stock les objets donnees.
     * @param objectsList liste des objets a stocker
     * @throws IOException exception levee quand l'ecriture a echouee
     */
    public void write(List<T> objectsList) throws IOException {
        write(objectsList, this.fileName);
    }
    
    /**
     * Ajoute l'objet dans le fichier de donnees s'il n'y est pas deja. 
     * Se base sur les getters pour la persistance.
     * @param object objet a ajouter au fichier
     * @param fileName nom du fichier de stockage
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void write(T object, String fileName) throws IOException {
        if (object == null) return;
        ArrayList<T> storedObjects;
        try {
            storedObjects = read(fileName);
        } catch (FileNotFoundException fnfe) {
            storedObjects = new ArrayList<>();
        }
        for (T storedObject : storedObjects) {
            if (storedObject.equals(object)) return;
        }
        storedObjects.add(object);
        write(storedObjects, fileName);
    }
    
    /**
     * Ajoute l'objet dans le fichier de stockage s'il n'y est pas deja se base 
     * sur les getters pour la persistance.
     * @param object objet a ajouter au fichier
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void write(T object) throws IOException {
        if (object == null) return;
        write(object, this.fileName);
    }
    
    /**
     * Supprime l'objet donne du fichier donne.
     * @param json objet a supprimer du fichier
     * @param fileName nom du fichier de stockage
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void delete(JSONObject json, String fileName) throws FileNotFoundException, IOException {
        List<JSONObject> storedObjects = readAsJSON(fileName).stream()
                .filter(o -> !o.toString().equals(json.toString()))
                .collect(Collectors.toList());
        writeJSON(storedObjects, fileName);
    }
    
    /**
     * Supprime l'objets donne du fichier.
     * @param json objet a supprimer du fichier
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void delete(JSONObject json) throws FileNotFoundException, IOException {
        delete(json, this.fileName);
    }
    
    /**
     * Supprime l'objet donne du fichier donne.
     * @param object objet a supprimer du fichier
     * @param fileName nom du fichier de stockage
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void delete(T object, String fileName) throws FileNotFoundException, IOException {
        if (object == null) return;
        List<T> storedObjects = read(fileName).stream()
                .filter(storeObject -> !storeObject.equals(object))
                .collect(Collectors.toList());
        write(storedObjects, fileName);
    }
    
    /**
     * Supprime l'objets donne du fichier.
     * @param object objet a supprimer du fichier
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void delete(T object) throws FileNotFoundException, IOException {
        if (object == null) return;
        delete(object, this.fileName);
    }
    
    /**
     * Supprime tous les objets qui possedent la valeur indiquee associee a 
     * la cle donnee dans le fichier donnee.
     * @param key cle
     * @param value valeur du/des objet(s) a supprimer
     * @param fileName nom du fichier de stockage
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void delete(String key, String value, String fileName) throws FileNotFoundException, IOException {
        List<JSONObject> storedObjects = readAsJSON(fileName).stream()
                .filter(storeObject -> !storeObject.has(key) || !storeObject.getString(key).equals(value))
                .collect(Collectors.toList());
        writeJSON(storedObjects, fileName);
    }
    
    /**
     * Supprime tous les objets qui possedent la valeur indiquee associee a 
     * la cle donnee dans le fichier.
     * @param key cle
     * @param value valeur du/des objet(s) a supprimer
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void delete(String key, String value) throws FileNotFoundException, IOException {
        delete(key, value, this.fileName);
    }
    
    /**
     * Remplace l'objet oldObject par newObject dans le fichier donne.
     * @param oldObject ancien objet
     * @param newObject nouvel objet
     * @param fileName nom du fichier de stockage
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void replace(T oldObject, T newObject, String fileName) throws FileNotFoundException, IOException {
        delete(oldObject, fileName);
        write(newObject, fileName);
    }
    
    /**
     * Remplace l'objet oldObject par newObject dans le fichier.
     * @param oldObject ancien objet
     * @param newObject nouvel objet
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void replace(T oldObject, T newObject) throws FileNotFoundException, IOException {
        replace(oldObject, newObject, this.fileName);
    }
    
    /**
     * Remplace l'objet avec la valeur donnee associee a la cle donnee 
     * par newObject dans le fichier donne.
     * @param key la cle
     * @param value valeur associee a la cle
     * @param newObject nouvel objet
     * @param fileName nom du fichier de stockage
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void replace(String key, String value, T newObject, String fileName) throws FileNotFoundException, IOException {
        delete(key, value, fileName);
        write(newObject, fileName);
    }
    
    /**
     * Remplace l'objet avec la valeur donnee associee a la cle donnee 
     * par newObject dans le fichier.
     * @param key la cle
     * @param value valeur associee a la cle
     * @param newObject nouvel objet
     * @throws FileNotFoundException exception levee quand le fichier n'existe pas
     * @throws IOException exception levee quand l'ecriture dans le fichier a echouee
     */
    public void replace(String key, String value, T newObject) throws FileNotFoundException, IOException {
        replace(key, value, newObject, this.fileName);
    }
}
