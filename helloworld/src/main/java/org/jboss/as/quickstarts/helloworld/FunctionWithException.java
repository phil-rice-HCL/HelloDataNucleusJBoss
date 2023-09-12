package org.jboss.as.quickstarts.helloworld;

public interface FunctionWithException<From,To> {
    To apply(From from) throws Exception;
}
