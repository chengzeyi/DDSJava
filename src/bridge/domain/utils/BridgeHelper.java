package bridge.domain.utils;

import bridge.domain.*;

import java.util.*;
import java.util.stream.Collectors;

public class BridgeHelper {
    public static PlayerPosition getNextPlayerPosition(PlayerPosition currentSide) {
        if (currentSide.getOrder() == 3) {
            return PlayerPosition.NORTH;
        } else {
            return new PlayerPosition(currentSide.getOrder() + 1);
        }
    }
    
    public static BridgeGame getGameFromPBN(String pbnHand, String contract) {
        Dictionary<PlayerPosition, Deck> hands = new Hashtable<>();
        String[] pbnHands = pbnHand.split(":|\\s");
        PlayerPosition side = new PlayerPosition(pbnHands[0].charAt(0));
        for (int i = 1; i < 5; i++) {
            hands.put(side, getDeck(pbnHands[i]));
            side = getNextPlayerPosition(side);
        }
        return new BridgeGame(hands, contract);
    }

    public static String toPBN(BridgeGame game) {
        StringBuilder sb = new StringBuilder();
        sb.append(game.getDeclarer().getFirstLetter());
        sb.append(":");
        PlayerPosition side = game.getDeclarer();
        for (int i = 1; i < 5; i++) {
            Deck deck = game.getGameState().get(side);
            sb.append(deckToPBNHand(deck));
            if (i != 4) {
                sb.append(' ');
            }
            side = getNextPlayerPosition(side);
        }
        return sb.toString();
    }

    public static String deckToPBNPlay(Deck deck) {
        StringBuilder sb = new StringBuilder();
        for (Card card : deck.getCards().stream().sorted().collect(Collectors.toList())) {
            sb.append(" " + card.getSuit().getShortName() + card.getRank().getShortName());
        }
        return sb.toString().trim();
    }

    private static String deckToPBNHand(Deck deck) {
        StringBuilder sb = new StringBuilder();
        for (Suit suit : Suit.SUITS) {
            for ( Card card : deck.getCards().stream().filter(card -> card.getSuit().equals(suit)).collect(Collectors.toList())) {
                sb.append(card.getRank().getShortName());
            }
            sb.append(".");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static Enumeration<Card> readPBNCard(Suit suit, String cardString) {
        Vector<Card> cards = new Vector<>();
        for (char c : cardString.toCharArray()) {
            cards.add(new Card(new Rank(c), suit));
        }
        return cards.elements();
    }

    private static Deck getDeck(String pbnHand) {
        Deck deck = new Deck();
        List<Card> list = new ArrayList<>();
        String[] suits = pbnHand.split("\\.");
        for (int i = 0; i < 4; i++) {
            Enumeration<Card> enumeration = readPBNCard(Suit.SUITS.get(i), suits[i]);
            while (enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
        }
        deck.setCards(list);
        return deck;
    }

    private static Card getCard(String card) {
        return new Card(new Rank(card.charAt(1)), Suit.SUITS.stream().filter(suit -> suit.getShortName() == card.charAt(0)).findAny().orElse(null));
    }
}
