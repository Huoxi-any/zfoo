/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.storage;

import com.zfoo.protocol.util.JsonUtils;
import com.zfoo.protocol.util.StringUtils;
import com.zfoo.storage.resource.StudentResource;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author godotg
 * @version 4.0
 */

@Ignore
public class ApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    // storage教程
    @Test
    public void startStorageTest() {
        // 加载配置文件，配置文件中必须引入storage
        // 配置文件中scan，需要映射Excel的类所在位置，会自动搜索文件夹下的Excel文件，Excel文件可以放在指定文件夹的任意目录
        // 配置文件中resource，需要映射Excel的文件所在位置
        var context = new ClassPathXmlApplicationContext("application.xml");

        // Excel的映射内容需要在被Spring管理的bean的方法上加上@ResInjection注解，即可自动注入Excel对应的对象
        // 参考StudentManager中的标准用法

        var studentManager = context.getBean(StudentManager.class);
        var studentResources = studentManager.studentResources;
        var studentCsvResources = studentManager.studentCsvResources;
        // 类名称和Excel名称必须完全一致，Excel的列名称必须对应对象的属性名称
        // 类名称和Excel名称必须完全一致，Excel的列名称必须对应对象的属性名称
        for (StudentResource resource : studentResources.getAll()) {
            logger.info(JsonUtils.object2String(resource));
        }
        System.out.println(StringUtils.MULTIPLE_HYPHENS);

        // 通过id找到对应的行
        var id = 1002;
        var valueById = studentResources.get(id);
        logger.info(JsonUtils.object2String(valueById));
        System.out.println(StringUtils.MULTIPLE_HYPHENS);

        // 通过索引找对应的行
        var valuesByIndex = studentResources.getIndex("name", "james0");
        logger.info(JsonUtils.object2String(valuesByIndex));

        // 通过索引找对应的行
        var csvValuesByIndex = studentCsvResources.getIndex("name", "james0");
        logger.info(JsonUtils.object2String(csvValuesByIndex));

        // Excel的映射内容需要在被Spring管理的bean的方法上加上@ResInjection注解，即可自动注入Excel对应的对象
        var testManager = context.getBean(TestManager.class);
        var testResources = testManager.testResources;
        for (com.zfoo.storage.resource.TestResource resource : testResources.getAll()) {
            Map<Integer, String> map = resource.getType9();
            System.err.println(map.get(1));
            logger.info(JsonUtils.object2String(resource));
        }
        // 通过id找到对应的行
        id = 2;
        var resource = testResources.get(id);
        logger.info(JsonUtils.object2String(resource));
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
    }

}
