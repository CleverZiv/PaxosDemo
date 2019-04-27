package com.leng.entity;

/**
 * @Classname Proposal
 * @Date 2019/4/26 23:47
 * @Autor lengzefu
 */
public class Proposal {
    /**
     * 提案序号
     */
    private Integer proposalId;

    /**
     * 提案内容
     */
    private String proposalValue;

    public Proposal() {}

    public Proposal(Integer proposalId,String proposalValue) {
        this.proposalId = proposalId;
        this.proposalValue = proposalValue;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposalValue() {
        return proposalValue;
    }

    public void setProposalValue(String proposalValue) {
        this.proposalValue = proposalValue;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "proposalId=" + proposalId +
                ", proposalValue='" + proposalValue + '\'' +
                '}';
    }
}
