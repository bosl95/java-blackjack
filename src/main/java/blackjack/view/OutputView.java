package blackjack.view;

import blackjack.domain.Result;
import blackjack.domain.card.Card;
import blackjack.view.dto.PlayerStatusDto;
import blackjack.view.dto.RoundStatusDto;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    private static final String TWO_CARDS_DEAL_OUT_MESSAGE = "%s와 %s에게 2장을 나누었습니다.";
    private static final String PARTICIPANT_STATUS_MESSAGE = "%s: %s";
    private static final String DELIMITER = ", ";
    private static final String GAME_RESULT_MESSAGE = "%s카드 : %s - 결과: %d";

    public static void showPlayCardStatus(String name, List<String> cards) {
        System.out.println(String.format(PARTICIPANT_STATUS_MESSAGE, name, String.join(DELIMITER, cards)));
    }

    public static void showDealerAddCard(int turnOverCount) {
        System.out.println(String.format("딜러는 %d이하라 한장의 카드를 더 받았습니다.", turnOverCount));
    }

    public static void showFinalStatus(RoundStatusDto statusDto) {
        List<PlayerStatusDto> playerStatusDto = statusDto.getPlayerStatusDto();
        System.out.println(String.format(GAME_RESULT_MESSAGE,
                statusDto.getDealerName(),
                statusDto.getDealerCardStatus().stream().collect(Collectors.joining(DELIMITER)),
                statusDto.getDealerScore()));
        playerStatusDto.forEach(dto ->
                System.out.println(String.format(GAME_RESULT_MESSAGE,
                        dto.getPlayerName(),
                        dto.getPlayerCardStatus().stream().collect(Collectors.joining(DELIMITER)),
                        dto.getPlayerScore())));
    }

    public static void showInitialStatus(RoundStatusDto roundStatusDto) {
        String dealerName = roundStatusDto.getDealerName();
        List<String> dealerCardStatus = roundStatusDto.getDealerCardStatus();
        List<PlayerStatusDto> playerStatusDto = roundStatusDto.getPlayerStatusDto();

        String playerNames = playerStatusDto.stream()
                .map(dto -> dto.getPlayerName())
                .collect(Collectors.joining(DELIMITER));

        System.out.println(String.format(TWO_CARDS_DEAL_OUT_MESSAGE, dealerName, playerNames));
        System.out.println(String.format(PARTICIPANT_STATUS_MESSAGE,
                dealerName,
                dealerCardStatus.stream().collect(Collectors.joining(DELIMITER))));

        playerStatusDto.forEach(dto ->
                System.out.println(String.format(PARTICIPANT_STATUS_MESSAGE,
                        dto.getPlayerName(),
                        dto.getPlayerCardStatus().stream().collect(Collectors.joining(DELIMITER)))));
    }

    public static void showOutcomes(Result result) {
        System.out.println("\n## 최종 승패");
        System.out.println(String.format("딜러: %d승 %d패 %d무",
                result.findDealerWinCount(), result.findDealerLoseCount(), result.findDealerDrawCount()));
        result.getPlayerResults().forEach((name, outcome) ->
                System.out.println(String.format(PARTICIPANT_STATUS_MESSAGE, name, outcome.getName())));
    }
}
