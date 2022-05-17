import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class RPS  {

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();

    }
    public enum Type{
        ROCK(1,2),
        PAPER(2,3),
        SCISSORS(3,1);
        private final int index;
        private final int beatenBy;
        Type(int index, int beatenBy) {
            this.beatenBy = beatenBy;
            this.index = index;
        }
        public int getBeatenBy() {
            return beatenBy;
        }
        public int getIndex() {
            return index;
        }
    }
    public static class Player{

        @JsonProperty("score")
        private int score;
        private Type choice;
        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Type getChoice() {
            return choice;
        }

        public void setChoice(Type choice) {
            this.choice = choice;
        }
    }

    public static class Game{
        private final Scanner input = new Scanner(System.in);
        List<Player> players = List.of(new Player(),new Player());

        public Type setChoice(Integer num){
            switch (num){
                case 1:
                    return Type.ROCK;
                case 2:
                    return Type.PAPER;
                case 3:
                    return Type.SCISSORS;
                default:
                    return null;
            }
        }

        public void playGame(){
            System.out.println("Player 1 - enter your name: ");
            players.get(0).setName(input.nextLine());
            System.out.println("Player 2 - enter your name: ");
            players.get(1).setName(input.nextLine());

            System.out.println(players.get(0).getName() +  " Enter 1: Rock, 2: Paper, 3: Scissors");
            int choice = input.nextInt();
            checkInput(choice);
            players.get(0).setChoice(setChoice(choice));
            System.out.println(players.get(1).getName() + " Enter 1: Rock, 2: Paper, 3: Scissors");
            choice = input.nextInt();
            checkInput(choice);
            players.get(1).setChoice(setChoice(choice));

            if(players.get(0).choice == players.get(1).choice){
                System.out.println("Draw!");
            }
            else if(players.get(0).choice.getBeatenBy() == players.get(1).choice.getIndex()){
                System.out.println(players.get(0).getName() + " " + players.get(0).choice.name());
                System.out.println(players.get(1).getName() + " " + players.get(1).choice.name());
                System.out.println(players.get(1).getName() + " wins!");
                players.get(1).score ++;
            } else {
                System.out.println(players.get(0).getName() + " " + players.get(0).choice.name());
                System.out.println(players.get(1).getName() + " " + players.get(1).choice.name());
                System.out.println(players.get(0).getName() + " wins!");
                players.get(0).score ++;
            }
            writePLayersToJson();

        }
        public void writePLayersToJson(){
            try{
                ObjectMapper mapper = new ObjectMapper();
                ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
                writer.writeValue(Paths.get("player_scores.json").toFile(), players);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
        public void checkInput(Integer choice){
            Integer[] validChoices = {1,2,3};
            if(!Arrays.asList(validChoices).contains(choice)){
                System.out.println("Invalid option, please enter 1- 3");
                checkInput(choice);
            }
        }
    }

}
