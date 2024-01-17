package ma.oneshoot.oneshootmain.upload;

import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.oneshootmain.extract.IPdfTextExtractorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
//@Profile("dev")
@RequestScope
public class FileSystemStorageService implements IStorageService{
    private Path location ;
    private Path distination;
    private IPdfTextExtractorServiceImpl extractorService;

    public FileSystemStorageService(IPdfTextExtractorServiceImpl extractorService) {
        this.extractorService = extractorService;
        this.init();
    }

    @Override
    public void init() {
        String uniqueDirectoryName;
        File file;
        do {
            uniqueDirectoryName = UUID.randomUUID().toString();
            file = new File("upload-dir/"+uniqueDirectoryName);
        } while (file.exists());
        try {
            this.location =  Files.createDirectory(Paths.get("upload-dir",uniqueDirectoryName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String store(MultipartFile file) {
        log.info("Uploading file {} ", file.getOriginalFilename());
        if(file.isEmpty()){
            throw new RuntimeException("Failed to store empty file");
        }
        Path distination = this.location.resolve(
            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
            .normalize().toAbsolutePath();
        if (!distination.getParent().equals(this.location.toAbsolutePath())){
            throw new RuntimeException("Cannot store file outside current directory");
        }
        try (InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, distination, StandardCopyOption.REPLACE_EXISTING);
            log.info("File {} isUploaded successfully", file.getOriginalFilename());
            return distination.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store file");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.location, 1)
                .filter(path -> !path.equals(this.location))
                .map(this.location::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files");
        }
    }

    @Override
    public Path load(String filename) {
        return this.location.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {
        try {
            Files.walk(this.location)
                .filter(path -> !path.equals(this.location))
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete stored files");
        }
    }

    @Override
    public Path getUploadDistination() {
        return this.location;
    }
}
