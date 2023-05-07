import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;


public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Введіть будь-ласка назву країни англійською: ");
        String country = scan.nextLine();
        scan.close();


        try {

            String apiUrl = "https://restcountries.com/v3.1/name/" + country;
            URL url = new URL(apiUrl);

            //Открываем коннекшн и делаем ГЕТ запрос по апишнику
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Проверяем респонс
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Пишем Джсон в строку
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                scanner.close();

                //Парсим строку в Джсон объект
                JSONParser parse = new JSONParser();
                JSONArray data_obj = (JSONArray) parse.parse(inline);

                //Получаем доступ к нулевому объекту массива
                JSONObject arr = (JSONObject) data_obj.get(0);

                //Страна
                JSONObject counryObject = (JSONObject) arr.get("name");
                String name = (String) counryObject.get("common");

                //Столица
                JSONArray capitalArray = (JSONArray) arr.get("capital");
                String capital = (String) capitalArray.get(0);

                //Регион
                String region = (String) arr.get("region");


                //Выводим результат
                System.out.println(" Країна - " + name + "\n" + " Столиця - " + capital + "\n" + " Регіон - " + region);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}