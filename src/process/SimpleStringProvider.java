package process;

public class SimpleStringProvider {
    public String stringProvieder(){
        String config =
            "{" +
            "\"ipList\" : [" +
            "\"10.251.36.234\"" +
            "]," +
            "\"ipOidMap\" : {" +
            "\"10.251.36.234\" : [" +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.1\"," +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.4\"," +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.5\"," +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.6\"" +
            "]" +
            "}," +
            "\"ipHostnameMap\" : {" +
            "\"10.251.36.234\" : \"lb-dc-tbs-vip01.telkomsel.co.id\"" +
            "}," +
            "\"oidDetailMap\" : {" +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.1\" : \"ltmPoolMbrStatusPoolName\"," +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.4\" : \"ltmPoolMbrStatusPort\"," +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.5\" : \"ltmPoolMbrStatusAvailState\"," +
            "\"1.3.6.1.4.1.3375.2.2.5.6.2.1.6\" : \"ltmPoolMbrStatusEnabledState\"" +
            "}," +
            "\"ipConcatMetricMap\" : {" +
            "\"10.251.36.234\" : [" +
            "\"ltmPoolMbrStatusPoolName\"" +
            "]" +
            "}," +
            "\"ipSourceMap\" : {" +
            "\"10.251.36.234\" : \"hostname\"" +
            "}," +
            "\"ipTargetMap\" : {" +
            "\"10.251.36.234\" : \"ltmPoolMbrStatusPoolName\"" +
            "}," +
            "\"ipValuesMap\" : {" +
            "\"10.251.36.234\" : [" +
            "\"ltmPoolMbrStatusAvailState\"," +
            "\"ltmPoolMbrStatusEnabledState\"" +
            "]" +
            "}" +
            "}";


        return config;
    }
}
