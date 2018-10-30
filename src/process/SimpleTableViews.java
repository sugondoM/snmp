package process;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

//import com.nimsoft.nimbus.NimLog;
//import com.nimsoft.nimbus.NimQoS;
//import com.nimsoft.nimbus.NimUserLogin;
//import com.nimsoft.nimbus.ci.ConfigurationItem;
//import com.nimsoft.nimbus.ci.RemoteDevice;

import model.InitConfiguration;
import sun.rmi.runtime.NewThreadAction;

public class SimpleTableViews {
	private static final int DEFAULT_VERSION = SnmpConstants.version1;
	private static final String DEFAULT_PROTOCOL = "udp";
	private static final int DEFAULT_PORT = 161;
	private static final long DEFAULT_TIMEOUT = 3 * 1000L;// milliseconds
	private static final int DEFAULT_RETRY = 3;
	private static final String DEFAULT_COMMUNITY = "public";//community
	
	public static final String SNMP_VERSION = "SNMP_VERSION";
	public static final String SNMP_PROTOCOL = "SNMP_PROTOCOL";
	public static final String SNMP_PORT = "SNMP_PORT";
	public static final String SNMP_TIMEOUT = "SNMP_TIMEOUT";
	public static final String SNMP_RETRY = "SNMP_RETRY";
	public static final String SNMP_COMMUNITY= "SNMP_COMMUNITY";

	/***
	 * @param target
	 * @return
	 * @throws Exception
	 */	
	
	public List<String> insertDb(CommunityTarget target, String ipAddress, String hostname, List<String> oidList,
			Map<String, String> oidDetailMap, List<String> concatList, String sourceNimsoft, String targetNimsoft,
			List<String> valuesToSave) throws Exception {
		List<String> result = new ArrayList<String>();
		List<String> instances = selectInstance(target,oidList.get(0));
		List<String> oidDetailList = new ArrayList<>();
		Boolean onceTimes = true;
		TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		//NimUserLogin.login("agit1", "agitapm1234");
		try {
			snmp.listen();
			Map<String, String> value2save = new HashMap<String, String>();
			int instanceSize = instances.size();
			int oidSize = oidList.size();
			PDU pdu = new PDU();
			pdu.setType(PDU.GET);
			Writer writer = null;
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("logsnmp-"+ipAddress+".txt"), "utf-8"));
			
			for (int i = 0 ; i < instanceSize; i++) {
				String index = instances.get(i);
				
				for (int j = 0; j < oidSize; j++) {
					pdu.add(new VariableBinding(new OID(oidList.get(j)+index)));
					if (oidDetailList != null && oidDetailMap.get(oidList.get(j)) != null) {
						oidDetailList.add(oidDetailMap.get(oidList.get(j)));
					} else {
						oidDetailList.add("unknownMetric");
					}
					
				}
				
				ResponseEvent respEvent = snmp.send(pdu, target);
				PDU response = respEvent.getResponse();
				
				if (response != null) {
					int reSize = response.size();
					String tempMetricName = "";
					String tempMetricValue = "";
					int prevConcatIndex = -1;
				
					for (int k = 0; k < reSize; k++) {
						VariableBinding vb = response.get(k);
						String value = vb.getVariable().toString();
						//System.out.println(concatList.contains(oidDetailList.get(k)));
						if (concatList.contains(oidDetailList.get(k))) {
							if (tempMetricName == "") {
								tempMetricName = oidDetailList.get(k);
							}
							if (tempMetricValue == "") {
								tempMetricValue += value;
							} else {
								tempMetricValue += "."+value;
							}
							
							if (prevConcatIndex == -1) {
								prevConcatIndex = k;
							}
						} else {
							//System.out.println(tempMetricName);
							
							if (tempMetricName == "") {
								tempMetricName = oidDetailList.get(k);
							}
							
							if (tempMetricValue == "") {
								tempMetricValue += value;
							} else {
								tempMetricValue += "."+value;
							}
							tempMetricValue = tempMetricValue.replaceAll("/", ".");
							
							if (k == 0 || prevConcatIndex == 0) {
								//writer.write("ipAddress:"+ipAddress+"|hostname:"+hostname+"|");
								//writer.write(tempMetricName + ":" + tempMetricValue + "|");
								value2save.put("ipAddress", ipAddress);
								value2save.put("hostname", hostname);
								value2save.put(tempMetricName, tempMetricValue);
								
							} else if(k == reSize-1) {
								//riter.write(tempMetricName + ":" + tempMetricValue + ";\n");
								value2save.put(tempMetricName, tempMetricValue);
							} else {
								//writer.write(tempMetricName + ":" + tempMetricValue + "|");
								value2save.put(tempMetricName, tempMetricValue);
							}

							tempMetricName = "";
							tempMetricValue = "";
							prevConcatIndex = -1;
						}
					}
					
					//if(onceTimes) {
						if(value2save.get(sourceNimsoft) != null && value2save.get(sourceNimsoft) != null) {
							for(String val : valuesToSave){
								if(value2save.get(val) != null) {
									result.add(value2save.get(sourceNimsoft)+"|"+value2save.get(targetNimsoft)+"|"+val+"|"+value2save.get(val));
									//System.out.println(value2save.get(sourceNimsoft)+"|"+value2save.get(targetNimsoft)+"|"+val+"|"+value2save.get(val));
									writer.write(value2save.get(sourceNimsoft)+"|"+value2save.get(targetNimsoft)+"|"+val+"|"+value2save.get(val)+"\n");
									//RemoteDevice rd = GenerateRemoteDevice(value2save.get("hostname"), value2save.get("ipAddress"));
							        //ConfigurationItem ConfItem = new ConfigurationItem("1.90", "Availability", rd);
									//System.out.println("QOS_"+val);
									//NimQoS nimQos = new NimQoS(ConfItem, "1.90:1", val, true);
					                //nimQos.setSource(value2save.get(sourceNimsoft));
				                    //nimQos.setTarget(value2save.get(targetNimsoft));
				                    //nimQos.setValue(Integer.parseInt(value2save.get(val)));
				                    //nimQos.send();
				                    //nimQos.close();
								}
							}
							
						}
						onceTimes = false;
					//}
					
				}
				pdu.clear();
				pdu.setType(PDU.GET);
			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			snmp.close();
		}
		return result;
	}
	
