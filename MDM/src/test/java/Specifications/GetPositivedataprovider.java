package Specifications;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;


public class GetPositivedataprovider {
    @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] positiveData() {
        return new Object[][]{
                {5},
                {"6"},
                {"100"},
                {"199"},
                {"200"}
        };
    }
        @DataProvider
        @Description("Позитивные тесты с использованием DataProvider")
        public static Object[][] type() {
            return new Object[][]{
                    {0},
                    {1},
                    {2},
                    {3},
                    {4}
            };
    }
}