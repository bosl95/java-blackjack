package blackjack.domain.card;

import java.util.*;

public enum Number {
    ACE("A", 11, 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10);

    private final String name;
    private final int score;
    private final int secondScore;

    Number(String name, int score, int secondScore) {
        this.name = name;
        this.score = score;
        this.secondScore = secondScore;
    }

    Number(String name, int score) {
        this(name, score, 0);
    }

    public static Number from(String name) {
        return Arrays.stream(Number.values())
                .filter(number -> number.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 카드 숫자를 찾을 수 없습니다. (입력 : " + name + ")"));
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isAce() {
        return this.equals(ACE);
    }

    public int useSecondScore(int score) {
        return score - this.score + secondScore;
    }
}
