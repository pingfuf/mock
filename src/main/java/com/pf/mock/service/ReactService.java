package com.pf.mock.service;

import com.pf.mock.data.RnVersion;
import com.pf.mock.utils.RnUtil;
import org.springframework.stereotype.Service;

@Service
public class ReactService {

    public RnVersion getLatestVersion() {
        RnVersion version = new RnVersion();
        version.setCode(2);
        version.setDate("2017-12-02");
        version.setName("1.1");
        version.setTag("v1.1");

        return version;
    }

    public RnVersion getHyVersion() {
        RnVersion version = new RnVersion();
        version.setCode(2);
        version.setDate("2017-12-02");
        version.setName("1.1");
        version.setTag("http://192.168.3.229:8080/mock/rn/downloadHy");

        return version;
    }

    public RnVersion getVersion(int code) {
        return RnUtil.getVersionMap().get(code);
    }
}
