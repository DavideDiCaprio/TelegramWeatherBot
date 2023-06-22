import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RepeatMessage {

    public void timer() {

        var task = new TimerTask() {

            @Override
            public void run() {
                GetWeather getWeather = new GetWeather();
                String messagge = null;

                try {
                    messagge = getWeather.getForecastWeather();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                SendMessage telegramMessage = new SendMessage();
                telegramMessage.sendToTelegram(messagge);
            }
        };

        var timer = new Timer();
        timer.scheduleAtFixedRate(task,0,600000); //time expressed in millisecond

    }
}
