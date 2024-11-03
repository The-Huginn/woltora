package com.thehuginn.influx.db.dev.service.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class InfluxDbDevServiceProcessor {

    companion object {
        const val FEATURE = "influx-db-dev-service-extension"
    }

    @BuildStep
    fun feature() : FeatureBuildItem = FeatureBuildItem(FEATURE)
}
