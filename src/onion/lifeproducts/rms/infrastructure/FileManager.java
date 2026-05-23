package onion.lifeproducts.rms.infrastructure;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Optional;


public class FileManager
{
    public FileManager()
    {

    }
    public Optional<String> readFromFile(String path)
    {
        Path filePath = Paths.get(path);
        try 
        {
            String content = Files.readString(filePath);
            return Optional.of(content);
        } 
        catch (IOException e) 
        {
            return Optional.ofNullable(null);
        }
    }
    public Boolean writeToFile(String path, String content)
    {
        Path filePath = Paths.get(path);
        try
        {
            
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
    public Boolean appendToFile(String path, String content)
    {
        Path filePath = Paths.get(path);
        try
        {
            
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
