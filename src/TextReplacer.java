import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class represents a utility for replacing text in a file.
 */
public class TextReplacer {
    private String originalWord = null;
    private String newWord = null;
    private Path file = null;
    private String replaced = null;

    /**
     * Sets the original word to be replaced.
     * @param originalWord The original word to be replaced
     */
    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    /**
     * Sets the new word to replace the original word.
     * @param newWord The new word to replace the original word
     */
    public void setNewWord(String newWord) {
        this.newWord = newWord;
    }

    /**
     * Sets the file to perform text replacement.
     * @param file The file to perform text replacement
     */
    public void setFile(File file) {
        if(file == null){
            this.file = null;
            return;
        }
        this.file = file.toPath();
    }

    /**
     * Replaces the original word with the new word in the file content.
     * @return True if the replacement is successful, false otherwise
     */
    public boolean replaceWords(){
        if(this.file == null){
            return false;
        }
        byte[] input;
        try{
            input = Files.readAllBytes(this.file);
            String content = new String(input, Charset.defaultCharset());
            this.replaced = content.replaceAll(this.originalWord, this.newWord);
        } catch (IOException ex){
            return false;
        }
        return true;
    }

    /**
     * Saves the replaced content back to the file.
     * @throws IOException If an I/O error occurs while saving the file
     */
    public void saveFile() throws IOException {
        if (this.file == null) {
            throw new IOException();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.file.toString());
            writer.write(this.replaced);
        } finally {
            writer.close();
        }
    }
}
