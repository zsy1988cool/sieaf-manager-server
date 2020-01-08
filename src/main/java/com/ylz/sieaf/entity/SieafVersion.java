package com.ylz.sieaf.entity;

public class SieafVersion {
    String version;
    String module;
    
    public SieafVersion(String m, String v) {
    	version = v;
    	module = m;
    }

    public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
