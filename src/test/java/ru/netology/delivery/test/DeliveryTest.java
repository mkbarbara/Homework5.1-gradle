package ru.netology.delivery.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        open("http://localhost:9999");
        var validUser = DataGenerator.Registration.generateUser("ru");

        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        pushValues(validUser, firstMeetingDate);
        $(withText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));

        open("http://localhost:9999");
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        pushValues(validUser, secondMeetingDate);
        $$("button").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
    }

    private void pushValues(DataGenerator.UserInfo user, String date) {
        $("[data-test-id = city] input").setValue(user.getCity());
        $("[data-test-id = name] input").setValue(user.getName());
        $("[data-test-id = phone] input").setValue(user.getPhone());
        $("[data-test-id = date] [type = tel]").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id = date] [type = tel]").setValue(date);
        $("[data-test-id = agreement] [role= presentation]").click();
        $$("button").find(exactText("Запланировать")).click();
    }
}
