package ma.oneshoot.oneshootmain.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/uploads")
public class UploadResourceController {
    @Autowired
    private UploadRepository uploadRepository;


    @GetMapping
    public List<Upload> getUploads(){
        return uploadRepository.findAll();
    }
}
