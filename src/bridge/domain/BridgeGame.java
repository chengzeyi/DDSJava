package bridge.domain;

import bridge.domain.utils.BridgeHelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class BridgeGame {
    private Trick currentTrick;

    public Trick getCurrentTrick() {
        return currentTrick;
    }

    private PlayerPosition nextPlayer;

    private Dictionary<PlayerPosition, Deck> gameState;

    public Dictionary<PlayerPosition, Deck> getGameState() {
        return gameState;
    }

    private List<Trick> tricks;

    public List<Trick> getTricks() {
        return tricks;
    }

    private PlayerPosition declarer;

    public PlayerPosition getDeclarer() {
        return declarer;
    }

    private PlayerPosition dummy;

    public PlayerPosition getDummy() {
        return dummy;
    }

    private Contract contract;

    public Contract getContract() {
        return contract;
    }

    public BridgeGame(Dictionary<PlayerPosition, Deck> gameState, String contractShortStr)
    {
        this.gameState = gameState;
        contract = new Contract(contractShortStr);
        tricks = new ArrayList<>();
        declarer = contract.getPlayerPosition();
        dummy = BridgeHelper.getNextPlayerPosition(BridgeHelper.getNextPlayerPosition(declarer));
        currentTrick = new Trick();
        currentTrick.setTrickDealer(BridgeHelper.getNextPlayerPosition(declarer));
    }

    private static PlayerPosition findWinner(Trick trick, Trump trump) {
        Card highestTrump = trick.getDeck().getCards().stream().filter(card -> card.getSuit().equals(trump.getSuit())).max(Card::compareTo).orElse(null);
        Card highestInTrickDealerSuit = trick.getDeck().getCards().stream().filter(card -> card.getSuit().equals(trick.getTrickDealerSuit())).max(Card::compareTo).orElse(null);
        return highestTrump != null ? highestTrump.getPlayerPosition() : highestInTrickDealerSuit.getPlayerPosition();
    }

    public int getCardsRemaining() {
        int total = 0;
        Enumeration<Deck> decks = gameState.elements();
        while (decks.hasMoreElements()) {
            total += decks.nextElement().getCount();
        }
        return total;
    }

    public PlayerPosition playCard(Card card, PlayerPosition playerPosition) {
        nextPlayer = BridgeHelper.getNextPlayerPosition(playerPosition);
        if (currentTrick.getDeck().getCount() <= 4) {
            card.setPlayerPosition(playerPosition);
            currentTrick.getDeck().addCard(card);
        }
        if (currentTrick.getDeck().getCount() == 4) {
            tricks.add(currentTrick);
            PlayerPosition winner = findWinner(currentTrick, contract.getTrump());
            currentTrick.setTrickWinner(winner);
            nextPlayer = winner;
            currentTrick = new Trick();
            currentTrick.setTrickDealer(winner);
        }
        gameState.get(playerPosition).removeCard(card);
        return nextPlayer;
    }

    public int getNorthSouthTricksMade() {
        return Integer.parseInt(String.valueOf(tricks.stream().filter(trick -> trick.getTrickWinner().equals(PlayerPosition.NORTH) || trick.getTrickWinner().equals(PlayerPosition.SOUTH)).count()));
    }

    public int getEastWestTricksMade() {
        return Integer.parseInt(String.valueOf(tricks.stream().filter(trick -> trick.getTrickWinner().equals(PlayerPosition.EAST) || trick.getTrickWinner().equals(PlayerPosition.WEST)).count()));
    }
}
