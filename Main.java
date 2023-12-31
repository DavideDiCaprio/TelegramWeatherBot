import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.IOException;

public class Main {

    public static void main (String [] args) throws IOException {

        RepeatMessage sendForecastWeather = new RepeatMessage();
        sendForecastWeather.timer();

        try {

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CreateBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }
}
