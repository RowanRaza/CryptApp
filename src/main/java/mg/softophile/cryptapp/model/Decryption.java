package mg.softophile.cryptapp.model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;

public class Decryption {
    public static String decrypt(String encryptedFile_, String pathTarget, String pathToSecretKey, boolean deleteEncryptedFile){
        String result = "";
        try {
            SecretKey secretKey = loadKey(pathToSecretKey);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            File encryptedFile = new File(encryptedFile_);
            File pathTg = new File(pathTarget);
            if (!encryptedFile.isFile())
                return "Opération intérrompue, choisissez un fichier valide!";
            if (!pathTg.isDirectory())
                return "Opération intérrompue, choisissez un répertoire valide!";

            FileInputStream inputStream = new FileInputStream(encryptedFile);
            byte[] encryptedBytes = new byte[(int) encryptedFile.length()];
            inputStream.read(encryptedBytes);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            inputStream.close();
            String fileNmWithoutExtension = "";
            char usedSymbol = '/';
            if (encryptedFile_.contains("\\")){
                fileNmWithoutExtension = encryptedFile_.substring(
                    encryptedFile_.lastIndexOf("\\")+1, encryptedFile_.lastIndexOf(".")
                );
                usedSymbol = '\\';
            }else {
                fileNmWithoutExtension = encryptedFile_.substring(
                    encryptedFile_.lastIndexOf("/")+1, encryptedFile_.lastIndexOf(".")
                );
            }
            File decryptedFile = new File(pathTarget+usedSymbol+fileNmWithoutExtension+".mp4");
            FileOutputStream outputStream = new FileOutputStream(decryptedFile);
            outputStream.write(decryptedBytes);
            outputStream.close();

            result = ("La vidéo a été déchiffrée avec succès.");

            if (deleteEncryptedFile) {
                encryptedFile.delete();
                File key = new File(pathToSecretKey);
                key.delete();
            }
        }catch(BadPaddingException bpe) {
            result = "Opération interrompue, il se pourrait que la clé que vous avez choisi ne soit pas la bonne!";
        }
        catch (Exception e) {
            result = "Opération interrompue, un problème est survenu!";
        }
        return result;
    }

    private static SecretKey loadKey(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
        SecretKey secretKey = (SecretKey) inputStream.readObject();
        inputStream.close();
        return secretKey;
    }
}
