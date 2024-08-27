package org.chainoptim.core.tenant.subscription.service;

import org.chainoptim.shared.enums.Feature;

public interface FeatureCounterService {
    long getCountByFeature(Integer entityId, Feature feature);
}
