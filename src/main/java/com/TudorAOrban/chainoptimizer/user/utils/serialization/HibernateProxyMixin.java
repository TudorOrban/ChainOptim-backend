package com.TudorAOrban.chainoptimizer.user.utils.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public interface HibernateProxyMixin {}
