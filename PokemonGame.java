import java.util.Random;
import java.util.Scanner;
class Pokemon 
{
    private String name;
    private String type;
    private int health;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int level;
    private int experience;
    private Move[] moves;
    private boolean isWild;
    public Pokemon(String name, String type, int health, int attack, int defense, int specialAttack, int specialDefense, int speed, int level, Move[] moves, boolean isWild) 
    {
        this.name = name;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.level = level;
        this.experience = 0;
        this.moves = moves;
        this.isWild = isWild;
    }
    public String getName() 
    {
        return name;
    }
    public int getHealth() 
    {
        return health;
    }
    public int getAttack() 
    {
        return attack;
    }
    public int getDefense() 
    {
        return defense;
    }
    public int getSpecialAttack() 
    {
        return specialAttack;
    }
    public int getSpecialDefense() 
    {
        return specialDefense;
    }
    public int getSpeed() 
    {
        return speed;
    }
    public int getLevel() 
    {
        return level;
    }
    public Move[] getMoves() 
    {
        return moves;
    }
    public boolean isWild() 
    {
        return isWild;
    }
    public void setWild(boolean isWild) 
    {
        this.isWild = isWild;
    }
    public void attack(Pokemon opponent) 
    {
        Random random = new Random();
        int damage = (int) (0.5 * this.level * (this.attack / opponent.defense) * this.moves[0].getPower());
        opponent.takeDamage(damage);
        System.out.println(this.name + " attacks " + opponent.getName() + " for " + damage + " damage!");
    }
    public void takeDamage(int damage) 
    {
        health -= damage;
        if (health <= 0) 
        {
            health = 0;
            System.out.println(this.name + " has fainted!");
        }
    }
    public void gainExperience(int exp) 
    {
        experience += exp;
        if (experience >= level * 100) 
        {
            levelUp();
        }
    }
    public void levelUp() 
    {
        level++;
        health += 10;
        attack += 5;
        defense += 5;
        specialAttack += 5;
        specialDefense += 5;
        speed += 5;
        System.out.println(this.name + " has leveled up to level " + level + "!");
    }
    public void learnMove(Move move) 
    {
        Move[] newMoves = new Move[this.moves.length + 1];
        for (int i = 0; i < this.moves.length; i++) 
        {
            newMoves[i] = this.moves[i];
        }
        newMoves[this.moves.length] = move;
        this.moves = newMoves;
        System.out.println(this.name + " has learned " + move.getName() + "!");
    }
    public int getStat(String stat) 
    {
        switch (stat) 
        {
            case "health":
                return health;
            case "attack":
                return attack;
            case "defense":
                return defense;
            case "specialAttack":
                return specialAttack;
            case "specialDefense":
                return specialDefense;
            case "speed":
                return speed;
            default:
                return 0;
        }
    }
}
class Move 
{
    private String name;
    private String type;
    private int power;
    private int accuracy;
    private String category;
    private String effect;
    public Move(String name, String type, int power, int accuracy, String category, String effect) 
    {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.category = category;
        this.effect = effect;
    }
    public String getName() 
    {
        return name;
    }
    public int getPower() 
    {
        return power;
    }
    public void applyEffect(Pokemon target) 
    {
        System.out.println(this.effect);
    }
}
class Trainer 
{
    private String name;
    private Pokemon[] team;
    private Item[] inventory;
    public Trainer(String name, Pokemon[] team, Item[] inventory) 
    {
        this.name = name;
        this.team = team;
        this.inventory = inventory;
    }
    public String getName() 
    {
        return name;
    }
    public Pokemon[] getTeam() 
    {
        return team;
    }
    public void catchPokemon(Pokemon wildPokemon) 
    {
        Random random = new Random();
        if (random.nextInt(100) < 50) 
        {
            System.out.println("You caught " + wildPokemon.getName() + "!");
            Pokemon[] newTeam = new Pokemon[this.team.length + 1];
            for (int i = 0; i < this.team.length; i++) 
            {
                newTeam[i] = this.team[i];
            }
            newTeam[this.team.length] = wildPokemon;
            this.team = newTeam;
            wildPokemon.setWild(false);
        } 
        else 
        {
            System.out.println("The Pokeball missed " + wildPokemon.getName() + "!");
        }
    }
    public void useItem(Item item, Pokemon target) 
    {
        item.use(target);
    }
    public void battle(Trainer opponent) 
    {
        System.out.println(this.name + " challenges " + opponent.getName() + " to a battle!");
        Pokemon myPokemon = this.team[0];
        Pokemon opponentPokemon = opponent.getTeam()[0];
        Scanner scanner = new Scanner(System.in);
        while (myPokemon.getHealth() > 0 && opponentPokemon.getHealth() > 0) 
        {
            System.out.println(myPokemon.getName() + " vs " + opponentPokemon.getName());
            System.out.println(myPokemon.getName() + " HP: " + myPokemon.getHealth());
            System.out.println(opponentPokemon.getName() + " HP: " + opponentPokemon.getHealth());
            System.out.println("Choose a move:");
            for (int i = 0; i < myPokemon.getMoves().length; i++) 
            {
                System.out.println((i + 1) + ". " + myPokemon.getMoves()[i].getName());
            }
            int choice = scanner.nextInt() - 1;
            if (choice < 0 || choice >= myPokemon.getMoves().length) 
            {
                System.out.println("Invalid choice. Turn skipped.");
            } 
            else 
            {
                Move myMove = myPokemon.getMoves()[choice];
                Move opponentMove = opponentPokemon.getMoves()[0];
                if (myPokemon.getSpeed() > opponentPokemon.getSpeed()) 
                {
                    myPokemon.attack(opponentPokemon);
                    if (opponentPokemon.getHealth() > 0) 
                    {
                        opponentPokemon.attack(myPokemon);
                    }
                } 
                else 
                {
                    opponentPokemon.attack(myPokemon);
                    if (myPokemon.getHealth() > 0) 
                    {
                        myPokemon.attack(opponentPokemon);
                    }
                }
            }
        }
        if (myPokemon.getHealth() <= 0) 
        {
            System.out.println(myPokemon.getName() + " fainted! " + opponent.getName() + " wins!");
        } 
        else 
        {
            System.out.println(opponentPokemon.getName() + " fainted! " + this.name + " wins!");
            myPokemon.gainExperience(50); // Arbitrary experience points for winning
        }
    }
}
public class PokemonGame 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Pokemon game!");
        System.out.print("Enter your trainer's name: ");
        String trainerName = scanner.nextLine();
        Move tackle = new Move("Tackle", "Normal", 40, 100, "Physical", "The user charges at the target.");
        Move growl = new Move("Growl", "Normal", 0, 100, "Status", "The user growls to intimidate the target.");
        Move scratch = new Move("Scratch", "Normal", 40, 100, "Physical", "The user scratches the target with sharp claws.");
        Move tailWhip = new Move("Tail Whip", "Normal", 0, 100, "Status", "The user wags its tail to intimidate the target.");
        Pokemon pikachu = new Pokemon("Pikachu", "Electric", 50, 50, 40, 50, 50, 90, 5, new Move[]{tackle, growl}, true);
        Pokemon charmander = new Pokemon("Charmander", "Fire", 40, 50, 40, 50, 50, 65, 5, new Move[]{scratch, tailWhip}, true);
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", 45, 49, 49, 65, 65, 45, 5, new Move[]{tackle, growl}, true);
        Pokemon squirtle = new Pokemon("Squirtle", "Water", 44, 48, 65, 50, 64, 43, 5, new Move[]{tackle, tailWhip}, true);
        Pokemon[] wildPokemon = {pikachu, charmander, bulbasaur, squirtle};
        Pokemon[] team = new Pokemon[0];
        Trainer trainer = new Trainer(trainerName, team, new Item[0]);
        System.out.println("Trainer: " + trainer.getName());
        for (Pokemon wildPoke : wildPokemon) 
        {
            System.out.println("-----------------------------------");
            System.out.println("Wild Pokemon Appeared!");
            System.out.println("Pokemon: " + wildPoke.getName());
            System.out.print("Do you want to catch this Pokemon? (yes/no): ");
            String catchChoice = scanner.nextLine();
            if (catchChoice.equalsIgnoreCase("yes")) 
            {
                trainer.catchPokemon(wildPoke);
            } 
            else 
            {
                System.out.println("You decided not to catch " + wildPoke.getName() + ".");
            }
        }
        System.out.println("-----------------------------------");
        System.out.println("Your team: ");
        for (Pokemon pokemon : trainer.getTeam()) {
            System.out.println(pokemon.getName());
        }
        if (trainer.getTeam().length > 0) {
            Trainer opponent = new Trainer("Gary", new Pokemon[]{squirtle}, new Item[0]);
            trainer.battle(opponent);
        } else {
            System.out.println("You have no Pokemon to battle with.");
        }
        scanner.close();
    }
}
