package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;

import java.util.List;

public abstract class Running implements State {
    protected Cards cards;

    public Running(Cards cards) {
        this.cards = cards;
    }

    @Override
    public List<Card> getCards() {
        return cards.getCards();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public double earningRate() {
        throw new UnsupportedOperationException("배팅률을 계산할 수 없습니다.");
    }
}