//	public RemoteDevice GenerateRemoteDevice(String hostname, String ipAddress) {
//        HashMap<String, String> attrs = new HashMap<>();
//        attrs.put(RemoteDevice.DEVICE_DEDICATED_ATTR_KEY, "Host");
//        attrs.put(RemoteDevice.HOST_NAME_KEY, hostname);
//        attrs.put(RemoteDevice.IP_KEY, ipAddress);
//        RemoteDevice rd = new RemoteDevice(hostname, ipAddress);
//        rd.addAttributes(attrs);
//        return rd;
//    }

	/***
	 * @param target
	 * @param targetOid
	 * @return
	 * @throws Exception
	 */
	private List<String> selectInstance(CommunityTarget target,String targetOid) throws Exception {
		List<String> instances = new ArrayList<String>();
		TransportMapping transport = null;
		try {
			transport = new DefaultUdpTransportMapping();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		Snmp snmp = new Snmp(transport);

		try{
			transport.listen();
			PDU pdu = new PDU();
			OID targetOID = new OID(targetOid);
			pdu.add(new VariableBinding(targetOID));
			
			boolean finished = false;
			
			while (!finished) {
				VariableBinding vb = null;
				ResponseEvent respEvent = snmp.getNext(pdu, target);
				PDU response = respEvent.getResponse();
				if (null == response) {
					finished = true;
					break;
				} else {
					vb = response.get(0);
				}
				finished = checkWalkFinished(targetOID, pdu, vb);
				if (!finished) {
					String fullOid = vb.getOid().toString();
					String index = fullOid.replace(targetOid, "");
					 
					instances.add(index);
					
					pdu.setRequestID(new Integer32(0));
					pdu.set(0, vb);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	    	
			snmp.close();
		}
		return instances;
	}
	
	/**
	 * @param targetOID
	 * @param pdu
	 * @param vb
	 * @return
	 */
	private boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb) {
		boolean finished = false;
		if (pdu.getErrorStatus() != 0) {
			finished = true;
		} else if (vb.getOid() == null) {
			finished = true;
		} else if (vb.getOid().size() < targetOID.size()) {
			finished = true;
		} else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
			finished = true;
		} else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
			finished = true;
		} else if (vb.getOid().compareTo(targetOID) <= 0) {
			finished = true;
		}
		return finished;
	}
	
	private CommunityTarget getTarget(String ipAddress,String port) {
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("ITaddm"));
		target.setAddress(GenericAddress.parse(ipAddress+"/"+port)); // supply your own IP and port
		target.setRetries(2);
		target.setVersion(SnmpConstants.version2c);
		
		return target;
	}
	
	public List<String> checkSnmpTableView(InitConfiguration initConfig) {
		List<String> result = new ArrayList<String>();
		List<String> resulttmp = new ArrayList<String>();
		try {
			//System.out.println("Process Start");
			CommunityTarget targets = new CommunityTarget();
			String hostname = null;
			if (initConfig.getIpList() != null && initConfig.getIpList().size() > 0) {
				for (String ip : initConfig.getIpList()) {
					if (initConfig.getIpOidMap() != null 
							&& initConfig.getIpOidMap().get(ip) != null 
							&& initConfig.getIpOidMap().get(ip).size() > 0) {
						if (initConfig.getIpHostnameMap() != null 
								&& initConfig.getIpHostnameMap().get(ip) != null) {
							hostname = initConfig.getIpHostnameMap().get(ip);
						} else {
							hostname = "unknown";
						}
						targets = getTarget(ip, "161");
						resulttmp = insertDb(targets, ip, hostname, initConfig.getIpOidMap().get(ip), initConfig.getOidDetailMap(),
								initConfig.getIpConcatMetricMap().get(ip), initConfig.getIpSourceMap().get(ip), 
								initConfig.getIpTargetMap().get(ip), initConfig.getIpValuesMap().get(ip));
						result.addAll(resulttmp);
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result;
		}
		return result;
	}
}
