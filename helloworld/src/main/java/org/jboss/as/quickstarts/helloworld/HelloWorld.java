/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.Query;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * A simple CDI service which is able to say hello to someone
 *
 * @author Pete Muir
 */


@PersistenceCapable(table = "helloworld")
@Entity
public class HelloWorld {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @PrimaryKey
    @Id
    int messageId;

    @Column(name = "hellomessage")
    String helloMessage;

    public HelloWorld(int messageId, String helloMessage) {
        this.messageId = messageId;
        this.helloMessage = helloMessage;
    }
    public HelloWorld() {
    }

    public String getHelloMessage() {
        return helloMessage;
    }

    public void setHelloMessage(String helloMessage) {
        this.helloMessage = helloMessage;
    }

    @Override
    public String toString() {
        return "HelloWorld [messageId=" + messageId + ", helloMessage=" + helloMessage + "]";
    }

    public void selectAll() throws Exception {
        PmHelper.usePopulatedDb(pm -> {
            while (true) {
                Query q2 = pm.newQuery("SELECT hw FROM HelloWorld hw", HelloWorld.class);
                List result = q2.executeList();
                logger.info(result.toString());
                System.out.println(result.toString());
                Thread.sleep(500);
            }
        });
    }

    public String selectAllReturnString() {
        return PmHelper.usePopulatedDb(pm -> {
            Query q2 = pm.newQuery("SELECT FROM " + HelloWorld.class.getName());
            List result = q2.executeList();
            System.out.println(result.toString());
            return "Result" + result.toString();
        });
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public static void main(String[] args) throws Exception {
        HelloWorld hw = new HelloWorld();
        hw.selectAll();
    }
}
