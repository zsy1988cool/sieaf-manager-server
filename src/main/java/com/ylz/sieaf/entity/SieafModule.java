package com.ylz.sieaf.entity;

import com.ylz.sieaf.core.macro.*;

public enum SieafModule {
	MODULE_STANDARD(SieafDef.SIEAF_TYPE_STANDARD, ""), 
	MODULE_SETTLE(SieafDef.SIEAF_TYPE_SETTLE, "settle"),
	MODULE_PRO(SieafDef.SIEAF_TYPE_PRO, "pro"),
	MODULE_SERVICE(SieafDef.SIEAF_TYPE_SERVICE, "service"),
	MODULE_LINUX(SieafDef.SIEAF_TYPE_LINUX_STANDARD, "linux");

	String moduleId;
	String moduleName;
	
	SieafModule(String id, String name) {
		moduleId = id;
		moduleName = name;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String getModuleId() {
		return moduleId;
	}
	
	public static SieafModule fromModuleId(String moduleId) {
		SieafModule[] modules = SieafModule.values();
		for(SieafModule module : modules) {
			if(module.getModuleId().equals(moduleId)) {
				return module;
			}
		}
		
		return MODULE_STANDARD;
	}
}
