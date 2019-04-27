import com.leng.entity.Acceptor;
import com.leng.entity.Proposal;
import com.leng.entity.Proposer;
import com.leng.util.GenerateProposalId;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //生成3个Acceptor
        Acceptor acceptor1 = new Acceptor("acceptor-01");
        Acceptor acceptor2 = new Acceptor("acceptor-02");
        Acceptor acceptor3 = new Acceptor("acceptor-03");
        List<Acceptor> acceptorList = new ArrayList<>();
        acceptorList.add(acceptor1);
        acceptorList.add(acceptor2);
        acceptorList.add(acceptor3);

        //生成2个proposal
        Proposal proposal1 = new Proposal(GenerateProposalId.getProposalId().intValue(), "proposer-A的提议");
        Proposal proposal2 = new Proposal(GenerateProposalId.getProposalId().intValue(), "proposer-B的提议");

        //生成2个proposer
        Proposer proposerA = new Proposer("proposer-A", proposal1, acceptorList);
        Proposer proposerB = new Proposer("proposer-B", proposal2, acceptorList);

        new Thread(proposerA).start();
        new Thread(proposerB).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("acceptor-01的提案为：{}"+ acceptor1.getAcceptedProposal().toString());
        System.out.println("acceptor-02的提案为：{}"+ acceptor2.getAcceptedProposal().toString());
        System.out.println("acceptor-03的提案为：{}"+ acceptor3.getAcceptedProposal().toString());

    }
}
