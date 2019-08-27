/**
 * Sieaf更新控制层
 * @author 周色议
 * @Time 2019-08-27
 *
 */
package com.ylz.sieaf.web;

import com.ylz.sieaf.entity.ResultModel;
import com.ylz.sieaf.service.SieafUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/update")
public class SieafUpdateController {

    @Autowired
    SieafUpdateService sieafUpdateService;

    @PostMapping(value = "/findUpdateVersion")
    ResultModel findUpdateVersion(@RequestParam(value = "version", required = true) String clientVersion) {
        ResultModel resultModel = new ResultModel();
        Map<String, String> updateVersionInfo = sieafUpdateService.findUpdateVersionInfo(clientVersion);
        if(updateVersionInfo != null) {
            resultModel.setFlag(ResultModel.SUCCESS);
            resultModel.setData(updateVersionInfo);
        } else {
            resultModel.setFlag(ResultModel.FAILED);
        }
        return resultModel;
    }

    @GetMapping(value = "/downloads/{version}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> downloads(@PathVariable(value="version") final String version) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDispositionFormData("attachment", new String(version.getBytes("UTF-8"), "ISO8859-1"));
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(sieafUpdateService.readVersionFile(version), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
