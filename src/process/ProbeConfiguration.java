package igs.uim.core.har.probe.qossrvavail;

import igs.uim.core.base.BaseProbeInformation;

public class ProbeConfiguration {


    /**
     * Created by Harfiyan on 16/04/2017.
     */
    public class ProbeInformation extends BaseProbeInformation {
        @Override
        public String GetMessageOrPollerName() {
            return "QOS_SERVER_AVAILABILITY";
        }

        @Override
        public String GetProbeName() {
            return "apmuimavailability";
        }

        @Override
        public String GetProbeDescription() {
            return "Get Qos Net Connect data and insert it to Qos Server Availability.";
        }

        @Override
        public String GetProbeCodeVersion() {
            return "Probe Qos Server Availability version 1.0.0";
        }
    }

}
