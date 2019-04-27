package com.leng.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname GenerateProposalId
 * @Date 2019/4/27 13:50
 * @Autor lengzefu
 */
public class GenerateProposalId {

    private static AtomicInteger proposalId = new AtomicInteger(0);

    public static void increment() {
       proposalId.getAndIncrement();
    }

    public static AtomicInteger getProposalId() {
        return proposalId;
    }
}
