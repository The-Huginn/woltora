package com.thehuginn.influx.db.dev.service.runtime;

import io.quarkus.arc.runtime.BeanContainerListener;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class InfluxDbRecorder {

    public BeanContainerListener setConfig(InfluxDbDevServiceConfig config) {
        return beanContainer -> {
            InfluxDbProducer producer = beanContainer.beanInstance(InfluxDbProducer.class);
            producer.setConfig(config);
        };
    }

}
