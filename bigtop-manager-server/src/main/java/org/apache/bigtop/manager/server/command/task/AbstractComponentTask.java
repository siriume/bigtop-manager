/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bigtop.manager.server.command.task;

import org.apache.bigtop.manager.common.message.entity.payload.CommandPayload;
import org.apache.bigtop.manager.common.message.entity.pojo.PackageInfo;
import org.apache.bigtop.manager.common.message.entity.pojo.PackageSpecificInfo;
import org.apache.bigtop.manager.common.utils.JsonUtils;
import org.apache.bigtop.manager.dao.repository.ComponentDao;
import org.apache.bigtop.manager.grpc.generated.CommandRequest;
import org.apache.bigtop.manager.grpc.generated.CommandType;
import org.apache.bigtop.manager.server.holder.SpringContextHolder;
import org.apache.bigtop.manager.server.model.dto.PackageDTO;
import org.apache.bigtop.manager.server.model.dto.PackageSpecificDTO;
import org.apache.bigtop.manager.server.model.dto.StackDTO;
import org.apache.bigtop.manager.server.utils.StackUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractComponentTask extends AbstractTask {

    protected ComponentDao componentDao;

    public AbstractComponentTask(TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    protected void injectBeans() {
        super.injectBeans();

        this.componentDao = SpringContextHolder.getBean(ComponentDao.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CommandRequest getCommandRequest() {
        StackDTO stackDTO = StackUtils.getServiceStack(taskContext.getServiceName());
        CommandPayload commandPayload = new CommandPayload();
        commandPayload.setServiceName(taskContext.getServiceName());
        commandPayload.setComponentName(taskContext.getComponentName());
        commandPayload.setServiceUser(taskContext.getServiceUser());
        commandPayload.setStackName(stackDTO.getStackName());
        commandPayload.setStackVersion(stackDTO.getStackVersion());
        commandPayload.setCommand(getCommand());
        commandPayload.setCustomCommand(getCustomCommand());

        Map<String, Object> properties = taskContext.getProperties();

        commandPayload.setPackageSpecifics(
                convertPackageSpecificInfo((List<PackageSpecificDTO>) properties.get("packageSpecifics")));
        if (stackDTO.getStackName().equals("infra")) {
            commandPayload.setClusterHosts((Map<String, List<String>>) properties.get("clusterHosts"));
        }
        CommandRequest.Builder builder = CommandRequest.newBuilder();
        builder.setType(CommandType.COMPONENT);
        builder.setPayload(JsonUtils.writeAsString(commandPayload));

        return builder.build();
    }

    private List<PackageSpecificInfo> convertPackageSpecificInfo(List<PackageSpecificDTO> packageSpecificDTOList) {
        if (packageSpecificDTOList == null) {
            return new ArrayList<>();
        }

        List<PackageSpecificInfo> packageSpecificInfos = new ArrayList<>();
        for (PackageSpecificDTO packageSpecificDTO : packageSpecificDTOList) {
            PackageSpecificInfo packageSpecificInfo = new PackageSpecificInfo();
            packageSpecificInfo.setArch(packageSpecificDTO.getArch());
            List<PackageInfo> packageInfoList = new ArrayList<>();
            for (PackageDTO packageDTO : packageSpecificDTO.getPackages()) {
                PackageInfo packageInfo = new PackageInfo();
                packageInfo.setName(packageDTO.getName());
                packageInfo.setChecksum(packageDTO.getChecksum());
                packageInfoList.add(packageInfo);
            }
            packageSpecificInfo.setPackages(packageInfoList);
            packageSpecificInfos.add(packageSpecificInfo);
        }

        return packageSpecificInfos;
    }
}
