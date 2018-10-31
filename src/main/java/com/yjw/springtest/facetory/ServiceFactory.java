package com.yjw.springtest.facetory;

import com.yjw.springtest.dao.ServiceMethod;
import com.yjw.springtest.realization.StartServiceRealization;
import com.yjw.springtest.realization.StopServiceRealization;

public class ServiceFactory {
    public static ServiceMethod produceStartService() {
        return new StartServiceRealization();
    }

    public static ServiceMethod produceStopService() {
        return new StopServiceRealization();
    }
}
