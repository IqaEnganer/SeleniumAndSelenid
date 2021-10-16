import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppOrderSelenideTest {
    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
    }

    // Все поля заполнены верно
    @Test
    public void shouldOrderCardWithCssSelectors() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Самар Самаров");
        $("[type='tel']").setValue("+79561234567");
        $(".checkbox__box").click();
        $("button").click();
        $(".paragraph_theme_alfa-on-white").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    // Неверно заполненное поле ФИО
    @Test
    public void shouldShowErrorIfIncorrectFillingOfTheFullName() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Igor");
        $("[type='tel']").setValue("+79561234567");
        $(".checkbox__box").click();
        $("button").click();
        $("[data-test-id ='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Пустое поле ФИО
    @Test
    public void shouldShowErrorIfEmptyFieldWithFullName() {
        open("http://localhost:9999");
        $("[type='text']").setValue("");
        $("[type='tel']").setValue("+79561234567");
        $(".checkbox__box").click();
        $("button").click();
        $("[data-test-id ='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    // Неверно заполненное поле для номера телефона
    @Test
    public void shouldShowErrorIfIncorrectFillingOfThePhoneNumber() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Самар Самаров");
        $("[type='tel']").setValue("79561234567");
        $(".checkbox__box").click();
        $("button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // Пустое поле для ввода номера телефона
    @Test
    public void shouldShowErrorIfEmptyFieldWithPhoneNumber() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Самар Самаров");
        $("[type='tel']").setValue("");
        $(".checkbox__box").click();
        $("button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    // Отправка без флажка согласия
    @Test
    public void shouldShowErrorWithoutAgreementInCheckBox() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Самар Самаров");
        $("[type='tel']").setValue("+79012345678");
        $("button").click();
        $(".input_invalid").should(exist);
    }
}
