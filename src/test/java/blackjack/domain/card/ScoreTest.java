package blackjack.domain.card;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreTest {
    @Test
    void generate_test() {
        Score score = new Score(20);
        assertThat(score).isEqualTo(new Score(20));
    }
}