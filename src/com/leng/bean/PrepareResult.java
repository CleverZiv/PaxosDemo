package com.leng.bean;

import com.leng.entity.Proposal;

/**
 * @Classname PrepareResult
 * @Date 2019/4/27 0:01
 * @Autor lengzefu
 */
public class PrepareResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 写入的proposal
     */
    private Proposal acceptedProposal = null;

    public PrepareResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public PrepareResult(boolean isSuccess, Proposal acceptedProposal) {
        this.isSuccess = isSuccess;
        this.acceptedProposal = acceptedProposal;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Proposal getAcceptedProposal() {
        return acceptedProposal;
    }

    public void setAcceptedProposal(Proposal acceptedProposal) {
        this.acceptedProposal = acceptedProposal;
    }

    @Override
    public String toString() {
        return "PrepareResult{" +
                "isSuccess=" + isSuccess +
                ", acceptedProposal=" + acceptedProposal +
                '}';
    }
}
