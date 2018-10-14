package bridge.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Deck() {
        cards = new ArrayList<>();
    }

    public Card getTopCard() {
        return cards.isEmpty() ? null : cards.get(cards.size() - 1);
    }

    public Card getBottomCard() {
        return cards.isEmpty() ? null : cards.get(0);
    }

    public Card getCardWithHighestRank() {
        return cards.isEmpty() ? null : Collections.max(cards);
    }

    public boolean notEmpty() {
        return !cards.isEmpty();
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCard(Rank rank, Suit suit) {
        return cards.stream().filter(card -> card.getRank().equals(rank) && card.getSuit().equals(suit)).findFirst().orElse(null);
    }

    public Card getCard(int score, Suit suit) {
        return getCard(new Rank(score), suit);
    }

    public boolean has(Rank rank, Suit suit) {
        return getCard(rank, suit) != null;
    }

    public boolean has(int score, Suit suit) {
        return getCard(score, suit) != null;
    }

    public int getCount() {
        return cards.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCount());
        sb.append(":");
        for (Card card : cards) {
            sb.append(card);
        }
        return sb.toString();
    }
}
