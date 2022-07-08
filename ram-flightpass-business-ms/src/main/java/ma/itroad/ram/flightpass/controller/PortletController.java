package ma.itroad.ram.flightpass.controller;


import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.PortletBean;
import ma.itroad.ram.flightpass.model.enums.PageName;
import ma.itroad.ram.flightpass.service.FileStorageService;
import ma.itroad.ram.flightpass.service.PortletService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PortletController {

    private PortletService portletService;
    private FileStorageService fileStorageService;


    @PostMapping(path = "/api/portlet", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer> postPortlet(@Valid @RequestPart("portlet") PortletBean portletbean, @RequestPart("file") MultipartFile file) {
        return new ResponseEntity(portletService.postPortlet(portletbean, file), HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/portlet")
    public ResponseEntity<List<PortletBean>> getPortlets(@RequestParam(required = false) Long segmentId,
                                                         @RequestParam(required = false) String pageName){
        if (segmentId!=null && pageName != null){
            return new ResponseEntity<>(portletService.getPortletsBySegmentIdAndPageName(segmentId, PageName.valueOf(pageName)),HttpStatus.OK);
        }else if(segmentId!=null){
            return new ResponseEntity<>(portletService.getPortletsBySegmentId(segmentId),HttpStatus.OK);

        }
        else if(pageName != null){
            return new ResponseEntity<>(portletService.getPortletsByPageName(PageName.valueOf(pageName)),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(portletService.getPortlets(),HttpStatus.OK);
        }

    }

    @GetMapping(path = "/api/portlet/{portletId}")
    public ResponseEntity<PortletBean> getPortlet(@PathVariable Long portletId){
        return new ResponseEntity(portletService.getPortletById(portletId) , HttpStatus.OK);
    }



    @GetMapping("/api/portlet/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
    @PatchMapping(path = "/api/portlet/{portletId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer> patchPortletById(@PathVariable Long portletId ,@RequestPart("portlet") PortletBean portletbean, @RequestPart("file") MultipartFile file) {
        return new ResponseEntity(portletService.patchPortletById(portletId, portletbean ,file), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "api/portlet/{portletId}")
    public ResponseEntity<HttpStatus> deletePortletById(@PathVariable Long portletId){
        portletService.deletePortletById(portletId);
        return new ResponseEntity<>(HttpStatus.OK);
    }






}

