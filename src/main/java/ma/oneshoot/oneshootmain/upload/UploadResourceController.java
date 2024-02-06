package ma.oneshoot.oneshootmain.upload;

import ma.oneshoot.oneshootmain.utils.annotations.TenantSpecific;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cl/uploads")
public class UploadResourceController {

    private UploadRepository uploadRepository;

    @Autowired
    public UploadResourceController(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @GetMapping
    @TenantSpecific
    public List<Upload> getUploads(){
        return uploadRepository.findAll();
    }
}
