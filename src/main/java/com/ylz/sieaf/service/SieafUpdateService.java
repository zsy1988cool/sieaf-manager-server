/**
 * Sieaf更新服务层
 * @author 周色议
 * @Time 2019-08-27
 *
 */

package com.ylz.sieaf.service;

import com.ylz.sieaf.core.macro.SieafDef;
import com.ylz.sieaf.core.util.SieafVersionTool;
import com.ylz.sieaf.entity.SieafModule;
import com.ylz.sieaf.entity.SieafVersion;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class SieafUpdateService {
    private String buildDownloadUrl(String moduleId, String version) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        StringBuilder contextPath = new StringBuilder();
        contextPath.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append("/sieaf/update/downloads/")
                .append(moduleId)
                .append("/")
                .append(version)
                .append(".zip");
        return contextPath.toString();
    }

    public Map<String, String> findUpdateVersionInfo(SieafVersion sieafVersion) {
        // find max version
    	String versionStr = sieafVersion.getVersion();
        File maxVersionFile = SieafVersionTool.findMaxVersionFile(sieafVersion.getModule());
        if(maxVersionFile != null) {
            String maxVersion = SieafVersionTool.getSieafVersion(maxVersionFile.getName());
            long serverVerValue = SieafVersionTool.gainVersionValue(maxVersion);
            long localVerValue = SieafVersionTool.gainVersionValue(versionStr);
            if(serverVerValue > localVerValue) {
                Map<String, String> info = new HashMap<>();
                info.put(SieafDef.SIEAF_NAME, "sieaf");
                info.put(SieafDef.SIEAF_ALIAS, "sieaf dll");
                info.put(SieafDef.SIEAF_OLD_VERSION, versionStr);
                info.put(SieafDef.SIEAF_VERSION, maxVersion);
                info.put(SieafDef.SIEAF_URL, buildDownloadUrl(sieafVersion.getModule(), maxVersion));
                return info;
            }
        }

        return null;
    }
    
    public byte[] readStandardVersionFile(String versionStr) throws Exception {
    	SieafVersion version = new SieafVersion(SieafModule.MODULE_STANDARD.getModuleId(), versionStr);
    	return readVersionFile(version);
    }

    public byte[] readVersionFile(SieafVersion version) throws Exception {
        String filePath = SieafVersionTool.findVersionPath(version.getModule(), version.getVersion());
        if(filePath == null || filePath.isEmpty()) {
            throw new Exception("could not file sieaf version!");
        }

        final int lenOfRead = 1000;
        byte[] fileValue = null;
        try(
                FileInputStream fis = new FileInputStream(new File(filePath));
                ByteArrayOutputStream bos = new ByteArrayOutputStream(lenOfRead);
           ) {
            byte[] b = new byte[lenOfRead];
            int n;
            while((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            fileValue = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return fileValue;
    }
}
