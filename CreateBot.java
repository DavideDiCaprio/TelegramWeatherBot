import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CreateBot extends TelegramLongPollingBot {

    
 
    @Override
    public void onUpdateReceived(Update update) {

        System.out.println(update.getMessage().getText());
        System.out.println(update.getMessage().getFrom().getFirstName());
        System.out.println(update.getMessage().getChatId().toString()); //to get ChatID for use it in your configText


        String command = update.getMessage().getText();

        //command must be created in telegram

        if (command.equals("/your command")) {
            String message = "your command test";
            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(message);

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        //
        return "";
    }

    @Override
    public String getBotToken() {
        //
        return "";
    }
    
}
