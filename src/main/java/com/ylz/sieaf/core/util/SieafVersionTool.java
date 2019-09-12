/**
 * Sieaf版本操作工具类
 * @author 周色议
 * @Time 2019-08-27
 *
 */

package com.ylz.sieaf.core.util;

import java.io.File;
import java.io.FileFilter;

public class SieafVersionTool {
    static {
        String tmpDir = System.getProperty("java.io.tmpdir");
        String delimit = tmpDir.endsWith("\\") ? "" : "/";
        versionDir=  tmpDir + delimit + "sieaf/";
    }
    private static final String versionDir;
    public static final long INVALID_VERSION = -1;
    public static final int VERSION_LEN = 3;

    public static String getUpdateDir() {
        return versionDir;
    }
    public static long gainVersionValue(String version) {
        if(!version.startsWith("v"))
            return INVALID_VERSION;

        String splitChar = "\\.";
        String[] subVList = version.substring(1).split(splitChar);
        if(VERSION_LEN == subVList.length) {
            long baseValue = 0;
            int baseNum = 1000000;
            for(int i = 0; i < subVList.length; i++) {
                String sV = subVList[i];
                int nV = Integer.valueOf(sV);
                if(nV < 0) {
                    return  INVALID_VERSION;
                }

                baseValue += nV * baseNum;
                baseNum /= 1000;
            }
            return baseValue;
        }

        return INVALID_VERSION;
    }

    public static String findVersionPath(String version) {
        String absolutePath = versionDir + version;
        File verFile = new File(absolutePath);
        if(!verFile.exists() || verFile.isDirectory()) {
            return null;
        }

        return absolutePath;
    }

    public static File findMaxVersionFile() {
        System.out.println(versionDir);
        File verDir = new File(versionDir);
        File maxVerFile = null;
        if(verDir.exists() && verDir.isDirectory()) {
            File[] verFiles = verDir.listFiles(new SieafFileFilter());

            long preValue = 0;
            for(File vF : verFiles) {
                if(vF.isFile()) {
                    String version = getSieafVersion(vF.getName());
                    long versionValue = gainVersionValue(version);
                    if(versionValue != INVALID_VERSION && versionValue > preValue) {
                        maxVerFile = vF;
                        preValue = versionValue;
                    }
                }
            }
        }

        return maxVerFile;
    }

    public static String getSieafVersion(String fileName)
    {
        return fileName.replaceFirst(".zip", "");
    }

    public static class SieafFileFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
            if(file.isDirectory())
                return false;

            String name = file.getName();
            return name.startsWith("v") && name.endsWith("zip");
        }
    }
}
