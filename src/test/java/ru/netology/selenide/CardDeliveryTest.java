package ru.netology.selenide;

import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {

    private String generateDate(int dayToAdd, String pattern) {
        return LocalDate.now().plusDays(dayToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
//        Configuration.holdBrowserOpen = true;
    }

    @Test
    public void shouldFillCardForm() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Александр Сергеевич");
        $("[data-test-id='phone'] input").setValue("+79005005050");
        $("[data-test-id='agreement']").click();
        $$("[type='button'").filterBy(visible).last().click();
        $(".notification__content")
                .shouldBe(text("Встреча успешно забронирована на " + generateDate(3, "dd.MM.yyyy")), Duration.ofSeconds(15));
    }

    @Test
    public void shouldFillCardFormWithListAndCalendarTool() {
        $("[data-test-id='city'] input").setValue("Во");
        $(Selectors.byText("Волгоград")).click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(generateDate(7, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Александр Сергеевич");
        $("[data-test-id='phone'] input").setValue("+79005005050");
        $("[data-test-id='agreement']").click();
        $$("[type='button'").filterBy(visible).last().click();
        $(".notification__content")
                .shouldBe(text("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")), Duration.ofSeconds(15));
    }
}

