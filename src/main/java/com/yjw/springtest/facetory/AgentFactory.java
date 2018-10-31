package com.yjw.springtest.facetory;

import com.yjw.springtest.dao.AgentMethod;
import com.yjw.springtest.realization.StartAgentRealization;
import com.yjw.springtest.realization.StopAgentRealization;

public class AgentFactory {

    public static AgentMethod produceStartAgent() {
        return new StartAgentRealization();
    }
    public static AgentMethod produceStopAgent() {
        return new StopAgentRealization();
    }
}
