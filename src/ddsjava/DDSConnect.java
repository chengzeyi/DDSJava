package ddsjava;

import bridge.domain.*;
import bridge.domain.utils.BridgeHelper;
import ddsjava.dto.BestCard;
import ddsjava.dto.FutureTricks;
import ddsjava.integration.*;

import java.util.ArrayList;
import java.util.List;

public class DDSConnect {
    public FutureTricks solveBoardPBN(BridgeGame game, int target, int solutions, int mode) throws DDSException {
        if (game.getCurrentTrick().getDeck().getCount() > 3) {
            throw new IllegalArgumentException("Invalid current trick cards count");
        }
        DDDealPBN.ByValue dealPBN = new DDDealPBN.ByValue();
        dealPBN.trump = game.getContract().getTrump().getOrder();
        dealPBN.first = game.getCurrentTrick().getTrickDealer().getOrder();
        for (int i = 0; i < game.getCurrentTrick().getDeck().getCount(); i++) {
            Card card = game.getCurrentTrick().getDeck().getCards().get(i);
            dealPBN.currentTrickRank[i] = card.getRank().getScore();
            dealPBN.currentTrickSuit[i] = card.getSuit().getOrder();
        }
        dealPBN.remainCards = DDSHelper.pbnStringToBytes(BridgeHelper.toPBN(game));
        DDFutureTricks.ByReference ddsResult = new DDFutureTricks.ByReference();
        int res = DDSImport.instance.SolveBoardPBN(dealPBN, target, solutions, mode, ddsResult, 0);
        if (res != 1) {
            System.out.println(BridgeHelper.toPBN(game));
            throw new DDSException(res);
        }
        FutureTricks result = new FutureTricks();
        result.setCards(ddsResult.cards);
        result.setNodes(ddsResult.nodes);
        List<Integer> scores = new ArrayList<>();
        for (int i : ddsResult.score) {
            scores.add(i);
        }
        result.setScores(scores);
        for (int i = 0; i < 13; i++) {
            if (ddsResult.rank[i] != 0) {
                Rank rank = new Rank(ddsResult.rank[i]);
                Suit suit = new Suit(ddsResult.suit[i]);
                result.getFutureCards().getCards().add(new Card(rank, suit));
            } else {
                break;
            }
        }
        return result;
    }

    public BestCard solveBoardPBNBestCard(BridgeGame game) throws DDSException {
        FutureTricks result = solveBoardPBN(game, -1, 1, 0);
        BestCard bestCard = new BestCard();
        bestCard.setCard(result.getFutureCards().getCards().get(0));
        bestCard.setScore(result.getScores().get(0));
        return bestCard;
    }

    public FutureTricks solveBoard(BridgeGame game) throws DDSException {
        return solveBoardPBN(game, -1, 3, 0);
    }

    public List<Contract> calcMakableContracts(String pbn) throws DDSException {
        List<Contract> ret = new ArrayList<>();
        DDTableResults.ByReference results = new DDTableResults.ByReference();
        DDTableDealPBN.ByValue dto = new DDTableDealPBN.ByValue();
        dto.setCards(DDSHelper.pbnStringToBytes(pbn));
        int res = DDSImport.instance.CalcDDtablePBN(dto, results);
        if (res != 1) {
            throw new DDSException(res);
        }
        int index = 0;
        for (Trump trump : Trump.TRUMPS) {
            for (PlayerPosition player : PlayerPosition.PLAYERS) {
                Contract contract = new Contract();
                contract.setTrump(trump);
                contract.setPlayerPosition(player);
                contract.setValue(results.resTable[index]);
                ret.add(contract);
                index++;
            }
        }
        return ret;
    }
}
