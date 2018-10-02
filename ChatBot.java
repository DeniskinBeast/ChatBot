import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ChatBot {

    private static String help = "Напиши свой ответ в следующей строке или введи \"-n\", чтобы пропустить вопрос. " +
            "Пока никаких фич у меня больше нет, простите";

    private static Map<String, String> keys = initializeKeys();
    private static Question[] questions = {new Question("На озере расцвела одна лилия. " +
            "Каждый день число цветков удваивалось, и на двадцатый день все "
            + "\nозеро покрылось цветами. На какой день покрылась цветами "
            + "\nполовина озера?", Arrays.asList("19"), 1),
            new Question("Сколько тонн земли находится в яме размера 2x2x2 метра",
                    Arrays.asList("0"), 1),
            new Question("Введите следующий символ последовательности С О Н Д Я Ф М А",
                    Arrays.asList("м"), 1),
            new Question("Обогнав в спринте третьего бегуна на каком месте вы окажетесь ВЫ ПРАИГРАИТЕ",
                    Arrays.asList("3", "третий", "третьем", "на третьем"), 1),
            new Question("Малыш спрятал от Карлсона банку с вареньем в "
                    + "\nодну из трех разноцветных коробок. На коробках"
                    + "\nМалыш сделал надписи: на красной – «Здесь варенья нет»; "
                    + "\nна синей – «Варенье - здесь»; на зеленой – "
                    + "\n«Варенье в синей коробке». Только одна из надписей п"
                    + "\nравдива. В какой коробке Малыш спрятал варенье?",
                    Arrays.asList("в зеленой", "в зелёной", "зеленой",
                            "зелёной"), 2)
    };

    private static Map<String, String> initializeKeys()
    {
        Map<String, String> keys = new HashMap<String, String>();
        keys.put("-h", help);
        keys.put( "-n", "Пропускаем этот вопрос");
        return keys;
    }

    private static String Ask(UserCondition user)
    {
        int n = user.getQuestionNumber();
        return questions[n].question;
    }

    private static String Check(UserCondition user, String answer)
    {
        String output;
        int scores;
        int questionNumber = user.getQuestionNumber();
        if (questions[questionNumber].answers.contains(answer))
        {
            output = "Правильный ответ!";
            scores = questions[questionNumber].cost;
            user.addScores(scores);
            user.moveToNextQuestion();
        }
        else output = "Неправильный ответ";
        return output;
    }

    private static String analyzeInput(UserCondition user, String input)
    {
        if (keys.containsKey(input))
        {
            if (input.equals("-n"))
                user.moveToNextQuestion();
            return keys.get(input);
        }
        return Check(user, input);
    }

    public static void main(String[] args)
    {
        consoleRealisation();
    }

    private static void consoleRealisation()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите своё имя");
        String userName = input.nextLine();
        UserCondition user = new UserCondition(userName);
        System.out.println(String.format("Привет, %s! Предлагаю тебе ответить на несколько каверзных вопросов" +
                "\nПросто напиши свой ответ в следующей строке, никак не приходит в голову правильный ответ? " +
                "\nТогда введи \"-n\", чтобы пропустить вопрос. Помни, ты всегда можешь попросить меня рассказать" +
                "\nо работе со мной через ключ \"-h\", готов?" +
                "\nТогда вот первый вопрос:", userName));
        int length = questions.length;
        while (true)
        {
            if (user.getQuestionNumber() < length)
                System.out.println(Ask(user));
            else
                System.out.println(String.format("Вы набрали %d очков, поздравляем!", user.getScore()));
            System.out.println(analyzeInput(user, input.nextLine().toLowerCase()));
        }
    }

    /*public static void askAndCheck(UserCondition user, Scanner input)
    {
        int n = user.getQuestionNumber();
        System.out.println(questions[n].question);
        String answer = input.nextLine().toLowerCase();
        int scores;
        if (questions[n].answers.contains(answer))
        {
            System.out.println("Правильный ответ!");
            scores = questions[n].cost;
            user.addScores(scores);
            user.moveToNextQuestion();
        }
        else if (answer.equals("-h"))
            System.out.println(help);
        else if (answer.equals("-n"))
            user.moveToNextQuestion();
        else System.out.println("Неправиьный ответ");
    }*/
}
