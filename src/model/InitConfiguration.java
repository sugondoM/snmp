package model;

import java.util.List;
import java.util.Map;

public class InitConfiguration {
	List<String> ipList;
	Map<String, List<String>> ipOidMap;
	Map<String, String> ipHostnameMap;
	Map<String, String> oidDetailMap;
	Map<String, List<String>> ipConcatMetricMap;
	Map<String, String> ipSourceMap;
	Map<String, String> ipTargetMap;
	Map<String, List<String>> ipValuesMap;
	
	public Map<String, List<String>> getIpConcatMetricMap() {
		return ipConcatMetricMap;
	}
	public void setIpConcatMetricMap(Map<String, List<String>> ipConcatMetricMap) {
		this.ipConcatMetricMap = ipConcatMetricMap;
	}
	public List<String> getIpList() {
		return ipList;
	}
	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}
	public Map<String, List<String>> getIpOidMap() {
		return ipOidMap;
	}
	public void setIpOidMap(Map<String, List<String>> ipOidMap) {
		this.ipOidMap = ipOidMap;
	}
	public Map<String, String> getIpHostnameMap() {
		return ipHostnameMap;
	}
	public void setIpHostnameMap(Map<String, String> ipHostnameMap) {
		this.ipHostnameMap = ipHostnameMap;
	}
	public Map<String, String> getOidDetailMap() {
		return oidDetailMap;
	}
	public void setOidDetailMap(Map<String, String> oidDetailMap) {
		this.oidDetailMap = oidDetailMap;
	}
	public Map<String, String> getIpSourceMap() {
		return ipSourceMap;
	}
	public void setIpSourceMap(Map<String, String> ipSourceMap) {
		this.ipSourceMap = ipSourceMap;
	}
	public Map<String, String> getIpTargetMap() {
		return ipTargetMap;
	}
	public void setIpTargetMap(Map<String, String> ipTargetMap) {
		this.ipTargetMap = ipTargetMap;
	}
	public Map<String, List<String>> getIpValuesMap() {
		return ipValuesMap;
	}
	public void setIpValuesMap(Map<String, List<String>> ipValuesMap) {
		this.ipValuesMap = ipValuesMap;
	}
}
