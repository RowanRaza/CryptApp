package mg.softophile.cryptapp.model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Encryption {
    public static String encrypt(String pathName, String pathTarget, int keySize, boolean deleteSource) {
        String result = "";
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize);
            SecretKey secretKey = keyGenerator.generateKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            File inputFile = new File(pathName);
            File pathTg = new File(pathTarget);
            if (!inputFile.isFile())
                return "Opération intérrompue, choisissez un fichier valide!";
            if (!pathTg.isDirectory())
                return "Opération intérrompue, choisissez un répertoire valide!";

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] encryptedBytes = cipher.doFinal(inputBytes);
            inputStream.close();
            String fileNmWithoutExtension = "";
            char usedSymbol = '/';
            if (pathName.contains("\\")){
                fileNmWithoutExtension = pathName.substring(
                    pathName.lastIndexOf("\\")+1, pathName.lastIndexOf(".")
                );
                usedSymbol = '\\';
            }else {
                fileNmWithoutExtension = pathName.substring(
                    pathName.lastIndexOf("/")+1, pathName.lastIndexOf(".")
                );
            }
            File encryptedFile = new File(pathTarget+usedSymbol+fileNmWithoutExtension+".enc");
            FileOutputStream outputStream = new FileOutputStream(encryptedFile);
            outputStream.write(encryptedBytes);
            outputStream.close();

            saveKey(secretKey, pathTarget+usedSymbol+fileNmWithoutExtension+".key");
            if (deleteSource) {
                inputFile.delete();
            }
            result = "Opération terminée, le fichier est protégé!";
        } catch (Exception e) {
            result = "Opération interrompue, un problème est survenu!";
        }
        return result;
    }
    private static void saveKey(SecretKey secretKey, String fileName) throws Exception {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        outputStream.writeObject(secretKey);
        outputStream.close();
    }
}
