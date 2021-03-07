package blackjack.controller;

import blackjack.domain.Answer;
import blackjack.domain.Result;
import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.user.Dealer;
import blackjack.domain.user.Player;
import blackjack.domain.user.Users;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import blackjack.view.dto.PlayerStatusDto;
import blackjack.view.dto.RoundStatusDto;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GameController {
    public static final int GAME_OVER_SCORE = 21;
    private static final int FIRST_TWO_CARD = 2;
    private final InputView inputView = new InputView(new Scanner(System.in));

    public void start() {
        Users users = Users.of(inputView.getPlayerNames());
        Deck deck = new Deck(Card.getShuffledCards());
        startRound(users, deck);
        OutputView.showInitialStatus(createRoundStatusDto(users));
        addUsersCardOrPass(users, deck);
        OutputView.showFinalStatus(createRoundStatusDto(users));
        OutputView.showOutcomes(new Result(users, GAME_OVER_SCORE));
    }

    public void startRound(Users users, Deck deck) {
        users.getDealer().addFirstCards(deck.drawCards(FIRST_TWO_CARD));
        for (Player player : users.getPlayers()) {
            player.addFirstCards(deck.drawCards(FIRST_TWO_CARD));
        }
    }

    private RoundStatusDto createRoundStatusDto(Users users) {
        RoundStatusDto roundStatusDto = new RoundStatusDto(users.getDealer().getName(),
                users.getDealer().getCardsStatus(),
                createPlayerStatusDto(users.getPlayers()),
                users.getDealer().calculateScore(GAME_OVER_SCORE));
        return roundStatusDto;
    }

    private List<PlayerStatusDto> createPlayerStatusDto(List<Player> players) {
        return players.stream()
                .map(this::getPlayerStatusDto)
                .collect(Collectors.toList());
    }

    private PlayerStatusDto getPlayerStatusDto(Player player) {
        return new PlayerStatusDto(player.getName(), player.getCardsStatus(), player.calculateScore(GAME_OVER_SCORE));
    }

    private void addUsersCardOrPass(Users users, Deck deck) {
        users.getPlayers().forEach(player -> askAddCardOrPass(player, deck));
        if (users.getDealer().isGameOver(GAME_OVER_SCORE)) {
            users.getDealer().addCard(deck.drawCard());
            OutputView.showDealerAddCard(Dealer.TURN_OVER_COUNT);
        }
    }

    private void askAddCardOrPass(Player player, Deck deck) {
        String answer = "";
        while (!player.isGameOver(GAME_OVER_SCORE) && !Answer.NO.equals(answer)) {
            answer = inputView.getCardOrPass(player.getName());
            Answer.of(answer).executeByAnswer(player, deck);
            showPlayerCardStatus(player, answer);
        }
    }

    private void showPlayerCardStatus(Player player, String answer) {
        if (Answer.YES.equals(answer)) {
            OutputView.showPlayCardStatus(player.getName(), player.getCardsStatus());
        }
    }
}
