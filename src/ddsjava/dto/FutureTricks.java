package ddsjava.dto;

import bridge.domain.Deck;

import java.util.List;

public class FutureTricks {
    private int nodes;

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    private int cards;

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    private Deck futureCards;

    public Deck getFutureCards() {
        return futureCards;
    }

    public void setFutureCards(Deck futureCards) {
        this.futureCards = futureCards;
    }

    private List<Integer> scores;

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public FutureTricks()
    {
        futureCards = new Deck();
    }
}
