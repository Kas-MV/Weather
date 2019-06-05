package com.example.voiseassistant;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.util.Consumer;
import android.text.TextUtils;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {

    @TargetApi(Build.VERSION_CODES.O)
    public static void getAnswer(String user_question, final Consumer<String> callback) {
        Map<String, String> database = new HashMap<String, String>() {{
            put("привет", "И вам здрасте");
            put("как дела", "Да вроде ничего");
            put("чем занимаешься", "Отвечаю на дурацикие  вопросы !");
            put("как тебя зовут", "Я - голосовой помощник Иннокентий");
            put("кто тебя создал", "Меня создал Марат");
            put("кого ты любишь", "Я люблю Лену");
        }};

        user_question = user_question.toLowerCase();
        final ArrayList<String> answers = new ArrayList<>();

        for (String database_question : database.keySet()) {
            if (user_question.contains(database_question)) {
                answers.add(database.get(database_question));
            }
        }

        Pattern cityPattern = Pattern.compile("какя погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(user_question);
        if (matcher.find()) {
            String cityName = matcher.group(1);
            Weather.get(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    callback.accept (TextUtils.join(", ", answers));
                }
            });
        } else {
            if (answers.isEmpty()) {
                callback.accept("Ok");
                return;
            }
            callback.accept (TextUtils.join(", ", answers));
        }

    }
}
