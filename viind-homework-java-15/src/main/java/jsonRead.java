import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


public class jsonRead {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {


        try {
            Path filePath = Path.of("src/main/json/conversations_corona_chatbot.json");
            ObjectMapper mapper = new ObjectMapper();
            String conversationString = Files.readString(filePath);
            Conversations[] conversations = mapper.readValue(conversationString, Conversations[].class);


            conversations = conversations;
            List<String> fallBackList;
            int counter = 0;
            float allActionsSum = 0;
            float average = 0;

            int counterAllActions = 0;
            float counterAllFallBackMsg = 0;
            float percent = 0;
            List<String> allCurrentIntents = new ArrayList<>();
            int counterAllIntents = 0;
            int counterAllFAQ = 0;
            String result;
            List<String> intentFaqList;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            for (Conversations currentConversations : conversations) {

                if (currentConversations.getIntents().length > 1) {
                    allActionsSum = allActionsSum + currentConversations.getIntents().length;
                    counter++;
                }



                fallBackList = Arrays.asList(currentConversations.getActions());

                if (fallBackList.contains("action_default_fallback")) {
                    counterAllFallBackMsg = counterAllActions + fallBackList.size();

                }
                counterAllActions++;




                for (String currentIntents : currentConversations.getIntents()) {

                    if (currentIntents.matches("faq/imported_.*")) {
                        counterAllIntents = counterAllIntents + currentIntents.length();
                       /* result = currentIntents.toString();
                        System.out.println(result);*/

                        allCurrentIntents.add(currentIntents);

                    }
                }



            }
            Map<String, Integer> resultMap = new HashMap<>();
            for (String s : allCurrentIntents) {

                if (resultMap.containsKey(s)) {

                    resultMap.put(s, resultMap.get(s) + 1);
                } else {

                    resultMap.put(s, 1);
                }
            }
            int max = resultMap.values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getMax();
            Set<String> res = new HashSet<>();
            if (resultMap.containsValue(max)) {
                for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
                    if (Objects.equals(entry.getValue(), max)) {
                        res.add(entry.getKey());
                    }
                }
            }


            System.out.println("-----------Challenge-1--------------");
            average = allActionsSum / counter;
            System.out.println("Sum of all Actions: " + allActionsSum);
            System.out.println("Counter of all Conversations: " + counter);
            System.out.println("Average: " + df.format(average));
            System.out.println("------------------------------------");
            percent = counterAllFallBackMsg / counterAllActions * 100;

            System.out.println("-----------Challenge-2--------------");
            System.out.println("Sum of all Actions: " + counterAllFallBackMsg);
            System.out.println("Counter of all Conversations: " + counterAllActions);
            System.out.println("Percent of all Fallback-Messages: " + df.format(percent) + "%");
            System.out.println("------------------------------------");

            System.out.println("-----------Challenge-3--------------");
            System.out.println("Sum of all Intents: " + counterAllIntents);
            System.out.println("Meisten anfragen: " + res + ":"+ max );
            System.out.println("------------------------------------");

        } catch (IOException e) {
            System.out.println("F*ck you!");
        }
    }
}