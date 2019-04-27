package com.leng.entity;

import com.leng.util.GenerateProposalId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @Classname Proposer
 * @Date 2019/4/26 23:46
 * @Autor lengzefu
 */
public class Proposer implements Runnable {
    private static Logger logger = LogManager.getLogger(Proposer.class);
    /**
     * 提议者名字
     */
    private String proposerName;
    /**
     * 提案
     */
    private Proposal proposal;
    /**
     * 决策者列表
     */
    private List<Acceptor> acceptorList;

    /**
     * 响应的决策者列表
     */
    private List<Acceptor> promisedAcceptorList = new ArrayList<>();

    /**
     * 是否超过半数
     */
    private boolean isHalfMore;

    public Proposer(String proposerName, Proposal proposal, List<Acceptor> acceptorList) {
        this.proposerName = proposerName;
        this.proposal = proposal;
        this.acceptorList = acceptorList;
        isHalfMore = false;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public List<Acceptor> getAcceptorList() {
        return acceptorList;
    }

    public void setAcceptorList(List<Acceptor> acceptorList) {
        this.acceptorList = acceptorList;
    }
    public List<Acceptor> getPromisedAcceptorList() {
        return promisedAcceptorList;
    }

    public void setPromisedAcceptorList(List<Acceptor> promisedAcceptorList) {
        this.promisedAcceptorList = promisedAcceptorList;
    }

    public boolean isHalfMore() {
        return isHalfMore;
    }

    public void setHalfMore(boolean halfMore) {
        isHalfMore = halfMore;
    }

    /**
     * 第一阶段执行方法
     * @return
     */
    private void prepare() {
        do {
            logger.info("{}开始发送prepare信号",proposerName);
            logger.info("proposalId:{},proposalValue:{}", proposal.getProposalId(),proposal.getProposalValue());
            //发送proposal给acceptorList，这是一个遍历且不断循环的过程，每一个新的循环需要重新生成proposalId
            for(Acceptor acceptor : acceptorList) {
                //模拟网络波动或故障，有些acceptorList访问不到
                if(new Random().nextInt(10) < 3) {
                    logger.warn("提议者：{}与决策者：{}失去连接", proposerName, acceptor.getAcceptorName());
                    continue;
                }
                //访问成功的acceptor将调用acceptor的onPrepare()方法
                if(acceptor.onPrepare(proposal)) {
                    //成功获取访问权，记录下来
                    logger.info("提议者：{}成功获得决策者：{}的访问权", proposerName, acceptor.getAcceptorName());
                    promisedAcceptorList.add(acceptor);
                }
            }
            //访问循环结束时，判断proposer是否获得超过半数的访问权
            if(null == promisedAcceptorList || promisedAcceptorList.size() < (acceptorList.size() / 2 + 1)) {
                //超过半数则跳出循环，否则增加proposalId，继续循环
                logger.info("获取访问权的个数{}", promisedAcceptorList.size());
                proposal.setProposalId(GenerateProposalId.getProposalId().intValue());
                logger.info("提议者：{}的提案新编号：{}", proposerName,proposal.getProposalId());
            }else {
                isHalfMore = true;
            }
        }while (!isHalfMore);
    }

    /**
     * 第二阶段执行方法
     */
    private void commit() {
        //访问获取访问权的promisedAcceptorList，、判断其提案是否全为空，全为空，则提交自己的提案
        if(!isAllNull(promisedAcceptorList)) {
            //不全为空，则从有值的里面获取编号最大的提案内容，作为自己的提案，但是提交的提案编号仍然是自己的
            String maxIdProposalValue = getMaxIdProposalValue(promisedAcceptorList);
            logger.info("提议者：{}，遵守后者认同前者原则，认同可能已经确定的值：{}", proposerName,maxIdProposalValue);
            proposal.setProposalValue(maxIdProposalValue);
        }
        //调用onCommit()
        for(Acceptor acceptor : promisedAcceptorList) {
            logger.info("提议者:{},进入第二阶段", proposerName);
            acceptor.onCommit(proposal);
        }

    }

    /**
     * 判断提案是否全为空
     */
    private boolean isAllNull(List<Acceptor> acceptorList) {
        boolean res = true;
        for(Acceptor acceptor : acceptorList) {
            if(acceptor.getAcceptedProposal() != null) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 获取最大编号的提案值
     * @param acceptorList
     * @return
     */
    private String getMaxIdProposalValue(List<Acceptor> acceptorList) {

        Integer maxId = Integer.MIN_VALUE;
        String maxIdProposalValue = "";

        for(Acceptor acceptor : acceptorList) {
            if(null != acceptor.getAcceptedProposal() && acceptor.getAcceptedProposal().getProposalId() > maxId) {
                maxIdProposalValue = acceptor.getAcceptedProposal().getProposalValue();
            }
        }
        return maxIdProposalValue;

    }



    @Override
    public void run() {
        prepare();
        commit();
    }

    @Override
    public String toString() {
        return "Proposer{" +
                "proposerName='" + proposerName + '\'' +
                ", proposal=" + proposal +
                ", acceptorList=" + acceptorList +
                ", promisedAcceptorList=" + promisedAcceptorList +
                '}';
    }
}
