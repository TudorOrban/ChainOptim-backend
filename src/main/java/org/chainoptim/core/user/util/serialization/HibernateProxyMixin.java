package org.chainoptim.core.user.util.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public interface HibernateProxyMixin {}
