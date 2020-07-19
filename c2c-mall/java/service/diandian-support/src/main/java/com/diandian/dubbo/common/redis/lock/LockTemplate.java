/*
 * Copyright © 2018 organization 苞米豆
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.diandian.dubbo.common.redis.lock;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;


/**
 * <p>
 * 锁模板方法
 * </p>
 *
 * @author zengzh TaoYu
 * @since 1.0.0
 */
@Slf4j
public class LockTemplate {

    @Setter
    private LockExecutor lockExecutor;

    public LockInfo lock(String key, long expire, long timeout) throws InterruptedException {
        Assert.isTrue(timeout > 0, "tryTimeout must more than 0");
        long start = System.currentTimeMillis();
        int acquireCount = 0;
        String value = UUID.randomUUID().toString();
        while (System.currentTimeMillis() - start < timeout) {
            boolean result = lockExecutor.acquire(key, value, expire);
            acquireCount++;
            if (result) {
                return new LockInfo(key, value, expire, timeout, acquireCount);
            }
            Thread.sleep(300);
        }
        log.info("lock failed, try {} times", acquireCount);
        return null;
    }

    public boolean releaseLock(LockInfo lockInfo) {
        return lockExecutor.releaseLock(lockInfo);
    }

    public void releaseLock(List<LockInfo> lockInfoList) {
        if (CollectionUtil.isNotEmpty(lockInfoList)) {
            lockInfoList.forEach(lockInfo -> {
                lockExecutor.releaseLock(lockInfo);
            });
        }
    }
}
