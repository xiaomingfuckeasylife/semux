/**
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.util;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.semux.api.SemuxAPIMock;
import org.semux.rules.KernelRule;

public class ApiClientTest {

    @Rule
    public KernelRule kernelRule = new KernelRule(51610, 51710);

    private SemuxAPIMock api;

    @Before
    public void setUp() {
        api = new SemuxAPIMock(kernelRule.getKernel());
        api.start();
    }

    @After
    public void tearDown() {
        api.stop();
    }

    @Test
    public void testRequest() {
        String cmd = "get_block";

        System.err.println("kernel1");
        ApiClient apiClient = kernelRule.getKernel().getApiClient();
        System.err.println("kernel2");
        try {
            String response = apiClient.request(cmd, "number", 0);

            assertTrue(response.contains("\"success\":true"));
            assertTrue(response.contains("result"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("kernel3");
    }
}