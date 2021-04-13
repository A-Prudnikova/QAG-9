package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class RegFormTests extends TestBase {
    Faker faker = new Faker();

    String name = faker.name().firstName(),
            surname = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            phone = faker.number().digits(10),
            subject = "English",
            address = faker.address().streetAddress(),
            state = "NCR",
            city = "Delhi";

    @Test
    void successfulFillTest() {
        step("Open reg form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });
        step("Fill reg form", () -> {
            $("#firstName").setValue(name);
            $("#lastName").setValue(surname);
            $("#userEmail").setValue(email);
            $(byText("Male")).click();
            $(byText("Female")).click();
            $(byText("Other")).click();
            $("#userNumber").setValue(phone);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue("0");
            $(".react-datepicker__year-select").selectOptionByValue("1910");
            $(".react-datepicker__day--003").click();
            $("#subjectsInput").setValue(subject).pressEnter();
            $(byText("Sports")).click();
            //$(byText("Reading")).click();
            //$(byText("Music")).click();
            $("#uploadPicture").uploadFromClasspath("ava.jpg");
            $("#currentAddress").setValue(address);
            $("#react-select-3-input").setValue(state).pressEnter();
            $("#react-select-4-input").setValue(city).pressEnter();
        });
        step("Submit from", () -> $("#submit").click());
        step("Verify successful submit", () -> {
            $(".modal-content").shouldBe(visible).shouldHave(
                    text(name),
                    text(surname),
                    text(email),
                    text("Other"),
                    text(phone),
                    text("03 January,1910"),
                    text(subject),
                    text("Sports"),
                    text("ava.jpg"),
                    text(address),
                    text(state),
                    text(city)
            );
        });

    }
}
