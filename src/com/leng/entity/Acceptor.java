package com.leng.entity;

import com.leng.Common.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname Acceptor
 * @Date 2019/4/26 23:47
 * @Autor lengzefu
 */
public class Acceptor {
    private static Logger logger = LogManager.getLogger(Proposer.class);

    private String acceptorName;

    /**
     * 已经写入的提案
     */
    private Proposal acceptedProposal = null;

    /**
     * 最新被授予访问权的提案的Id
     */
    private Integer latestProposalId = null;

    public Acceptor(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public Integer getLatestProposalId() {
        return latestProposalId;
    }

    public void setLatestProposalId(Integer latestProposalId) {
        this.latestProposalId = latestProposalId;
    }

    public Proposal getAcceptedProposal() {
        return acceptedProposal;
    }

    public void setAcceptedProposal(Proposal acceptedProposal) {
        this.acceptedProposal = acceptedProposal;
    }

    /**
     * acceptor响应proposer的方法
     * @param proposal
     * @return
     */
    public synchronized boolean onPrepare(Proposal proposal) {
        //当前acceptor的latestProposalId、是否可以授予访问权
        logger.info("决策者：{}此次收到提案编号为：{}", acceptorName, proposal.getProposalId());
        if(null == latestProposalId || proposal.getProposalId() > latestProposalId) {
            logger.info("新提案编号：{}获取到访问权",proposal.getProposalId());
            latestProposalId = proposal.getProposalId();
            return true;
        }
        return true;
    }

    public synchronized boolean onCommit(Proposal proposal) {
        //验证是否具有访问权
        if(proposal.getProposalId().equals(latestProposalId)) {
            acceptedProposal = proposal;
            return true;
        }

        //不具有访问权，拒绝修改
        logger.info("编号为:{},不具有访问权，拒绝修改",proposal.getProposalId());
        return false;
    }

    @Override
    public String toString() {
        return "Acceptor{" +
                "acceptorName='" + acceptorName + '\'' +
                ", acceptedProposal=" + acceptedProposal +
                ", latestProposalId=" + latestProposalId +
                '}';
    }
}
